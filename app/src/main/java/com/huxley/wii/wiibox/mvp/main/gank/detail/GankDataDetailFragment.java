package com.huxley.wii.wiibox.mvp.main.gank.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.common.utils.WiiLog;
import com.huxley.wii.wiibox.mvp.main.gank.GankInfo;
import com.huxley.wii.wiibox.mvp.main.gank.GankModel;
import com.huxley.wii.wiitools.base.BaseNetFragment;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.MultiItemCommonAdapter;
import com.zhy.base.adapter.recyclerview.MultiItemTypeSupport;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxley on 16/7/15.
 */
public class GankDataDetailFragment extends BaseNetFragment implements GankDataDetailContract.View{

    private GankInfo mGankInfo;
    private ImageView ivPhoto;
    private GankDataDetailContract.Presenter mPresenter;
    private MultiItemCommonAdapter<Object> mAdapter;
    private List<Object> mGankInfos;

    public static GankDataDetailFragment newInstance(GankInfo gankInfo) {
        GankDataDetailFragment fragment = new GankDataDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constant.Extra.DATE, gankInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mGankInfos = new ArrayList<>();
        addView(R.layout.fragment_gank_data_detail);
        getData();
        initView();
        initListener();
        initData();
    }

    private void getData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGankInfo = (GankInfo) arguments.getSerializable(Constant.Extra.DATE);
        }
    }

    private void initView() {
        ivPhoto = $1(R.id.ivPhoto);

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
        collapsingToolbar.setTitle(mGankInfo.date);

        RecyclerView recyclerView = $1(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter = new MultiItemCommonAdapter<Object>(getContext(), mGankInfos, new MultiItemTypeSupport<Object>() {
            @Override
            public int getLayoutId(int itemType) {
                switch (itemType) {
                    case 0:
                        return R.layout.item_gank_title;
                    case 1:
                        return R.layout.item_gank_data;
                    default:
                        return 0;
                }
            }

            @Override
            public int getItemViewType(int position, Object gankInfo) {
                if (gankInfo instanceof String) {
                    return 0;
                } else if (gankInfo instanceof GankInfo.ResultsBean.ItemBean) {
                    return 1;
                } else {
                    return 2;
                }
            }
        }) {
            @Override
            public void convert(ViewHolder holder, Object gankInfo) {
                switch (holder.getItemViewType()) {
                    case 0:
                        holder.setText(R.id.tvName, ((String) gankInfo));
                        break;
                    case 1:
                        GankInfo.ResultsBean.ItemBean item = (GankInfo.ResultsBean.ItemBean) gankInfo;
                        holder.setText(R.id.tvArticleName, item.desc);
                        holder.setText(R.id.tvName, item.who);
                        holder.setText(R.id.tvSource, item.source);
                        holder.setText(R.id.tvDate, item.publishedAt);
                        break;
                }
            }
        });
    }

    private void initListener() {
        UIHelper.setOnClickListener(this, ivPhoto);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                WiiLog.i(position);
                if (mAdapter.getItemViewType(position) == 1) {
                    GankInfo.ResultsBean.ItemBean item = (GankInfo.ResultsBean.ItemBean) o;
                    UIHelper.openWebView(item.url, item.desc, getContext());
                }
            }
            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });
    }

    private void initData() {
        if (mGankInfo.results != null) {
            isContentView(GankModel.getInstance().getGankList(mGankInfo));
        }
        mPresenter.loadData(mGankInfo.date);
    }


    @Override
    public void isContentView(List<Object> gankInfos) {
        ImageLoaderUtils.setGankBigImage(ivPhoto, (String) gankInfos.remove(0));
        mGankInfos.addAll(gankInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void isErrorToLoad() {
        super.isErrorToLoad();

        mPresenter.loadData(mGankInfo.date);
    }

    @Override
    public void isEmptyView() {
        super.showEmptyView();
    }

    @Override
    public void isErrorView() {
        super.showErrorView();
    }

    @Override
    public void isNoNetView() {
        super.showNoNetView();
    }

    @Override
    public void setProgress(boolean isShow) {
        if (isShow) {
            super.showProgress();
        } else {
            super.dismissProgress();
        }
    }

    @Override
    public void setPresenter(GankDataDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
