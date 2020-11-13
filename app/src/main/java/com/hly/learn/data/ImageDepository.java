package com.hly.learn.data;

import com.hly.learn.util.ImageBean;
import com.hly.learn.util.RetrofitApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageDepository {

    private RetrofitApi mRetrofitApi;

    public ImageDepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cn.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mRetrofitApi = retrofit.create(RetrofitApi.class);
    }

    public Observable<ImageBean> getImage(String format, int idx, int n) {
        return mRetrofitApi.getImage(format, idx, n);
    }
}
