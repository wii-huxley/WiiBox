package com.huxley.wii.wiibox.http.api;

import com.huxley.wii.wiibox.mvp.codekk.model.CodekkHomeListBean;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkSearchListBean;
import com.huxley.wii.wiibox.mvp.codekk.model.ResultBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * Created by huxley on 16/7/28.
 */

public interface CodekkApi {

    String BASE_URL = "http://p.codekk.com/api/op/";


    @GET("page/{num}")
    Observable<ResultBean<CodekkHomeListBean>> getHomeList(
            @Path("num") int num
    );

    @GET("detail/{id}")
    Observable<ResultBean<CodekkProjectBean>> getDetailInfo(
            @Path("id") String id
    );

    @GET("search")
    Observable<ResultBean<CodekkSearchListBean>> getSearchList(
            @Query("text") String text,
            @Query("page") String page
    );
}
