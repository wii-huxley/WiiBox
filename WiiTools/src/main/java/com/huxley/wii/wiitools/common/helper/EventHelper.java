package com.huxley.wii.wiitools.common.helper;

import android.app.Activity;
import android.view.View;

/**
 * Created by huxley on 16/2/29.
 */
public class EventHelper {

    public static void click(Activity o, View... views) {
        if (!(o instanceof View.OnClickListener)) {
            return;
        }
        for (View view : views) {
            view.setOnClickListener((View.OnClickListener) o);
        }
    }

    public static void longClick(Activity o, View... views) {
        if (!(o instanceof View.OnLongClickListener)) {
            return;
        }
        for (View view:views) {
            view.setOnLongClickListener((View.OnLongClickListener) o);
        }
    }

}
