package com.huxley.wii.wiibox.mvp.main.translate.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huxley on 16/7/19.
 */
public class YouDaoTranslateInfo implements Serializable {

    public BasicBean basic;
    public String query;
    public int errorCode;
    public List<String> translation;
    public List<WebBean> web;

    public static class BasicBean implements Serializable {
        public String phonetic;
        public List<String> explains;
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public static class WebBean implements Serializable {
        public String key;
        public List<String> value;
        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
