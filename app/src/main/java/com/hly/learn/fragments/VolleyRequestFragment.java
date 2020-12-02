package com.hly.learn.fragments;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hly.httpRequest.IDataListener;
import com.hly.httpRequest.Volley;
import com.hly.learn.R;
import com.hly.learn.util.ImageBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VolleyRequestFragment extends BaseFragment {

    private Button mBtn;
    private TextView mTxt;
    private ImageView mImg;
    private int curIdx = 0;
    private final String baseUrl = "https://cn.bing.com/HPImageArchive.aspx";
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.volley_layout;
    }

    @Override
    public void initData(View view) {
        mBtn = view.findViewById(R.id.volley_btn);
        mTxt = view.findViewById(R.id.volley_txt);
        mImg = view.findViewById(R.id.volley_img);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curIdx < 7) {
                    curIdx++;
                }
                url = baseUrl + "?" + "format=js&idx=" + curIdx + "&n=1";
                sendGetData(url);
            }
        });
    }

    //Get请求返回数据
    private void sendGetData(String url) {
        Volley.sendJsonRequest(url, ImageBean.class, new IDataListener<ImageBean>() {
            @Override
            public void onSuccess(ImageBean imageBean) {
                List<ImageBean.ImagesBean> imagesBeans = imageBean.getImages();
                ImageBean.ImagesBean imagesBean = imagesBeans.get(0);
                String url = ImageBean.ImagesBean.BASE_URL + imagesBean.getUrl();
                String des = imagesBean.getCopyright();
                mTxt.setText(des);
                sendGetBitmap(url);
            }

            @Override
            public void onFailure() {
                Toast.makeText(mContext, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //请求返回图片
    private void sendGetBitmap(String url) {
        Volley.sendBmpRequest(url, new IDataListener<Bitmap>() {
            @Override
            public void onSuccess(Bitmap bmp) {
                mImg.setImageBitmap(bmp);
            }

            @Override
            public void onFailure() {
                Toast.makeText(mContext, "获取失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
