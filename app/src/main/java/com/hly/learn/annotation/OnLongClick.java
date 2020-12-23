package com.hly.learn.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ClickEvent(listenerSetting = "setOnLongClickListener", listenerClass =
        View.OnLongClickListener.class, eventName = "onLongClick")
public @interface OnLongClick {

    int[] value();
}
