package com.hly.httpRequest;

/**
 * 封装请求
 */
public interface IHttpRequest {
    void setUrl(String url);
    void setRequestData(byte[] requestData);
    void setType(boolean isGet);
    void execute();
    //需要设置请求和响应两个接口之间的关系
    void setHttpCallBack(IHttpListener httpListener);
}
