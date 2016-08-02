package com.huxley.wii.wiibox.mvp.ting56.model;

import com.huxley.wii.wiitools.common.Utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxley on 16/7/31.
 */

public class Ting56ListBean {

    public String nextUrl;

    public List<Ting56Bean> ting56BeanList;

    public Ting56ListBean() {
        ting56BeanList = new ArrayList<>();
    }

    public boolean isEmp(){
        return ting56BeanList == null || ting56BeanList.size() <= 0;
    }

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }
}
