package com.hly.learn;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ModalData {

    public static Drawable getDrawable(Context context, int index) {
        if (index == 1) {
            return context.getResources().getDrawable(R.drawable.ic_chuancai);
        } else if (index == 2) {
            return context.getResources().getDrawable(R.drawable.ic_lucai);
        } else {
            return context.getResources().getDrawable(R.drawable.ic_xiangcai);
        }
    }

    public static String getImageName(Context context, int index) {
        if (index == 1) {
            return context.getResources().getString(R.string.chuancai);
        } else if (index == 2) {
            return context.getResources().getString(R.string.lucai);
        } else {
            return context.getResources().getString(R.string.xiangcai);
        }
    }

    public static String getImageDes(Context context, int index) {
        if (index == 1) {
            return context.getResources().getString(R.string.chuancaides);
        } else if (index == 2) {
            return context.getResources().getString(R.string.lucaides);
        } else {
            return context.getResources().getString(R.string.xiangcaides);
        }
    }
}
