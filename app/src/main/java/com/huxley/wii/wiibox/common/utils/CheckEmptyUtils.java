package com.huxley.wii.wiibox.common.utils;

import java.util.List;

/**
 * Created by huxley on 16/6/16.
 */
public class CheckEmptyUtils {

    public static boolean list(List list) {
        return list == null || list.size() <= 0;
    }
}
