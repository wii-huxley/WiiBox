package com.huxley.wii.wiibox.common.utils;

import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.WiiApp;
import com.huxley.wii.wiibox.common.helper.ResHelper;
import com.huxley.wii.wiitools.common.Utils.StringUtil;
import com.squareup.picasso.Picasso;

/**
 *
 * Created by huxley on 16/4/22.
 */
public class ImageLoaderUtils {

    public static void setGankBigImage(ImageView imageView, String url) {
        Picasso.with(WiiApp.getContext())
                .load(url)
                .resize(ResHelper.getScreenWidth(), ResHelper.dpToPx(256))
                .centerCrop()
                .into(imageView);
    }

    public static void setBigImage(ImageView imageView, String url) {
        Picasso.with(WiiApp.getContext())
                .load(url)
                .into(imageView);
    }

    public static void setGankImage(ImageView image, String url) {
        Picasso.with(WiiApp.getContext())
                .load(StringUtil.isEmp(url))
                .resize(ResHelper.getScreenWidth() / 2 - ResHelper.dpToPx(10), ResHelper.getScreenHeight())
                .placeholder(R.drawable.img_empty)
                .error(R.drawable.img_empty)
                .centerInside()
                .into(image);
    }

    public static void setTing56Image(ImageView image, String url) {
        Picasso.with(WiiApp.getContext())
                .load(url)
                .resize(ResHelper.dpToPx(100), ResHelper.dpToPx(100))
                .centerCrop()
                .into(image);
    }
}
