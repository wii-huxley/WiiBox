package com.huxley.wii.wiibox.mvp.main.androidtools.child.alignedtext;

import android.os.Bundle;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseTitleActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

public class AlignedTextActivity extends BaseTitleActivity {

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        addView(R.layout.activity_aligned_text);

        AlignedTextFragment alignedTextFragment = (AlignedTextFragment) getSupportFragmentManager().findFragmentById(R.id.contentFragment);

        if (alignedTextFragment == null) {
            alignedTextFragment = AlignedTextFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), alignedTextFragment, R.id.contentFragment);
        }

        new AlignedTextPresenter(alignedTextFragment);
    }
}
