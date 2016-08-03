package com.huxley.wii.wiibox.mvp.main.gank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.common.utils.WiiLog;
import com.huxley.wii.wiibox.mvp.main.gank.detail.GankDataDetailActivity;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankModel;
import com.huxley.wii.wiitools.base.BaseNetFragment;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * create an instance of this fragment.
 */
public class GankFragment extends BaseNetFragment implements SwipeRefreshLayout.OnRefreshListener, GankContract.View {

    private CommonAdapter<GankInfo> mAdapter;
    private GankContract.Presenter mGankPresenter;
    private List<GankInfo> mGankInfos;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        addView(R.layout.fragment_gank);
        initView();
        initListener();
        mGankPresenter.start();
    }

    private void initView() {
        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setTitle("Gank");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        RecyclerView recyclerView = $1(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter = new CommonAdapter<GankInfo>(getContext(), R.layout.item_gank, mGankInfos = new ArrayList<>()) {
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
        });

        swipeRefreshLayout = $1(R.id.swipeRefreshLayout);
        UIHelper.setSwipeRefreshStyles(swipeRefreshLayout, this);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Intent intent = GankDataDetailActivity.newIntent(getActivity(), (GankInfo) o, position);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(view.findViewById(R.id.ivPhoto), getString(R.string.transition_pic))
                );
                ActivityCompat.startActivityForResult(getActivity(), intent,
                        Constant.RequestCode.GANK_DETAIL_DATA, optionsCompat.toBundle());
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        WiiLog.i("---- GankFragment : onRefresh ----");
        mGankPresenter.loadGank(true);
    }

    @Override
    protected void isErrorToLoad() {
        super.isErrorToLoad();

        mGankPresenter.loadGank(false);

    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        this.mGankPresenter = checkNotNull(presenter);
    }

    @Override
    public void setProgress(boolean isShow){
        if (isShow) {
            super.showProgress();
        } else {
            super.dismissProgress();
        }
    }

    @Override
    public void isEmptyView() {
        swipeRefreshLayout.setRefreshing(false);
        super.showEmptyView();
    }

    @Override
    public void isContentView(List<GankInfo> gankList, boolean isFirst) {
        swipeRefreshLayout.setRefreshing(false);
        super.showContentView();
        if (isFirst) {
            mGankInfos.clear();
        }
        mGankInfos.addAll(0, gankList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void isErrorView() {
        super.showErrorView();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void isNoNetView() {
        super.showNoNetView();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void update(int position) {
        GankInfo gankInfo = GankModel.getInstance().getGankInfo(mGankInfos.get(position).date);
        mGankInfos.remove(position);
        mGankInfos.add(position, gankInfo);
        mAdapter.notifyItemChanged(position);
    }
}
