package com.hly.apt_processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ClassCreatorFactory {
    private String mBindClassName;
    private String mPackageName;
    private TypeElement mTypeElement;
    private Map<Integer, VariableElement> mVariableElementMap = new HashMap<>();

    ClassCreatorFactory(Elements elementUtils, TypeElement classElement) {
        this.mTypeElement = classElement;
        //PackageElement是element的子类，可以拿到包信息
        PackageElement packageElement = elementUtils.getPackageOf(mTypeElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = mTypeElement.getSimpleName().toString();
        this.mPackageName = packageName;
        //生成的类的名称
        this.mBindClassName = className + "_ViewBinding";
    }

    public void putElement(int id, VariableElement element) {
        mVariableElementMap.put(id, element);
    }

    public String generateJavaCode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/**\n" + " * Auto Created by apt\n" + "*/\n");
        stringBuilder.append("package ").append(mPackageName).append(";\n");
        stringBuilder.append('\n');
        stringBuilder.append("public class ").append(mBindClassName);
        stringBuilder.append(" {\n");
        generateBindViewMethods(stringBuilder);

        stringBuilder.append('\n');
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    private void generateBindViewMethods(StringBuilder stringBuilder) {
        stringBuilder.append("\tpublic void bindView(");
        stringBuilder.append(mTypeElement.getQualifiedName());
        stringBuilder.append(" owner) {\n");
        for (int id : mVariableElementMap.keySet()) {
            VariableElement variableElement = mVariableElementMap.get(id);
            String viewName = variableElement.getSimpleName().toString();
            String viewType = variableElement.asType().toString();
            stringBuilder.append("\t\towner.");
            stringBuilder.append(viewName);
            stringBuilder.append(" = ");
            stringBuilder.append("(");
            stringBuilder.append(viewType);
            stringBuilder.append(")(((android.app.Activity)owner).findViewById( ");
            stringBuilder.append(id);
            stringBuilder.append("));\n");
        }
        stringBuilder.append("  }\n");
    }

    public TypeSpec generateJavaCodeByJavapoet() {
        TypeSpec bindingClass = TypeSpec.classBuilder(mBindClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(generateBindViewMethodsByJavapoet())
                .build();
        return bindingClass;
    }

    private MethodSpec generateBindViewMethodsByJavapoet() {
        ClassName host = ClassName.bestGuess(mTypeElement.getQualifiedName().toString());
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bindView")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(host, "owner");
        for (int id : mVariableElementMap.keySet()) {
            VariableElement variableElement = mVariableElementMap.get(id);
            String viewName = variableElement.getSimpleName().toString();
            String viewType = variableElement.asType().toString();
            methodBuilder.addCode("owner." + viewName + " = " + "(" + viewType
                    + ")(((android.app.Activity)owner).findViewById( " + id + "));");
        }
        return methodBuilder.build();
    }

    public String getClassFullName() {
        return mPackageName + "." + mBindClassName;
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }
}
