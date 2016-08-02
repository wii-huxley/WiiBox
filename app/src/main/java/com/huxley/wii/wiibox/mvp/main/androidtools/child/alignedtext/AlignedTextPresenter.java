package com.huxley.wii.wiibox.mvp.main.androidtools.child.alignedtext;

import android.support.annotation.NonNull;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * Created by huxley on 16/7/2.
 */
public class AlignedTextPresenter implements AlignedTextContract.Presenter {

    private AlignedTextContract.View alignedTextView;

    public AlignedTextPresenter(@NonNull AlignedTextContract.View alignedTextView) {
        this.alignedTextView = checkNotNull(alignedTextView, "taskDetailView cannot be null!");
        this.alignedTextView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
