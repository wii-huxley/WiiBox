package com.huxley.wii.wiibox.mvp.codekk.model;

import com.huxley.wii.wiitools.common.Utils.GsonUtils;

import java.util.List;

/**
 *
 * Created by LeiJin01 on 2016/7/29.
 */
public class CodekkSearchListBean {

    public boolean isFullSearch;
    public List<CodekkProjectBean> projectArray;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }
}
