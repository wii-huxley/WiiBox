package com.huxley.wii.wiibox.mvp.main.androidtools.child.treeview;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.widget.treeview.adapter.TreeListViewAdapter;
import com.huxley.wii.wiibox.common.widget.treeview.bean.Node;
import com.huxley.wii.wiitools.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TreeViewActivity extends BaseActivity implements AdapterView.OnItemLongClickListener, TreeListViewAdapter.OnTreeNodeClickListener {

    private ListView mTree;
    private SimpleTreeListAdapter<FileBean> mAdapter;
    private List<FileBean> mDatas;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tree_view;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initData();
        initView();
        initListener();
    }

    private void initListener() {
        mTree.setOnItemLongClickListener(this);
        mAdapter.setOnTreeNodeClickListener(this);
    }

    private void initView() {
        mTree = $(R.id.lvInfo);
        try {
            mTree.setAdapter(mAdapter = new SimpleTreeListAdapter<>(mTree, this, mDatas, 1));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mDatas = new ArrayList<>();
        FileBean bean = new FileBean(1,0,"根目录1");
        mDatas.add(bean);
        bean = new FileBean(2,0,"根目录2");
        mDatas.add(bean);
        bean = new FileBean(3,0,"根目录3");
        mDatas.add(bean);

        bean = new FileBean(4,1,"根目录1-1");
        mDatas.add(bean);
        bean = new FileBean(5,1,"根目录1-2");
        mDatas.add(bean);
        bean = new FileBean(6,1,"根目录1-3");
        mDatas.add(bean);

        bean = new FileBean(7,4,"根目录1-1-1");
        mDatas.add(bean);
        bean = new FileBean(8,4,"根目录1-1-2");
        mDatas.add(bean);

        bean = new FileBean(9,3,"根目录3-1");
        mDatas.add(bean);
        bean = new FileBean(10,3,"根目录3-2");
        mDatas.add(bean);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final EditText et = new EditText(this);
        //DialogFragment
        new AlertDialog.Builder(this)
                .setTitle("add Node")
                .setView(et)
                .setNegativeButton("cancel", null)
                .setPositiveButton("sure", (dialog, which) -> {
                    mAdapter.addExtraNode(position, et.getText().toString());
                }).show();

        return true;
    }

    @Override
    public void onClick(Node node, int position) {
        ToastHelper.showInfo(node.name);
    }
}
