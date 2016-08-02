package com.huxley.wii.wiibox.mvp.main.androidtools.child.treeview;


import com.huxley.wii.wiibox.common.widget.treeview.annotation.TreeNodeId;
import com.huxley.wii.wiibox.common.widget.treeview.annotation.TreeNodeLabel;
import com.huxley.wii.wiibox.common.widget.treeview.annotation.TreeNodePid;

/**
 * Created by LeiJin01 on 2016/6/23.
 */
public class FileBean {

    @TreeNodeId
    public int id;
    @TreeNodePid
    public int pId;
    @TreeNodeLabel
    public String label;

    public String desc;

    public FileBean() {
    }

    public FileBean(int id, int pId, String label) {
        this.id = id;
        this.pId = pId;
        this.label = label;
    }
}
