package com.hly.gradle;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;

import com.hly.gradle.tasks.UploadTask;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collection;

public class ReleaseHelperPlugin implements Plugin<Project> {

    public static final String ANDROID_EXTENSION_NAME = "android";
    //plugin的extension名字
    public static final String sPluginExtensionName = "releaseHelper";

    @Override
    public void apply(Project project) {
        //创建extension
        project.getExtensions().create(sPluginExtensionName, Extension.class);
        //项目编译完成后回调
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                Collection<ApplicationVariant> appVariants =
                        ((AppExtension) project.getExtensions().findByName(
                                ANDROID_EXTENSION_NAME)).getApplicationVariants();
                //这里可实现多渠道的打包
                for (ApplicationVariant variant : appVariants) {
                    //监听buildType为release的包
                    if (variant.getBuildType().getName().equalsIgnoreCase("release")) {
                        String variantName = variant.getName().substring(0, 1).toUpperCase()
                                + variant.getName().substring(1, 7).toLowerCase();

                        //创建上传task
                        UploadTask uploadTask = project.getTasks().create("uploadFor" + variantName, UploadTask.class);
                        uploadTask.init(variant, project);

                        //配置依赖关系，上传依赖打包,打包依赖clean;
                        //在命令行执行./gradlew uploadForRelease或在直接在as中找到uploadForRelease执行即可打包上传
                        variant.getAssembleProvider().get().dependsOn(project.getTasks().findByName("clean"));
                        uploadTask.dependsOn(variant.getAssembleProvider().get());
                    }
                }
            }
        });

    }
}
