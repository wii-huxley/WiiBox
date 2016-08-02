package com.huxley.wii.wiibox.beans;

/**
 * Created by huxley on 16/6/16.
 */
public class FolderBean {

    /**
     * 当前文件夹路径
     */
    public String dir;

    public String firstImgPath;

    public String name;

    public int count;


    public void setDir(String dir)
    {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf + 1);
    }
}
