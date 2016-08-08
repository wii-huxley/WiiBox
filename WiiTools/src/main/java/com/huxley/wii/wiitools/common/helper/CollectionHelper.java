package com.huxley.wii.wiitools.common.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by huxley on 16/8/6.
 */

public class CollectionHelper {

    @SafeVarargs
    public static <D> ArrayList<D> createList(D... data) {
        ArrayList<D> dList = new ArrayList<>();
        Collections.addAll(dList, data);
        return dList;
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() <=0;
    }
}
