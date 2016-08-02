package com.huxley.wii.wiibox.mvp.codekk.model;

import com.huxley.wii.wiitools.common.Utils.GsonUtils;

/**
 * Created by LeiJin01 on 2016/7/29.
 */
public class ResultBean<D> {

    public int code;
    public String message;
    public D data;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }
}
