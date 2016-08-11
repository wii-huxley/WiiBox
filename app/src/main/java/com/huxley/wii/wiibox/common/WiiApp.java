package com.huxley.wii.wiibox.common;

import com.huxley.wii.wiibox.beans.KeyInfo;
import com.huxley.wii.wiitools.base.WiiApplication;
import com.huxley.wii.wiitools.common.Utils.FileUtils;
import com.huxley.wii.wiitools.common.Utils.GsonUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;


/**
 *
 * Created by huxley on 16/2/28.
 */
public class WiiApp extends WiiApplication {

    private KeyInfo mKeyInfo;

    @Override
    public void onCreate() {
        super.onCreate();

        initKey();
        initBmob();
    }

    private void initBmob() {
        if (mKeyInfo != null) {
            Bmob.initialize(new BmobConfig.Builder(this) //设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
                    .setApplicationId(mKeyInfo.Bmob.ApplicationId) // 设置appkey
                    .setConnectTimeout(30) // 请求超时时间（单位为秒）：默认15s
                    .setUploadBlockSize(1024*1024) // 文件分片上传时每片的大小（单位字节），默认512*1024
                    .setFileExpiration(2500) // 文件的过期时间(单位为秒)：默认1800s
                    .build());
        }
    }

    private void initKey() {
        String key = FileUtils.readAssets(this, "key.json");
        mKeyInfo = GsonUtils.get().fromJson(key, KeyInfo.class);
    }

}