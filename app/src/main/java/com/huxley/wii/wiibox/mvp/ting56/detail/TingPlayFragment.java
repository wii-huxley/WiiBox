package com.huxley.wii.wiibox.mvp.ting56.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.CheckBox;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.musicplayer.PlayEvent;
import com.huxley.wii.wiibox.common.musicplayer.PlayStatusEvent;
import com.huxley.wii.wiibox.common.musicplayer.PlayerService;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Bean;
import com.huxley.wii.wiitools.base.BaseNetFragment;
import com.thefinestartist.utils.log.L;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by huxley on 16/7/13.
 */
public class TingPlayFragment extends BaseNetFragment implements TingPlayContract.View {

    private static final String KEY_URL = "url";
    private String mUrl;
    private int currentPosition = 0;
    private boolean isPlaying;
    private int oldPosition = -1;

    private CommonAdapter<Ting56Bean.Ting56DetailBean> mAdapter;
    private List<Ting56Bean.Ting56DetailBean> mTing56DetailBeen;
    private TingPlayContract.Presenter presenter;

    public static TingPlayFragment newInstance(String url) {
        TingPlayFragment fragment = new TingPlayFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        super.addView(R.layout.fragment_ting_play);

        EventBus.getDefault().register(this);
        initData();
        initView();
        initListener();

        presenter.openBook(mUrl);
    }

    private void initData() {
        mTing56DetailBeen = new ArrayList<>();
        Bundle savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString(KEY_URL, "");
        }
    }

    private void initView() {

        RecyclerView recyclerView = $1(R.id.recyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter = new CommonAdapter<Ting56Bean.Ting56DetailBean>(getContext(), R.layout.item_chapter, mTing56DetailBeen) {
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
                    if (currentPosition < 0) { //如果第一次播放
                        currentPosition = position;
                        play(position);
                        isPlaying = true;
                    } else if (currentPosition == position) { //暂停或者播放
                        if (isPlaying){
                            pause();
                        } else {
                            resume();
                        }
                        isPlaying = !isPlaying;
                    } else { //选择其他播放
                        play(position);
                        oldPosition = currentPosition;
                        currentPosition = position;
                        isPlaying = true;
                        mAdapter.notifyItemChanged(oldPosition);
                    }
                    mAdapter.notifyItemChanged(currentPosition);
                });
            }
        });
    }

    private void initListener() {
    }

    private void resume() {
        EventBus.getDefault().post(new PlayEvent(PlayEvent.Action.RESUME));
    }

    private void pause() {
        EventBus.getDefault().post(new PlayEvent(PlayEvent.Action.STOP));
    }

    private void play(int position) {
        PlayEvent playEvent = new PlayEvent(PlayEvent.Action.PLAY);
        playEvent.position = position;
        EventBus.getDefault().post(playEvent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void isEmptyView() {
        super.showEmptyView();
    }

    @Override
    public void isErrorView() {
        super.showErrorView();
    }

    @Override
    public void isNoNetView() {
        super.showNoNetView();
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
    public void setContent(List<Ting56Bean.Ting56DetailBean> ting56DetailBeen) {
        mTing56DetailBeen.addAll(ting56DetailBeen);
        mAdapter.notifyDataSetChanged();

        getActivity().startService(new Intent(getActivity(), PlayerService.class));
        PlayEvent<Ting56Bean.Ting56DetailBean> prepareEvent = new PlayEvent<>(PlayEvent.Action.PREPARE);
        prepareEvent.musics = mTing56DetailBeen;
        prepareEvent.position = (int) SP.Ting56.read(mUrl, 0);
        EventBus.getDefault().post(prepareEvent);
    }

    @Override
    public void setPresenter(TingPlayContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayStatusEvent event) {
        L.i(event.toString());
        isPlaying = (event.action == PlayStatusEvent.Action.PLAY);
        currentPosition = event.position;
        mAdapter.notifyDataSetChanged();
    }
}
