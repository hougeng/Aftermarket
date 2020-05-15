package com.aftermarket.android.service;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetInterface {
/*POST任意,最终以Json Object形式返回	用于测试Form表单，支持文件上传*/
    @POST("/login")
    Observable<JsonObject> login(@Body JsonObject jsonObject);

    @POST("/reg")
    Observable<JsonObject> reg(@Body JsonObject jsonObject);
/*get 获取test和logout*/
    @GET("/test")
    Observable<JsonObject> test();

    @GET("/logOut")
    Observable<String> logOut();
}
