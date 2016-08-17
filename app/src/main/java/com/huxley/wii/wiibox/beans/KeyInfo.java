package com.huxley.wii.wiibox.beans;

/**
 * Created by huxley on 16/8/10.
 */

public class KeyInfo {

    public BmobBean bmob;
    public WiiBoBean weibo;

    public static class WiiBoBean{
        public String appKey;
        public String redirectUrl;
        public String scope;
    }

    public static class BmobBean {
        public String applicationId;
    }
}
