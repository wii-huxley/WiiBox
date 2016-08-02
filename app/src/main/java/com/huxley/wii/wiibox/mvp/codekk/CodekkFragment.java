package com.huxley.wii.wiibox.mvp.codekk;

import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiitools.base.BaseListFragment;
import com.huxley.wii.wiitools.common.Utils.DateUtils;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.thefinestartist.finestwebview.FinestWebView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

/**
 *
 * Created by huxley on 16/7/31.
 */

public class CodekkFragment extends BaseListFragment<CodekkProjectBean> implements CodekkContract.View{

    private CodekkContract.Presenter mPresenter;

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((StaggeredGridLayoutManager) mLinearLayoutManager,
                mSwipeRefreshLayout, mAdapter, () -> mPresenter.loadMore()));

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                new FinestWebView.Builder(getContext()).show(((CodekkProjectBean) o).codeKKUrl);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });
    }

    @Override
    public void setPresenter(CodekkContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected CommonAdapter<CodekkProjectBean> getAdapter() {
        return new CommonAdapter<CodekkProjectBean>(getContext(), R.layout.item_codekk, mDatas) {
            @Override
            public void convert(ViewHolder holder, CodekkProjectBean codekkBean) {
                holder.setText(R.id.tvName, codekkBean.projectName);
                holder.setText(R.id.tvContent, codekkBean.desc);
                holder.setText(R.id.tvDate, DateUtils.formatCodekkDate(codekkBean.updateTime));
                holder.setVisible(R.id.spacer, holder.getItemPosition() == getItemCount() - 1);
            }
        };
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
    }

    @Override
    public void onRefresh() {
        mPresenter.refresh();
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
    public void setContent(List<CodekkProjectBean> codekkProjectBean, boolean isRefresh, boolean isFirst) {
        super.showContentView(isFirst);
        if (isRefresh) {
            mDatas.clear();
        }
        mDatas.addAll(codekkProjectBean);
        mAdapter.notifyDataSetChanged();
    }
}