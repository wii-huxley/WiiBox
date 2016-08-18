package com.huxley.wii.wiibox.page.main.gank.model;

import com.google.gson.Gson;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.http.HttpClient;
import com.huxley.wii.wiitools.common.Utils.GsonUtils;
import com.huxley.wii.wiitools.common.Utils.StringUtil;

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
            read = (String) SP.Gank.read(Constant.Key.HISTORY_LIST, "");
        }
        return Observable.just(GsonUtils.get().toObject(read, DataInfo.class))
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
        GankInfo.ResultsBean results = gankInfo.results;
        String url = results.photo.get(0).url;
        if (!StringUtil.isEmpty(url)) {
            objs.add(url);
        } else {
            objs.add("file:///android_asset/img_gank_default.jpeg");
        }
        List<GankInfo.ResultsBean.ItemBean> android = results.Android;
        if (android != null && android.size() > 0) {
            objs.add("android");
            objs.addAll(android);
        }
        List<GankInfo.ResultsBean.ItemBean> app = results.App;
        if (app != null && app.size() > 0) {
            objs.add("app");
            objs.addAll(app);
        }
        List<GankInfo.ResultsBean.ItemBean> frontEnd = results.frontEnd;
        if (frontEnd != null && frontEnd.size() > 0) {
            objs.add("拓展资源");
            objs.addAll(frontEnd);
        }
        List<GankInfo.ResultsBean.ItemBean> expandResources = results.expandResources;
        if (expandResources != null && expandResources.size() > 0) {
            objs.add("android");
            objs.addAll(expandResources);
        }
        List<GankInfo.ResultsBean.ItemBean> iOS = results.iOS;
        if (iOS != null && iOS.size() > 0) {
            objs.add("IOS");
            objs.addAll(iOS);
        }
        List<GankInfo.ResultsBean.ItemBean> recommend = results.recommend;
        if (recommend != null && recommend.size() > 0) {
            objs.add("瞎推荐");
            objs.addAll(recommend);
        }
        List<GankInfo.ResultsBean.ItemBean> restVideo = results.restVideo;
        if (restVideo != null && restVideo.size() > 0) {
            objs.add("休息视频");
            objs.addAll(restVideo);
        }
        return objs;
    }

    public void setDataInfo(DataInfo dataInfo) {
        SP.Gank.save(Constant.Key.HISTORY_LIST, new Gson().toJson(dataInfo));
    }

    public void setGankInfo(String date, GankInfo gankInfo) {
        SP.Gank.save(date, new Gson().toJson(gankInfo));
    }
}
