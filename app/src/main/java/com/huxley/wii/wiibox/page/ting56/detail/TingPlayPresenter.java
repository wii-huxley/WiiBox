package com.huxley.wii.wiibox.page.ting56.detail;

import com.huxley.wii.wiibox.common.musicplayer.PlayEvent;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.page.ting56.model.Ting56Bean;
import com.huxley.wii.wiibox.page.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.common.helper.ExceptionHelper;
import com.huxley.wii.wiitools.common.helper.NetWorkHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

/**
 * TingPlayPresenter
 * Created by huxley on 16/7/13.
 */
public class TingPlayPresenter implements TingPlayContract.Presenter{

    private final TingPlayContract.View tingPlayView;
    private String mUrl;

    public TingPlayPresenter(TingPlayContract.View tingPlayView, String url) {
        this.tingPlayView = checkNotNull(tingPlayView);
        this.mUrl = url;
        this.tingPlayView.setPresenter(this);
    }

    @Override
    public void start() {
        Ting56Model.getInstance().getChapterInfos(mUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Ting56Bean.Ting56DetailBean>>() {
                    @Override
                    public void onStart() {
                        tingPlayView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        tingPlayView.dismissLoading();
                    }
                    @Override
                    public void onError(Throwable e) {
                        tingPlayView.dismissLoading();
                        if (ExceptionHelper.isNetException(e) && !NetWorkHelper.isConnected()) {
                            tingPlayView.showNotNet();
                        } else {
                            tingPlayView.showError(e);
                        }
                    }
                    @Override
                    public void onNext(List<Ting56Bean.Ting56DetailBean> ting56DetailBeen) {
                        tingPlayView.showContent(ting56DetailBeen, true);
                        PlayEvent<Ting56Bean.Ting56DetailBean> prepareEvent = new PlayEvent<>(PlayEvent.Action.PREPARE);
                        prepareEvent.musics = ting56DetailBeen;
                        prepareEvent.tag = mUrl;
                        prepareEvent.position = (int) SP.Ting56.read(mUrl, 0);
                        EventBus.getDefault().post(prepareEvent);
                    }
                });
    }

    @Override
    public void resume() {
        EventBus.getDefault().post(new PlayEvent(PlayEvent.Action.RESUME));
    }

    @Override
    public void pause() {
        EventBus.getDefault().post(new PlayEvent(PlayEvent.Action.PAUSED));
    }

    @Override
    public String getTag() {
        return mUrl;
    }

    @Override
    public void play(List<Ting56Bean.Ting56DetailBean> data, int position) {
        PlayEvent playEvent = new PlayEvent(PlayEvent.Action.PLAY);
        playEvent.musics = data;
        playEvent.position = position;
        playEvent.tag = mUrl;
        EventBus.getDefault().post(playEvent);
    }

    @Override
    public void reTry() {
        start();
    }
}
