package com.hly.httpRequest;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHttpListener<M> implements IHttpListener {

    Class<M> responseClass;
    private IDataListener<M> dataListener;
    //用于切换线程
    Handler handler = new Handler(Looper.getMainLooper());

    public JsonHttpListener(Class<M> responseClass, IDataListener<M> dataListener) {
        this.responseClass = responseClass;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //获取响应结果，把byte数据转换成String数据
        String content = getContent(inputStream);
        //将json字符串转换成对象
        final M response = JSON.parseObject(content, responseClass);
        //把结果传送到调用层
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onSuccess(response);
                }
            }
        });
    }

    @Override
    public void onFailure() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (dataListener != null) {
                    dataListener.onFailure();
                }
            }
        });
    }

    /**
     * 将流转换成字符串
     */
    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
