package com.huxley.wii.wiibox.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huxley on 16/4/22.
 */
public class GankDataDetailBean {

    public boolean error;
    public ResultsBean results;
    public List<String> category;

    public static class ResultsBean {

        public List<ItemBean> Android;

        public List<ItemBean> App;

        public List<ItemBean> iOS;

        @SerializedName("休息视频")
        public List<ItemBean> restVideo;

        @SerializedName("前端")
        public List<ItemBean> frontEnd;

        @SerializedName("瞎推荐")
        public List<ItemBean> recommend;

        @SerializedName("福利")
        public List<ItemBean> photo;

        @SerializedName("拓展资源")
        public List<ItemBean> expandResources;

        public static class ItemBean {
            public String _id;
            public String createdAt;
            public String desc;
            public String publishedAt;
            public String source;
            public String type;
            public String url;
            public boolean used;
            public String who;

            @Override
            public String toString() {
                return "ItemBean{" +
                        "_id='" + _id + '\'' +
                        ", createdAt='" + createdAt + '\'' +
                        ", desc='" + desc + '\'' +
                        ", publishedAt='" + publishedAt + '\'' +
                        ", source='" + source + '\'' +
                        ", type='" + type + '\'' +
                        ", url='" + url + '\'' +
                        ", used=" + used +
                        ", who='" + who + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "Android=" + Android +
                    ", App=" + App +
                    ", iOS=" + iOS +
                    ", restVideo=" + restVideo +
                    ", frontEnd=" + frontEnd +
                    ", recommend=" + recommend +
                    ", photo=" + photo +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GankDataDetailBean{" +
                "error=" + error +
                ", results=" + results +
                ", category=" + category +
                '}';
    }
}
