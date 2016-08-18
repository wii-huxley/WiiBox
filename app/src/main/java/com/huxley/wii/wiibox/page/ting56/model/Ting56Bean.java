package com.huxley.wii.wiibox.page.ting56.model;

import com.huxley.wii.wiibox.common.musicplayer.MusicPath;
import com.huxley.wii.wiitools.common.Utils.GsonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 有声 图书
 * Created by huxley on 16/7/12.
 */
public class Ting56Bean implements Serializable{

    /** 图书 封面 */
    public String cover;

    /** 图书 名字 */
    public String name;

    /** 图书 状态 */
    public String status;

    /** 图书 内容 */
    public String content;

    /** 图书 链接 */
    public String url;

    public String info;

    public List<Ting56DetailBean> mTing56DetailBeen;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }

    /** 章节 */
    public static class Ting56DetailBean implements Serializable{

        /** 章节 地址 */
        @MusicPath
        public String url;

        /** 章节 标题 */
        public String title;

        @Override
        public String toString() {
            return GsonUtils.get().toJson(this);
        }
    }
}
