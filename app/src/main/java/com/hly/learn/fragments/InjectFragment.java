package com.hly.learn.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hly.learn.R;
import com.hly.learn.annotation.BindView;
import com.hly.learn.annotation.InjectManager;
import com.hly.learn.annotation.OnClick;
import com.hly.learn.annotation.OnLongClick;

import java.io.InputStream;

public class InjectFragment extends BaseFragment {

    //无注解变量
    private boolean isSwitch1;
    private boolean isSwitch2;

    @BindView(R.id.img1)
    private ImageView mImg1;
    @BindView(R.id.img2)
    private ImageView mImg2;

    @Override
    public int getLayoutId() {
        return R.layout.inject_layout;
    }

    @Override
    public void initData(View view) {
        InjectManager.inject(this, view);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                int resId;
                if (!isSwitch1) {
                    resId = R.drawable.ic_huicai;
                } else {
                    resId = R.drawable.ic_chuancai;
                }
                @SuppressLint("ResourceType")
                InputStream is = mContext.getResources().openRawResource(resId);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                mImg1.setImageBitmap(bmp);
                isSwitch1 = !isSwitch1;
                break;
            case R.id.btn2:
                int resId1;
                if (!isSwitch2) {
                    resId1 = R.drawable.ic_sucai;
                } else {
                    resId1 = R.drawable.ic_lucai;
                }
                @SuppressLint("ResourceType")
                InputStream is1 = mContext.getResources().openRawResource(resId1);
                Bitmap bmp1 = BitmapFactory.decodeStream(is1);
                mImg2.setImageBitmap(bmp1);
                isSwitch2 = !isSwitch2;
                break;
        }
    }

    @OnLongClick(R.id.btn2)
    public boolean onViewOnLongClick(View view) {
        Toast.makeText(mContext, "这是长按事件", Toast.LENGTH_SHORT).show();
        return true;
    }

}
