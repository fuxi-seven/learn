package com.hly.apt_processor;

import com.google.auto.service.AutoService;
import com.hly.apt_annotation.BindView;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {
    private boolean isUseJavapoet = true;
    private Elements mElementUtils;
    private Map<String, ClassCreatorFactory> mClassCreatorFactoryMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //拿到工具类
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //这个注解处理器是给哪个注解用的
        HashSet<String> supportType = new LinkedHashSet<>();
        supportType.add(BindView.class.getCanonicalName());
        return supportType;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //返回java版本
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mClassCreatorFactoryMap.clear();
        //得到所有包含该注解的element集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements) {
            //转换为VariableElement，VariableElement为element的子类
            VariableElement variableElement = (VariableElement) element;
            System.out.println(variableElement.getSimpleName());
            //可以获取类的信息的element，也是element的子类
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            //获取包名加类名
            String fullClassName = classElement.getQualifiedName().toString();

            //保存到集合中
            ClassCreatorFactory factory = mClassCreatorFactoryMap.get(fullClassName);
            if (factory == null) {
                factory = new ClassCreatorFactory(mElementUtils, classElement);
                mClassCreatorFactoryMap.put(fullClassName, factory);
            }
            BindView bindViewAnnotation = variableElement.getAnnotation(BindView.class);
            int id = bindViewAnnotation.value();
            factory.putElement(id, variableElement);
        }
        //开始创建java类
        for (String key : mClassCreatorFactoryMap.keySet()) {
            ClassCreatorFactory factory = mClassCreatorFactoryMap.get(key);
            try {
                if (!isUseJavapoet) {
                    JavaFileObject fileObject = processingEnv.getFiler().createSourceFile(
                            factory.getClassFullName(), factory.getTypeElement());
                    Writer writer = fileObject.openWriter();
                    //写入java代码
                    writer.write(factory.generateJavaCode());
                    writer.flush();
                    writer.close();
                } else {
                    JavaFile javaFile = JavaFile.builder(factory.getClassFullName(),
                            factory.generateJavaCodeByJavapoet()).build();
                    javaFile.writeTo(processingEnv.getFiler());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}