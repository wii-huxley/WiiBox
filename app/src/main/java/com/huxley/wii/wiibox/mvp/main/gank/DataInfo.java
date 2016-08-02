package com.huxley.wii.wiibox.mvp.main.gank;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huxley on 16/7/15.
 */
public class DataInfo {

    public boolean error;

    @SerializedName("results")
    public List<String> datas;

    @Override
    public String toString() {
        return "DataInfo{" +
                "error=" + error +
                ", datas=" + datas +
                '}';
    }
}
