package com.hly.learn.util;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;

public class AppItemInfo {
    public String packageName;
    public CharSequence title;
    public Drawable icon;
    public UserHandle user;
    public Intent intent;

    public AppItemInfo(LauncherActivityInfo info, UserHandle user) {
        this.packageName = info.getComponentName().getPackageName();
        this.title = info.getLabel();
        this.icon = info.getIcon(0);
        this.user = user;
        this.intent = makeLaunchIntent(info);
    }

    public static Intent makeLaunchIntent(LauncherActivityInfo info) {
        return makeLaunchIntent(info.getComponentName());
    }

    public static Intent makeLaunchIntent(ComponentName cn) {
        return new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER)
                .setComponent(cn)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    }
}
