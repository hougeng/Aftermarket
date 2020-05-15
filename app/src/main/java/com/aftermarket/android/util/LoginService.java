package com.aftermarket.android.util;

import android.content.Context;

import com.aftermarket.android.db.User;
import com.aftermarket.android.service.NetInterface;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.JsonObject;

import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {
    private String baseUrl;
    private OkHttpClient client;
    private Retrofit retrofit;
    private NetInterface service;
    private CookieJar cookieJar;
    private Context context;

    public LoginService(Context icontext){
        context=icontext;
        baseUrl="http://192.168.43.142:8088";

        cookieJar = new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(context));

        client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(4,TimeUnit.SECONDS)
                .writeTimeout(4,TimeUnit.SECONDS)
                .cookieJar(cookieJar)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//// 支持RxJava平台针对于Java语言的一个异步的响应式编程库
                .client(client)
                .build();

        service = retrofit.create(NetInterface.class);
    }

    public void userLogin(Observer<JsonObject> observer, User user) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",user.getUsername());  //返回单个数据，不需要Array
        jsonObject.addProperty("password",user.getPassword());
//        jsonObject.addProperty("uid",user.getUid());

        service.login(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    public void userRegister(Observer<JsonObject> observer, User user) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", user.getUsername());
        jsonObject.addProperty("password", user.getPassword());
//        jsonObject.addProperty("uid", user.getUid());

        service.reg(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



}
