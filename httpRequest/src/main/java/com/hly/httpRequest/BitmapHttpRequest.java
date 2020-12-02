package com.hly.httpRequest;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapHttpRequest implements IHttpRequest{

    private String url;
    private byte[] requestData;
    private boolean isGet;
    private IHttpListener httpListener;
    private HttpURLConnection urlConnection = null;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void setType(boolean isGet) {
        this.isGet = isGet;
    }

    @Override
    public void execute() {
        if (isGet) {
            httpUrlconnGet();
        }
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public void httpUrlconnGet() {
        URL url;
        try {
            url = new URL(this.url);
            //打开http连接
            urlConnection = (HttpURLConnection) url.openConnection();
            //设置连接的超时时间
            urlConnection.setConnectTimeout(6000);
            //不使用缓存
            urlConnection.setUseCaches(false);
            urlConnection.setInstanceFollowRedirects(true);
            //响应的超时时间
            urlConnection.setReadTimeout(3000);
            //设置这个连接是否可以写入数据
            urlConnection.setDoInput(true);
            //设置这个连接是否可以输出数据
            urlConnection.setDoOutput(true);

            //设置请求的方式
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //获得服务器响应
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = urlConnection.getInputStream();
                //回调监听器的listener方法
                httpListener.onSuccess(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
