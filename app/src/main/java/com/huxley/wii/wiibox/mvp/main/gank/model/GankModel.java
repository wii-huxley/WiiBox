package com.huxley.wii.wiibox.mvp.main.gank.model;

import com.google.gson.Gson;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.http.HttpClient;
import com.huxley.wii.wiitools.common.Utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by huxley on 16/3/26.
 */
public class GankModel {

    private static GankModel sGankModel;

    private GankModel() {}

    public static GankModel getInstance() {
        if (sGankModel == null) {
            synchronized (GankModel.class) {
                if (sGankModel == null) {
                    sGankModel = new GankModel();
                }
            }
        }
        return sGankModel;
    }

    public Observable<DataInfo> getGankDatas() {
        return HttpClient.getGankApi().getDatas();
    }

    public Observable<List<GankInfo>> getLocalGankDatas() {
        String read = null;
        if (SP.Gank.hasHistory()) {
            read = (String) SP.Gank.read(Constant.Extra.HISTORY_LIST, "");
        }
        return Observable.just(GsonUtils.get().fromJson(read, DataInfo.class))
                .flatMap(dataInfo -> {
                    setDataInfo(dataInfo);
                    return Observable.from(dataInfo.datas);
                })
                .map(this::getGankInfo).toList();
    }

    public Observable<List<GankInfo>> getGankInfos() {
        return getGankDatas()
                .flatMap(dataInfo -> {
                    setDataInfo(dataInfo);
                    return Observable.from(dataInfo.datas);
                })
                .map(this::getGankInfo).toList();
    }

    public GankInfo getGankInfo(String date) {
        String data = (String) SP.Gank.read(date, "");
        GankInfo gankInfo = new Gson().fromJson(data, GankInfo.class);
        if (gankInfo == null) {
            gankInfo = new GankInfo();
        }
        gankInfo.date = date;
        return gankInfo;
    }

    public Observable<List<Object>> getGankDetailInfo(final String date) {
        final String finalDate = date.replaceAll("-", "/");
        return HttpClient.getGankApi().getGankDetailInfo(finalDate)
                .flatMap(gankInfo -> {
                    setGankInfo(date, gankInfo);
                    return Observable.from(getGankList(gankInfo));
                }).toList();
    }

    public List<Object> getGankList(GankInfo gankInfo) {
        List<Object> objs = new ArrayList<>();
        List<String> categorys = gankInfo.category;
        GankInfo.ResultsBean results = gankInfo.results;
        for (int i = 0; i < categorys.size(); i++) {
            String category = categorys.get(i);
            switch (category) {
                case "iOS":
                    objs.add(category);
                    objs.addAll(results.iOS);
                    break;
                case "休息视频":
                    objs.add(category);
                    objs.addAll(results.restVideo);
                    break;
                case "前端":
                    objs.add(category);
                    objs.addAll(results.frontEnd);
                    break;
                case "Android":
                    objs.addAll(0, results.Android);
                    objs.add(0, category);
                    break;
                case "瞎推荐":
                    objs.add(category);
                    objs.addAll(results.recommend);
                    break;
                case "App":
                    objs.add(category);
                    objs.addAll(results.App);
                    break;
                case "拓展资源":
                    objs.add(category);
                    objs.addAll(results.expandResources);
                    break;
                case "福利":
                    objs.add(0, results.photo.get(0).url);
                    break;
            }
        }
        return objs;
    }

    public void setDataInfo(DataInfo dataInfo) {
        SP.Gank.save(Constant.Extra.HISTORY_LIST, new Gson().toJson(dataInfo));
    }

    public void setGankInfo(String date, GankInfo gankInfo) {
        SP.Gank.save(date, new Gson().toJson(gankInfo));
    }
}