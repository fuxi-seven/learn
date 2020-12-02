package com.hly.httpRequest;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements Runnable {
    private IHttpRequest httpRequest;
    private IHttpListener httpListener;

    public <T> HttpTask(T requestInfo, String url, boolean isGet, IHttpRequest httpRequest,
            IHttpListener httpListener) {
        this.httpRequest = httpRequest;
        this.httpListener = httpListener;
        //设置url
        this.httpRequest.setUrl(url);
        //设置请求方式
        this.httpRequest.setType(isGet);
        //设置响应回调
        this.httpRequest.setHttpCallBack(httpListener);
        //设置请求参数
        if (requestInfo != null) {
            //将用户发送的请求参数对象转换成字符串
            String requestContent = JSON.toJSONString(requestInfo);
            //字符串转byte数组
            try {
                this.httpRequest.setRequestData(requestContent.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        httpRequest.execute();
    }
}
