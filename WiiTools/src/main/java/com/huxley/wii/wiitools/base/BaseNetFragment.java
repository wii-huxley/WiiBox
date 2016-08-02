package com.huxley.wii.wiitools.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.huxley.wii.wiitools.R;
import com.wang.avi.AVLoadingIndicatorView;


/**
 *
 * Created by huxley on 16/5/10.
 */
public class BaseNetFragment extends BaseFragment {

    private FrameLayout baseNetContent;
    private View contentView;
    private TextView tvNoNet;
    private TextView tvNoContent;
    private TextView tvError;
//    private KProgressHUD mKProgressHUD;
    private AVLoadingIndicatorView loadView;

    @Override
    protected int getLayoutId() {
        return R.layout.wii_layout_net;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        tvNoNet = $(R.id.tvNoNet);
        tvNoContent = $(R.id.tvNoContent);
        tvError = $(R.id.tvError);
        baseNetContent = $(R.id.baseNetContent);
        loadView = $(R.id.loadView);
//        mKProgressHUD = KProgressHUD.create(getActivity())
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f);
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
        tvError.setVisibility(View.GONE);
        tvNoContent.setVisibility(View.GONE);
        tvNoNet.setVisibility(View.GONE);
        baseNetContent.setVisibility(View.GONE);
    }

    protected void addView(@LayoutRes int layoutId) {
        contentView = mInflater.inflate(layoutId, null);
        baseNetContent.addView(contentView);
    }

    protected void showProgress() {
        loadView.setVisibility(View.VISIBLE);
    }

    protected void dismissProgress() {
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

    public <V extends View> V $1(int id) {
        return (V)contentView.findViewById(id);
    }
}
