package com.huxley.wii.wiibox.mvp.ting56.search;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.base.recyclerview.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 * Ting56SearchFragment
 * Created by huxley on 16/7/13.
 */
public class Ting56SearchFragment extends BaseRecyclerViewFragment<Ting56Bean> implements Ting56SearchContract.View {

    private Ting56SearchContract.Presenter mPresenter;

    public static Ting56SearchFragment newInstance() {
        return new Ting56SearchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wii_layout_list;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public CommonAdapter<Ting56Bean> getAdapter() {
        return new CommonAdapter<Ting56Bean>(getContext(), R.layout.item_book, mData) {
            @Override
            public void convert(ViewHolder holder, Ting56Bean tingBookInfo) {
                holder.setText(R.id.tvName, tingBookInfo.name + tingBookInfo.status);
                holder.setText(R.id.tvContent, tingBookInfo.content);
                holder.setText(R.id.tvInfo, tingBookInfo.info);
                ImageLoaderUtils.setTing56Image(holder.getView(R.id.ivCover), Ting56Model.URL_BASE + tingBookInfo.cover);
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

    private void initView() {
        mSwipeRefreshLayout.setEnabled(false);
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((LinearLayoutManager) mLayoutManager,
                mSwipeRefreshLayout, (CommonAdapter) mAdapter, mPresenter::loadMore));
        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Ting56Bean tingBookInfo = (Ting56Bean) o;
                UIHelper.startChapterListActivity(Ting56SearchFragment.this.getActivity(), tingBookInfo.url, tingBookInfo.name);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    public void setPresenter(Ting56SearchContract.Presenter presenter) {
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
    public void showContent(List<Ting56Bean> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
