package com.huxley.wii.wiitools.common;

import java.util.HashMap;

/**
 * Created by huxley on 16/7/3.
 */
public class WiiMap <K, T> extends HashMap<K, T> {

    public WiiMap putWii(K k, T t){
        put(k, t);
        return this;
    }
}
