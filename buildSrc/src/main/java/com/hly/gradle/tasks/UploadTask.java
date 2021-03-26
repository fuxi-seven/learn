package com.hly.gradle.tasks;

import static com.hly.gradle.ReleaseHelperPlugin.ANDROID_EXTENSION_NAME;
import static com.hly.gradle.ReleaseHelperPlugin.sPluginExtensionName;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.api.BaseVariantOutput;
import com.android.tools.r8.w.S;

import com.hly.gradle.Extension;
import com.hly.gradle.api.OkHttpUtil;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class UploadTask extends DefaultTask {

    public BaseVariant mVariant;
    public Project mProject;

    public void init(BaseVariant variant, Project project) {
        mVariant = variant;
        mProject = project;
        setDescription("update release apk to server");
        setGroup("release helper");
    }

    @TaskAction
    public void uploadToServer() {
        //获取android extension，即app模块下build.gradle内部android闭包
        //version code, version name都存在defaultConfig闭包下
        AppExtension appExtension = (AppExtension) mProject.getExtensions().findByName(ANDROID_EXTENSION_NAME);
        for (BaseVariantOutput it : mVariant.getOutputs()) {
            File apkFile = it.getOutputFile();
            if (apkFile == null || !apkFile.exists()) {
                throw new GradleException(apkFile + " is not existed!");
            } else {
                System.out.println(apkFile.getAbsoluteFile());
            }

            //获取自定义的闭包,即app模块下build.gradle内部releaseHelper闭包，Extension是在ReleaseHelperPlugin内部创建定义的
            //需要的信息可以在该闭包内写入
            Extension extension = Extension.getConfig(mProject);

            System.out.println("########################################################");
            System.out.println("# applicationId : " +
                    mVariant.getMergedFlavor().getApplicationId());
            System.out.println("# uploadFileName : " + apkFile.getAbsolutePath());
            System.out.println("# VersionName : " + appExtension.getDefaultConfig().getVersionName());
            System.out.println("# VersionCode : " + appExtension.getDefaultConfig().getVersionCode());
            System.out.println("# uKey is : " + extension.uKey);
            System.out.println("# apiKey is : " + extension.apiKey);
            System.out.println("# appName is : " + extension.appName);
            System.out.println("########################################################");

            //通过okhttp来上传
            OkHttpUtil.upload(extension.uKey, extension.apiKey, apkFile);
        }
    }
}
