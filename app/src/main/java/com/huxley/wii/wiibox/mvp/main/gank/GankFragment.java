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
import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoaderUtils;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankEvent;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankInfo;
import com.huxley.wii.wiibox.mvp.main.gank.model.GankModel;
import com.huxley.wii.wiitools.base.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.factory.DialogFactory;
import com.huxley.wii.wiitools.common.helper.CollectionHelper;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.orhanobut.dialogplus.DialogPlus;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * create an instance of this fragment.
 */
public class GankFragment extends BaseRecyclerViewFragment<GankInfo> implements SwipeRefreshLayout.OnRefreshListener, GankContract.View {

    private GankContract.Presenter mGankPresenter;
    private DialogPlus mDialogPlus;

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

        EventBus.getDefault().register(this);
        initView();
        initListener();
        mGankPresenter.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                ImageView ivPhoto = holder.getView(R.id.ivPhoto);
                ImageLoaderUtils.setGankImage(ivPhoto, url);
                ivPhoto.setTransitionName(gankBean.date);
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
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    private void initView() {
        Toolbar toolbar = UIHelper.createToolbar((AppCompatActivity) getActivity(), rootView);
        toolbar.setTitle(R.string.str_gank);
    }

    private void initListener() {
        ((CommonAdapter)mAdapter).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(view.findViewById(R.id.ivPhoto), ((GankInfo)o).date)
                );
                UIHelper.startGankDataDetailActivity(getActivity(), (GankInfo) o, position, optionsCompat);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                if (((GankInfo) o).results == null) {
                    SnackbarHelper.showInfo(mRecyclerView, "未检查到图片");
                } else {
                    openView = view.findViewById(R.id.ivPhoto);
                    openUrl = ((GankInfo) o).results.photo.get(0).url;
                    showSettingDialog();
                }
                return true;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    String openUrl;
    View openView;
    private void showSettingDialog() {
        if (mDialogPlus == null) {
            mDialogPlus = DialogFactory.newInstance(getContext(), "列表选项", "单选", false, CollectionHelper.createList("查看大图", "保存本地"), null, (dialog, item, view, position) -> {
                mDialogPlus.dismiss();
                switch (position) {
                    case 0:
                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(),
                                Pair.create(openView, openUrl)
                        );
                        UIHelper.startPhotoActivity(getActivity(), openUrl, optionsCompat);
                        break;
                    case 1:

                        break;
                }
            });
        }
        mDialogPlus.show();
    }

    @Override
    public void onRefresh() {
        mGankPresenter.loadGank(true);
    }

    @Override
    public void setPresenter(GankContract.Presenter presenter) {
        this.mGankPresenter = checkNotNull(presenter);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GankEvent event) {
        L.jsonObject(event);
        GankInfo gankInfo = GankModel.getInstance().getGankInfo(mData.get(event.position).date);
        mData.remove(event.position);
        mData.add(event.position, gankInfo);
        mAdapter.notifyItemChanged(event.position);
    }
}
