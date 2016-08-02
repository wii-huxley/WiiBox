package com.huxley.wii.wiibox.mvp.main.androidtools.child.gobang;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.widget.gobang.GobangListener;
import com.huxley.wii.wiibox.common.widget.gobang.GobangPanel;
import com.huxley.wii.wiitools.base.BaseActivity;


public class GobangActivity extends BaseActivity implements GobangListener {

    private GobangPanel mPanel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gobang;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mPanel = $(R.id.gobangPanel);
        mPanel.setWuZiQiLintener(this);
    }

    @Override
    public void onWin(boolean isWhiteWin) {
        ToastHelper.showInfo(isWhiteWin ? "白棋胜利" : "黑棋胜利");
    }
}
