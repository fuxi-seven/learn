package com.hly.httpRequest;

public class Volley {

    public static <T, M> void sendJsonRequest(String url, Class<M> response,
            IDataListener<M> dataListener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        IHttpListener httpListener = new JsonHttpListener<M>(response, dataListener);
        HttpTask<T> httpTask = new HttpTask<T>(null, url, true,httpRequest, httpListener);
        ThreadPoolManager.getSingleInstance().execute(httpTask);
    }

    public static <T, M> void sendBmpRequest(String url, IDataListener<M> dataListener) {
        IHttpRequest httpRequest = new BitmapHttpRequest();
        IHttpListener httpListener = new BitmapHttpListener<M>(dataListener);
        HttpTask<T> httpTask = new HttpTask<T>(null, url, true, httpRequest, httpListener);
        ThreadPoolManager.getSingleInstance().execute(httpTask);
    }

    public static <T, M> void sendPost(T requestInfo, String url, Class<M> response, IDataListener<M> dataListener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        IHttpListener httpListener = new JsonHttpListener<M>(response, dataListener);
        HttpTask<T> httpTask = new HttpTask<T>(requestInfo, url, false, httpRequest, httpListener);
        ThreadPoolManager.getSingleInstance().execute(httpTask);
    }
}
