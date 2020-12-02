package com.hly.httpRequest;

import java.io.InputStream;

/**
 * 封装响应
 */
public interface IHttpListener {
    //接受上一个接口的结果
    void onSuccess(InputStream inputStream);
    void onFailure();
}
