package com.huxley.wii.wiibox.mvp.main.translate.model;

import com.huxley.wii.wiitools.common.Utils.GsonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huxley on 16/7/19.
 */
public class BaiduTranslateInfo implements Serializable {

    public String from;
    public String to;
    public List<TransResultBean> trans_result;

    public static class TransResultBean implements Serializable {
        public String src;
        public String dst;

        @Override
        public String toString() {
            return GsonUtils.get().toJson(this);
        }
    }

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }
}
