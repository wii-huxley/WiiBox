package com.huxley.wii.wiibox.mvp.dytt.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttDetailInfo;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.Tools;
import com.huxley.wii.wiitools.common.factory.DialogFactory;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.List;

public class DyttDetailFragment extends BaseFragment implements DyttDetailContract.View {

    private DyttDetailContract.Presenter mDyttDetailPresenter;
    private ImageView                    ivPhoto;
    private LinearLayout                 llContent;
    private DialogPlus                   downloadDialog;
    private CollapsingToolbarLayout      collapsingToolbar;
    public static final String XUNLEI_PACKAGENAME = "com.xunlei.downloadprovider";

    public static DyttDetailFragment newInstance() {
        return new DyttDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dyttdetail;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initListener();
        mDyttDetailPresenter.start();
    }

    private void initListener() {
        View.OnClickListener clickListener = v -> {
            switch (v.getId()) {
                case R.id.btnDownload:
                    if (downloadDialog == null) {
                        ToastHelper.showInfo(R.string.str_prompt_loading);
                        return;
                    }
                    downloadDialog.show();
                    break;
            }
        };
        UIHelper.setOnClickListener(clickListener, $(R.id.btnDownload));
    }

    private void initView() {
        ivPhoto = $(R.id.ivPhoto);
        llContent = $(R.id.llContent);

        Toolbar toolbar = UIHelper.createToolbar((AppCompatActivity) getActivity(), rootView);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().finishAfterTransition());

        collapsingToolbar = $(R.id.collapsingToolbar);
    }

    @Override
    public void setTitle(String title) {
        collapsingToolbar.setTitle(title);
    }

    public void onClickDownload(View view, String ftpUrl) {
        if (TextUtils.isEmpty(ftpUrl)) {
            SnackbarHelper.showInfo(view, getContext().getString(R.string.dytt_un_ftpurl));
            return;
        }
        if (Tools.checkIsInstall(getContext(), XUNLEI_PACKAGENAME)) {
            UIHelper.setXunLeiActivity(getContext(), Uri.parse(DyttModel.getInstance().encode(ftpUrl)));
        } else {
            SnackbarHelper.showInfo(view, getContext().getString(R.string.dytt_un_install_xunlei));
        }
    }

    @Override
    public void setPresenter(@NonNull DyttDetailContract.Presenter presenter) {
        mDyttDetailPresenter = presenter;
    }

    @Override
    public void showLoading() {
        isLoading(true);
    }

    @Override
    public void dismissLoading() {
        isLoading(false);
    }

    @Override
    public void showNotNet() {
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(rootView, mDyttDetailPresenter::start);
    }

    @Override
    public void showContent(DyttDetailInfo data, boolean isRefresh) {
        List<String> pics = data.pics;
        if (pics != null && pics.size() > 0) {
            String remove = pics.remove(0);
            ImageLoaderUtils.setGankBigImage(ivPhoto, remove);
        }
        downloadDialog = DialogFactory.newInstance(getContext(), "下载列表", "单选", true, data.urls, null,
                (dialog, item, view, position) -> onClickDownload(view, (String) item));
    }
}
