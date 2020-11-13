package com.hly.learn.viewmodel;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class ViewBinding {
    @BindingAdapter(value = {"app:step"})
    public static void setProperty(TextView textView, String step) {
        textView.setText(step);
    }

    @BindingAdapter(value = {"app:url"})
    public static void updateImg(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
