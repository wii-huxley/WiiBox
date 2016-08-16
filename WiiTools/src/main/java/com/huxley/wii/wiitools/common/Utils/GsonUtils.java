package com.huxley.wii.wiitools.common.Utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by huxley on 16/7/28.
 */

public class GsonUtils {

    private static GsonUtils sGsonUtils;
    private Gson mGson;

    public GsonUtils() {
        mGson = new Gson();
    }

    public static GsonUtils get(){
        if (sGsonUtils == null) {
            synchronized (GsonUtils.class) {
                if (sGsonUtils == null) {
                    sGsonUtils = new GsonUtils();
                }
            }
        }
        return sGsonUtils;
    }

    public String toJson(Object obj) {
        return mGson.toJson(obj);
    }

    public<D> D toObject(String jsonString, Class<D> dataClass) {
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }
        return mGson.fromJson(jsonString, dataClass);
    }

    public <D> List<D> toList(String jsonString) {
        if (StringUtil.isEmpty(jsonString)) {
            return new ArrayList<>();
        }
        return (List) (new Gson()).fromJson(jsonString, List.class);
    }
}
