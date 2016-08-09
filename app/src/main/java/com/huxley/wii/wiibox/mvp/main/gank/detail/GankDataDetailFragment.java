package com.huxley.wii.wiibox.mvp.main.gank.detail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiitools.base.recyclerview.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.MultiItemCommonAdapter;
import com.zhy.base.adapter.recyclerview.MultiItemTypeSupport;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 *
 * Created by huxley on 16/7/15.
 */
public class GankDataDetailFragment extends BaseRecyclerViewFragment<Object> implements GankDataDetailContract.View {

    private ImageView                        ivPhoto;
    private GankDataDetailContract.Presenter mPresenter;
    private CollapsingToolbarLayout          collapsingToolbar;

    public static GankDataDetailFragment newInstance() {
        return new GankDataDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_data_detail;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initListener();
        mPresenter.start();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new MultiItemCommonAdapter<Object>(getContext(), mData, new MultiItemTypeSupport<Object>() {
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
        };
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    private void initView() {
        ivPhoto = $(R.id.ivPhoto);


        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> getActivity().finishAfterTransition());
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = $(R.id.collapsingToolbar);
    }

    @Override
    public void showTitle(String date) {
        collapsingToolbar.setTitle(date);
        ivPhoto.setTransitionName(date);
    }

    private void initListener() {
        UIHelper.setOnClickListener(this, ivPhoto);
        ((MultiItemCommonAdapter) mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
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

    @Override
    public void setPresenter(GankDataDetailContract.Presenter presenter) {
        this.mPresenter = presenter;
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
        SnackbarHelper.showNoNetInfo(mRecyclerView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(mRecyclerView, mPresenter::loadData);
    }

    @Override
    public void showContent(List<Object> gankInfos, boolean isRefresh) {
        ImageLoaderUtils.setGankBigImage(ivPhoto, (String) gankInfos.remove(0));
        mData.addAll(gankInfos);
        mAdapter.notifyDataSetChanged();
    }
}
