package com.huxley.wii.wiibox.mvp.main.gank;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankModel;
import com.huxley.wii.wiitools.base.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.List;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * create an instance of this fragment.
 */
public class GankFragment extends BaseRecyclerViewFragment<GankInfo> implements SwipeRefreshLayout.OnRefreshListener, GankContract.View {

    private GankContract.Presenter mGankPresenter;

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initListener();
        mGankPresenter.start();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return  new CommonAdapter<GankInfo>(getContext(), R.layout.item_gank, mData) {
            @Override
            public void convert(ViewHolder holder, GankInfo gankBean) {
                holder.setText(R.id.tvDate, gankBean.date);
                holder.getView(R.id.ivPhoto);
                GankInfo.ResultsBean results = gankBean.results;
                String url = "null";
                if (results != null) {
                    url = results.photo.get(0).url;
                }
                ImageLoaderUtils.setGankImage(holder.getView(R.id.ivPhoto), url);
                holder.setVisible(R.id.spacer, holder.getItemPosition() == (getItemCount() - 1));
            }
        };
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return $(R.id.swipeRefreshLayout);
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    }

    private void initView() {
        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setTitle("Gank");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    private void initListener() {
        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(view.findViewById(R.id.ivPhoto), getString(R.string.transition_pic))
                );
                UIHelper.startGankDataDetailActivity(getActivity(), (GankInfo) o, position, optionsCompat);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mGankPresenter.loadGank(true);
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        this.mGankPresenter = checkNotNull(presenter);
    }

    public void update(int position) {
        if (mData.get(position).results != null) {
            return;
        }
        GankInfo gankInfo = GankModel.getInstance().getGankInfo(mData.get(position).date);
        mData.remove(position);
        mData.add(position, gankInfo);
        mAdapter.notifyItemChanged(position);
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
        SnackbarHelper.showNoNetInfo(mRecyclerView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(mRecyclerView, () -> mGankPresenter.loadGank(true));
    }

    @Override
    public void showContent(List<GankInfo> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }
}
