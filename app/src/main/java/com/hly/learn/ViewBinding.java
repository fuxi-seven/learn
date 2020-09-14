package com.hly.learn;

import android.util.Log;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class ViewBinding {
    @BindingAdapter(value = {"app:step"})
    public static void setProperty(TextView textView, String step) {
        Log.e("Seven", "text is: " + step);
        textView.setText(step);
    }
}
