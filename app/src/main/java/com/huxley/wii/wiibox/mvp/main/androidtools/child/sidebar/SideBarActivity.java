package com.huxley.wii.wiibox.mvp.main.androidtools.child.sidebar;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseTitleActivity;

public class SideBarActivity extends BaseTitleActivity {

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        addView(R.layout.activity_side_bar);
    }
}
