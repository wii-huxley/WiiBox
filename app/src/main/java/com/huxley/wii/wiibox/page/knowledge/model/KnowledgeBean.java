package com.huxley.wii.wiibox.page.knowledge.model;

import com.huxley.wii.wiibox.common.widget.treeview.annotation.TreeNodeId;
import com.huxley.wii.wiibox.common.widget.treeview.annotation.TreeNodeLabel;
import com.huxley.wii.wiibox.common.widget.treeview.annotation.TreeNodePid;

import java.io.Serializable;

/**
 * KnowledgeBean
 * Created by LeiJin01 on 2016/8/15.
 */
public class KnowledgeBean implements Serializable {

    @TreeNodeId
    public int id;
    @TreeNodePid
    public int pId;
    @TreeNodeLabel
    public String name;

    public String path;

    public String desc;
}
