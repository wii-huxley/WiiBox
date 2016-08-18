package com.huxley.wii.wiibox.page.ting56.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.CheckBox;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.musicplayer.PlayStatusEvent;
import com.huxley.wii.wiibox.common.musicplayer.PlayerService;
import com.huxley.wii.wiibox.page.ting56.model.Ting56Bean;
import com.huxley.wii.wiitools.base.recyclerview.BaseRecyclerViewFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * TingPlayFragment
 * Created by huxley on 16/7/13.
 */
public class TingPlayFragment extends BaseRecyclerViewFragment<Ting56Bean.Ting56DetailBean> implements TingPlayContract.View {

    private int currentPosition = 0;
    private boolean isPlaying;
    private int oldPosition = -1;

    private TingPlayContract.Presenter mPresenter;

    public static TingPlayFragment newInstance() {
        return new TingPlayFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ting_play;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        EventBus.getDefault().register(this);
        mPresenter.start();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return $(R.id.recyclerView);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new CommonAdapter<Ting56Bean.Ting56DetailBean>(getContext(), R.layout.item_chapter, mData) {
            @Override
            public void convert(ViewHolder holder, final Ting56Bean.Ting56DetailBean ting56DetailBean) {
                CheckBox checkBox = holder.getView(R.id.cb_name);
                checkBox.setText(ting56DetailBean.title);
                int position = holder.getItemPosition();
                holder.setVisible(R.id.spacer, position == mAdapter.getItemCount() - 1);
                if (position == currentPosition) {
                    checkBox.setChecked(isPlaying);
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                    checkBox.setChecked(false);
                }
                checkBox.setOnClickListener(v -> {
                    if (currentPosition < 0) { // 如果第一次播放
                        currentPosition = position;
                        mPresenter.play(mData, position);
                        isPlaying = true;
                    } else if (currentPosition == position) { // 暂停或者播放
                        if (isPlaying) {
                            mPresenter.pause();
                        } else {
                            mPresenter.resume();
                        }
                        isPlaying = !isPlaying;
                    } else { //选择其他播放
                        mPresenter.play(mData, position);
                        oldPosition = currentPosition;
                        currentPosition = position;
                        isPlaying = true;
                        mAdapter.notifyItemChanged(oldPosition);
                    }
                    mAdapter.notifyItemChanged(currentPosition);
                });
            }
        };
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showLoadErrorInfo(mRecyclerView, mPresenter::reTry);
    }

    @Override
    public void showContent(List<Ting56Bean.Ting56DetailBean> data, boolean isRefresh) {
        if (isRefresh) {
            mData.clear();
        }
        mData.addAll(data);
        mAdapter.notifyDataSetChanged();

        getActivity().startService(new Intent(getActivity(), PlayerService.class));
    }

    @Override
    public void setPresenter(TingPlayContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayStatusEvent event) {
        if (mPresenter.getTag().equals(event.tag)) {
            isPlaying = (event.action == PlayStatusEvent.Action.PLAY);
            currentPosition = event.position;
            mAdapter.notifyDataSetChanged();
        }
    }
}
