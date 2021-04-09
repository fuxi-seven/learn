package com.hly.autoservicecommonlibrary.tab;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ITabPage {

    // tab标题
    String tabName();
    // tab 图标
    String iconName();
}
