package com.hly.httpRequest;

import java.io.InputStream;

/**
 * 封装响应
 */
public interface IHttpListener {
    void onSuccess(InputStream inputStream);
    void onFailure();
}
