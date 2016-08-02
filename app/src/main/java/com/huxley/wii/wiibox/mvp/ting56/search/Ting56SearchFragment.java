package com.huxley.wii.wiibox.mvp.ting56.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.base.BaseListFragment;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 *
 * Created by huxley on 16/7/13.
 */
public class Ting56SearchFragment extends BaseListFragment<Ting56Bean> implements Ting56SearchContract.View {

    private Ting56SearchContract.Presenter mPresenter;

    public static Ting56SearchFragment newInstance() {
        return new Ting56SearchFragment();
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    @Override
    protected CommonAdapter<Ting56Bean> getAdapter() {
        return new CommonAdapter<Ting56Bean>(getContext(), R.layout.item_book, mDatas) {
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
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    private void initView() {
        mSwipeRefreshLayout.setEnabled(false);
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((LinearLayoutManager) mLinearLayoutManager,
                mSwipeRefreshLayout, mAdapter, () -> mPresenter.loadMore()));
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
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
    public void onRefresh() {

    }

    @Override
    public void isNoNetView(boolean isFirst) {
        super.showNoNetView(isFirst);
        if (!isFirst) {
            ToastHelper.showInfo("没有网络");
        }
    }

    @Override
    public void isEmptyView(boolean isFirst) {
        super.showEmptyView(isFirst);
        if (!isFirst) {
            ToastHelper.showInfo("没有内容");
        }
    }

    @Override
    public void isErrorView(boolean isFirst) {
        super.showErrorView(isFirst);
        if (!isFirst) {
            ToastHelper.showInfo("加载错误");
        }
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
    protected void isErrorToLoad() {
        super.isErrorToLoad();
        mPresenter.start();
    }

    @Override
    public void setContent(boolean isRefresh, boolean isFirst, List<Ting56Bean> ting56DetailBeen) {
        super.showContentView(isFirst);
        if (isRefresh) {
            mDatas.clear();
        }
        mDatas.addAll(ting56DetailBeen);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(Ting56SearchContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
