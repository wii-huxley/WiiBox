package com.huxley.wii.wiibox.common.factory;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by huxley on 16/3/11.
 */
public class DialogFactory {

    public static ProgressDialog getProgressDialog(Context cxt, String msg) {
        ProgressDialog dialog = new ProgressDialog(cxt);
        dialog.setTitle(null);
        dialog.setMessage(msg);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
