package com.huxley.wii.wiitools.common.helper;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by huxley on 16/8/6.
 */

public class ListHelper {

    public static <D> ArrayList<D> create(D... data) {
        ArrayList<D> dList = new ArrayList<>();
        Collections.addAll(dList, data);
        return dList;
    }
}
