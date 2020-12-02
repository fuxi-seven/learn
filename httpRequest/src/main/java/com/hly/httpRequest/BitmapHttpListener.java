package com.hly.httpRequest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BitmapHttpListener<M> implements IHttpListener{

    private IDataListener<M> dataListener;
    //用于切换线程
    Handler handler = new Handler(Looper.getMainLooper());

    public BitmapHttpListener(IDataListener<M> dataListener) {
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //获取响应结果，把byte数据转换成byte[]数据
        byte[] content = readInputStream(inputStream);
        //将byte[]转换成Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeByteArray(content, 0, content.length);
        //把结果传送到调用层
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onSuccess((M)bitmap);
                }

            }
        });
    }

    @Override
    public void onFailure() {
        handler.post(() -> {
            if (dataListener != null) {
                dataListener.onFailure();
            }
        });
    }

    /**
     * 将流转换成二进制
     */
    public static byte[] readInputStream(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toByteArray();
    }
}
