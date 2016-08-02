package com.huxley.wii.wiibox.mvp.dytt.detail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttModel;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttDetailInfo;
import com.huxley.wii.wiibox.mvp.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.BaseNetFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import java.util.List;

public class DyttDetailFragment extends BaseNetFragment implements DyttDetailContract.View {

    private DyttDetailContract.Presenter mDyttDetailPresenter;
    private ImageView ivPhoto;
    private DyttListBean.MovieInfo mMovieInfo;
    private LinearLayout llContent;
    private DialogPlus downloadDialog;

    public static DyttDetailFragment newInstance(DyttListBean.MovieInfo movieInfo) {
        Bundle args = new Bundle();
        args.putSerializable(Constant.Extra.DATE, movieInfo);
        DyttDetailFragment fragment = new DyttDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(@NonNull DyttDetailContract.Presenter presenter) {
        mDyttDetailPresenter = presenter;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        addView(R.layout.fragment_dyttdetail);
        getData();
        initView();
        initListener();
        mDyttDetailPresenter.loadData(mMovieInfo.url);
    }

    private void initListener() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnDownload:
                        showDownloadDialog();
                        break;
                }
            }
        };
        UIHelper.setOnClickListener(clickListener, $1(R.id.btnDownload));
    }

    private void getData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mMovieInfo = (DyttListBean.MovieInfo) arguments.getSerializable(Constant.Extra.DATE);
        }
    }

    private void initView() {
        ivPhoto = $1(R.id.ivPhoto);
        llContent = $1(R.id.llContent);
        Toolbar toolbar = $1(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finishAfterTransition();
            }
        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = $1(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(mMovieInfo.name);

    }

    @Override
    public void setContent(DyttDetailInfo movieDetailInfo) {
        List<String> pics = movieDetailInfo.pics;
        if (pics != null && pics.size() > 0) {
            String remove = pics.remove(0);
            ImageLoaderUtils.setGankBigImage(ivPhoto, remove);
        }


        downloadDialog = DialogPlus.newDialog(getContext())
                .setAdapter(new CommonAdapter<String>(getContext(), R.layout.item_diglog_downloadlist, movieDetailInfo.urls) {
                    @Override
                    public void convert(ViewHolder holder, String name) {
                        holder.setText(R.id.tvName, name);
                    }
                })
                .setContentHolder(new ListHolder())
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setHeader(R.layout.layout_dialog_header)
                .setExpanded(true)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        onClickDownload(view, (String) item);
                    }
                })
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
    }

    private void showDownloadDialog() {
        if (downloadDialog == null) {
            ToastHelper.showInfo(R.string.str_loading);
            return;
        }
        downloadDialog.show();
    }

    public static final String XUNLEI_PACKAGENAME = "com.xunlei.downloadprovider";

    public void onClickDownload(View view, String ftpUrl) {
        if (TextUtils.isEmpty(ftpUrl)) {
            Snackbar.make(view, getContext().getString(R.string.un_ftpurl_label), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            return;
        }

        if (checkIsInstall(getContext(), XUNLEI_PACKAGENAME)) {
            // 唤醒迅雷
            getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(DyttModel.getInstance().encode(ftpUrl))));
        } else {
            Snackbar.make(view, getContext().getString(R.string.un_install_xunlei_label), Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private boolean checkIsInstall(Context paramContext, String paramString) {
        if ((paramString == null) || ("".equals(paramString)))
            return false;
        try {
            paramContext.getPackageManager().getApplicationInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }
}
