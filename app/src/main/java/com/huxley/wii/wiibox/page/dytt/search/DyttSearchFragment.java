package com.huxley.wii.wiibox.page.dytt.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.page.dytt.model.DyttListBean;
import com.huxley.wii.wiitools.base.recyclerview.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 * DyttSearchFragment
 * Created by huxley on 16/7/13.
 */
public class DyttSearchFragment extends BaseRecyclerViewFragment<DyttListBean.MovieInfo> implements DyttSearchContract.View {

    private DyttSearchContract.Presenter mPresenter;

    public static DyttSearchFragment newInstance() {
        return new DyttSearchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wii_layout_list;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initListener();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new CommonAdapter<DyttListBean.MovieInfo>(getContext(), R.layout.item_movie, mData) {
            @Override
            public void convert(ViewHolder holder, DyttListBean.MovieInfo movieInfo) {
                holder.setText(R.id.tvName, movieInfo.name);
                holder.setText(R.id.tvContent, movieInfo.content);
                holder.setVisible(R.id.spacer, holder.getItemPosition() == getItemCount() - 1);
            }
        };
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return $(R.id.swipeRefreshLayout);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    private void initListener() {
        mSwipeRefreshLayout.setEnabled(false);
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((LinearLayoutManager) mLayoutManager,
                mSwipeRefreshLayout, (CommonAdapter) mAdapter, mPresenter::loadMore));
        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                UIHelper.startDyttDetailActivity(getActivity(), (DyttListBean.MovieInfo) o);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    public void setPresenter(DyttSearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showLoading() {
        setRefreshing(true);
    }

    @Override
    public void dismissLoading() {
        setRefreshing(false);
    }

    @Override
    public void showNotNet() {
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(mRecyclerView, mPresenter::reTry);
    }

    @Override
    public void showContent(List<DyttListBean.MovieInfo> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        L.jsonObject(data);
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
