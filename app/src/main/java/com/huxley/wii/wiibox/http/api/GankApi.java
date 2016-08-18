package com.huxley.wii.wiibox.http.api;


import com.huxley.wii.wiibox.page.main.gank.model.DataInfo;
import com.huxley.wii.wiibox.page.main.gank.model.GankInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by huxley on 16/4/14.
 */
public interface GankApi {

    String BASE_URL = "http://gank.io/api/";

    @GET("day/history")
    Observable<DataInfo> getDatas();

    /** 每日数据 */
    @GET("day/{date}")
    Observable<GankInfo> getGankDetailInfo(
            @Path("date") String date    //日期      年/月/日
    );
}
