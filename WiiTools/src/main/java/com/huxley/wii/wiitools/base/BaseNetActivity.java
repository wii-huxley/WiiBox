package com.huxley.wii.wiitools.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huxley.wii.wiitools.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by huxley on 16/7/10.
 */
public class BaseNetActivity extends BaseTitleActivity {

    private TextView tvError;
    private TextView tvNoContent;
    private TextView tvNoNet;
    private FrameLayout baseNetContent;
//    private KProgressHUD mKProgressHUD;
    private View contentView1;
    private AVLoadingIndicatorView loadView;

    @Override
    protected void created(Bundle savedInstanceState) {
        addView(R.layout.wii_layout_net);

        tvError = $(R.id.tvError);
        tvNoContent = $(R.id.tvNoContent);
        tvNoNet = $(R.id.tvNoNet);
        baseNetContent = $(R.id.baseNetContent);
        loadView = $(R.id.loadView);
//        mKProgressHUD = KProgressHUD.create(this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.tvError || id == R.id.tvNoContent || id == R.id.tvNoNet) {
                    isErrorToLoad();
                }
            }
        };
        tvNoContent.setOnClickListener(clickListener);
        tvError.setOnClickListener(clickListener);
        tvNoNet.setOnClickListener(clickListener);
    }

    protected void isErrorToLoad() {

    }

    protected void addView1(@LayoutRes int layoutId) {
        contentView1 = mInflater.inflate(layoutId, null);
        baseNetContent.addView(contentView1);
    }

    protected void showProgress() {
//        mKProgressHUD.show();
        loadView.setVisibility(View.VISIBLE);
    }

    protected void dismissProgress() {
//        mKProgressHUD.dismiss();
        loadView.setVisibility(View.GONE);
    }

    protected void showErrorView() {
        tvError.setVisibility(View.VISIBLE);
        tvNoContent.setVisibility(View.GONE);
        tvNoNet.setVisibility(View.GONE);
        baseNetContent.setVisibility(View.GONE);
    }

    protected void showEmptyView() {
        tvError.setVisibility(View.GONE);
        tvNoContent.setVisibility(View.VISIBLE);
        tvNoNet.setVisibility(View.GONE);
        baseNetContent.setVisibility(View.GONE);
    }

    protected void showNoNetView() {
        tvError.setVisibility(View.GONE);
        tvNoContent.setVisibility(View.GONE);
        tvNoNet.setVisibility(View.VISIBLE);
        baseNetContent.setVisibility(View.GONE);
    }

    protected void showContentView() {
        tvError.setVisibility(View.GONE);
        tvNoContent.setVisibility(View.GONE);
        tvNoNet.setVisibility(View.GONE);
        baseNetContent.setVisibility(View.VISIBLE);
    }

    public <V extends View> V $2(int id) {
        return (V)contentView1.findViewById(id);
    }
}
