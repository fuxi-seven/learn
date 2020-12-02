package com.hly.httpRequest;

/**
 * 回调调用层的接口,数据返回的统一接口
 */
public interface IDataListener<M> {
    void onSuccess(M m);
    void onFailure();
}
