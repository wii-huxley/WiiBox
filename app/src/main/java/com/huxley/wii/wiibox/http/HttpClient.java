package com.huxley.wii.wiibox.http;

import com.huxley.wii.wiibox.http.adapter.RxJavaCallAdapterFactory;
import com.huxley.wii.wiibox.http.api.BaiduTranslateApi;
import com.huxley.wii.wiibox.http.api.CodekkApi;
import com.huxley.wii.wiibox.http.api.GankApi;
import com.huxley.wii.wiibox.http.api.YoudaoTranslateApi;
import com.huxley.wii.wiibox.http.converter.GsonConverterFactory;

import retrofit2.Retrofit;

/**
 * Created by huxley on 16/7/23.
 */
public class HttpClient {


    private static BaiduTranslateApi sBaiduTranslateApi;
    private static GankApi sGankApi;
    private static YoudaoTranslateApi sYoudaoTranslateApi;
    private static CodekkApi sCodekkApi;

    public static BaiduTranslateApi getBaiduTranslateApi(){
        if (sBaiduTranslateApi == null){
            synchronized (BaiduTranslateApi.class) {
                if (sBaiduTranslateApi == null) {
                    sBaiduTranslateApi = new Retrofit.Builder()
                            .baseUrl(BaiduTranslateApi.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(BaiduTranslateApi.class);
                }
            }
        }
        return sBaiduTranslateApi;
    }

    public static GankApi getGankApi(){
        if (sGankApi == null){
            synchronized (GankApi.class) {
                if (sGankApi == null) {
                    sGankApi = new Retrofit.Builder()
                            .baseUrl(GankApi.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(GankApi.class);
                }
            }
        }
        return sGankApi;
    }

    public static YoudaoTranslateApi getYoudaoTranslateApi(){
        if (sYoudaoTranslateApi == null){
            synchronized (YoudaoTranslateApi.class) {
                if (sYoudaoTranslateApi == null) {
                    sYoudaoTranslateApi = new Retrofit.Builder()
                            .baseUrl(YoudaoTranslateApi.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(YoudaoTranslateApi.class);
                }
            }
        }
        return sYoudaoTranslateApi;
    }

    public static CodekkApi getCodekkApi() {
        if (sCodekkApi == null) {
            synchronized (CodekkApi.class) {
                if (sCodekkApi == null) {
                    sCodekkApi = new Retrofit.Builder()
                            .baseUrl(CodekkApi.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                            .create(CodekkApi.class);
                }
            }
        }
        return sCodekkApi;
    }
}
