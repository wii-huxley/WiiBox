package com.huxley.wii.wiibox.http.api;


import com.huxley.wii.wiibox.mvp.main.translate.model.YouDaoTranslateInfo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by huxley on 16/4/14.
 */
public interface YoudaoTranslateApi {

    String BASE_URL = "http://fanyi.youdao.com/";

    @FormUrlEncoded
    @POST("openapi.do")
    Observable<YouDaoTranslateInfo> translate(
            @Field("keyfrom") String keyfrom,
            @Field("key") String key,
            @Field("type") String type,
            @Field("doctype") String doctype,
            @Field("version") String version,
            @Field("q") String q
    );
}
