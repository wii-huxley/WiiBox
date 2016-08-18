package com.huxley.wii.wiibox.page.codekk.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.page.codekk.model.CodekkProjectBean;
import com.huxley.wii.wiitools.base.recyclerview.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.Utils.DateUtils;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.huxley.wii.wiitools.listener.RecyclerViewScrollListener;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class CodekkSearchFragment extends BaseRecyclerViewFragment<CodekkProjectBean> implements CodekkSearchContract.View{

    private CodekkSearchContract.Presenter mCodekkSearchPresenter;

    public static CodekkSearchFragment newInstance() {
        return new CodekkSearchFragment();
    }

    @Override
    public void setPresenter(@NonNull CodekkSearchContract.Presenter presenter) {
        mCodekkSearchPresenter = checkNotNull(presenter);
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

    private void initListener() {
        mRecyclerView.addOnScrollListener(RecyclerViewScrollListener.getLoadMoreListener((StaggeredGridLayoutManager) mLayoutManager,
                mSwipeRefreshLayout, (CommonAdapter) mAdapter, this::loadMore));

        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                UIHelper.openWebView(((CodekkProjectBean) o).codeKKUrl, ((CodekkProjectBean) o).projectName, getContext());
//                UIHelper.startCodekkDetailActivity(getActivity(), ((CodekkProjectBean) o)._id, ((CodekkProjectBean) o).projectName);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });
        mSwipeRefreshLayout.setEnabled(false);
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
        SnackbarHelper.showLoadErrorInfo(mRecyclerView, mCodekkSearchPresenter::reTry);
    }

    @Override
    public void showContent(List<CodekkProjectBean> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new CommonAdapter<CodekkProjectBean>(getContext(), R.layout.item_codekk, mData) {
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
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return $(R.id.swipeRefreshLayout);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
    }

    protected void loadMore() {
        if (!mCodekkSearchPresenter.hasMore()) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(true);
        mCodekkSearchPresenter.loadMore();
    }

    @Override
    public void clearContent() {
        mData.clear();
        mAdapter.notifyDataSetChanged();
    }
}
