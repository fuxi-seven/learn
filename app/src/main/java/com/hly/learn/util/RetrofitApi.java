package com.hly.learn.util;

import io.reactivex.Observable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApi {

    /**
     * 注解里传入 网络请求 的部分URL地址
     * Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
     * 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
     * 采用Observable<...>接口
     * getTranslation()是接受网络请求数据的方法
     */
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20my%20name%20is%20Westbrook")
    Observable<Translation> getTranslation();

    @GET("ajax.php")
    Observable<Translation> getTranslation2(@Query("a") String a, @Query("f") String f,@Query("t") String t,@Query("w") String w);

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20China")
    Observable<Translation1> getTranslationTwo();

    @GET("ajax.php")
    Observable<Translation1> getTranslationTwo2(@Query("a") String a, @Query("f") String f,@Query("t") String t,@Query("w") String w);

    /*@GET("data/sk/{cityId}.html")
    Observable<WeatherInfo> getWeatherInfo(@Path("cityId") String cityId);*/

    @GET("weather_mini")
    Observable<WeatherInfo> getWeatherInfo(@Query("city") String city);

    @GET("weather_mini")
    Call<WeatherInfo> getWeatherInfoThird(@Query("city") String city);

    @GET("HPImageArchive.aspx")
    Observable<ImageBean> getImage(@Query("format") String format, @Query("idx") int idx, @Query("n") int n);

    @POST("translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=")
    @FormUrlEncoded
    Call<Translation2> getTranslationThird(@Field("i") String targetSentence);
}
