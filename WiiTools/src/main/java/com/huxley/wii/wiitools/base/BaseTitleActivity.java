package com.huxley.wii.wiitools.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.huxley.wii.wiitools.R;
import com.huxley.wii.wiitools.view.TitleFragment;

/**
 *
 * Created by huxley on 16/7/3.
 */
public class BaseTitleActivity extends BaseActivity {

    protected Toolbar toolbar;
    private LinearLayout baseTitleContent;
    private View baseTitleContentView;
    public TitleFragment titleFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.wii_layout_title;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        baseTitleContent = $(R.id.baseTitleContent);

        titleFragment = (TitleFragment) getSupportFragmentManager().findFragmentById(R.id.layout_title);
        titleFragment.setSupportActionBar(this);
    }

    protected void addView(@LayoutRes int layoutId) {
        baseTitleContentView = mInflater.inflate(layoutId, null);
        baseTitleContent.addView(baseTitleContentView);
    }

    public <V extends View> V $1(int id) {
        return (V)baseTitleContentView.findViewById(id);
    }
}
