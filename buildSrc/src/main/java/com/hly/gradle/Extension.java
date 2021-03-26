package com.hly.gradle;

import org.gradle.api.Project;

public class Extension {

    public String appName;
    public String uKey;
    public String apiKey;
    public Extension(){}

    public Extension(String appName, String uKey, String apiKey) {
        this.appName = appName;
        this.uKey = uKey;
        this.apiKey = apiKey;
    }

    public static Extension getConfig(Project project) {
        Extension extension = project.getExtensions().findByType(Extension.class);
        if (extension == null) {
            extension = new Extension();
        }
        return extension;
    }
}
