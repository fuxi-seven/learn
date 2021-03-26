package com.hly.gradle.api;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    public static void upload(String ukey, String apiKey, File apkFile) {
        //builder
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //add part
        bodyBuilder.addFormDataPart("uKey", ukey);
        bodyBuilder.addFormDataPart("_api_key", apiKey);
        //add file
        bodyBuilder.addFormDataPart("file", apkFile.getName(), RequestBody
                .create(MediaType.parse("*/*"), apkFile));
        //request
        Request request = new Request.Builder()
                .url("http://upload.pgyer.com/apiv1/app/upload")
                .post(bodyBuilder.build())
                .build();

        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            System.out.println("upload result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {

        }
    }
}
