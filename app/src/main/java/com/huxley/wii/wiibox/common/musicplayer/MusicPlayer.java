package com.huxley.wii.wiibox.common.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

import com.cleveroad.audiowidget.AudioWidget;
import com.huxley.wii.wiibox.common.utils.SP;
import com.huxley.wii.wiibox.mvp.ting56.model.Ting56Model;
import com.huxley.wii.wiitools.common.Utils.L;
import com.huxley.wii.wiitools.common.Utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.huxley.wii.wiibox.common.Constant.Key.POSITION_X;
import static com.huxley.wii.wiibox.common.Constant.Key.POSITION_Y;

/**
 *
 * Created by MASAILA on 16/5/13.
 */
public class MusicPlayer<M> implements MediaPlayer.OnCompletionListener {

    private AudioWidget audioWidget;
    private static MusicPlayer player;

    private MediaPlayer mMediaPlayer;
    private PlayMode mPlayMode;
    private List<String> mMusics;
    private Timer timer;
    private PlayStatusEvent playStatusEvent;

    private enum PlayMode {
        LOOP, RANDOM, REPEAT
    }

    private MusicPlayer(Context context) {
        audioWidget = new AudioWidget.Builder(context).build();
        audioWidget.controller().onControlsClickListener(new AudioWidget.OnControlsClickListener() {
            @Override
            public boolean onPlaylistClicked() {
                L.i("onPlaylistClicked");
                return false;
            }
            @Override
            public void onPlaylistLongClicked() {
                L.i("onPlaylistLongClicked");
            }
            @Override
            public boolean onPlayPauseClicked() {
                switch (playStatusEvent.action) {
                    case NONE: play(); break;
                    case PREPARE: break;
                    case PLAY: pause(); break;
                    case PAUSED: resume(); break;
                }
                return false;
            }
            @Override
            public void onPlayPauseLongClicked() {}
            @Override
            public void onNextClicked() {
                next();
            }
            @Override
            public void onNextLongClicked() {}
            @Override
            public void onPreviousClicked() {
                previous();
            }
            @Override
            public void onPreviousLongClicked() {}
            @Override
            public void onAlbumClicked() {}
            @Override
            public void onAlbumLongClicked() {}
        });
        audioWidget.controller().onWidgetStateChangedListener(new AudioWidget.OnWidgetStateChangedListener() {
            @Override
            public void onWidgetStateChanged(@NonNull AudioWidget.State state) {}
            @Override
            public void onWidgetPositionChanged(int cx, int cy) {
                SP.Config.save(POSITION_X, cx);
                SP.Config.save(POSITION_Y, cy);
            }
        });

        playStatusEvent = new PlayStatusEvent(PlayStatusEvent.Action.NONE);
        mMediaPlayer = new ManagedMediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMusics = new ArrayList<>();
        mPlayMode = PlayMode.LOOP;
    }

    public static MusicPlayer getPlayer(Context context) {
        if (player == null) {
            synchronized (MusicPlayer.class) {
                if (player == null) {
                    player = new MusicPlayer(context);
                }
            }
        }
        return player;
    }

    public void prepare(List<M> musics, int position){
        L.json(musics.toString());
        try {
            for (M music : musics) {
                String path = null;
                Class<?> clazz = music.getClass();
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.getAnnotation(MusicPath.class) != null) {
                        field.setAccessible(true);
                        path = (String) field.get(music);
                    }
                    if (path != null) {
                        break;
                    }
                }
                this.mMusics.add(path);
            }
            playStatusEvent.position = position;
            if (!audioWidget.isShown()) {
                audioWidget.show((int)SP.Config.read(POSITION_X, 100), (int)SP.Config.read(POSITION_Y, 100));
            }
        } catch (Exception e) {
            //出错
        }
    }

    public void play(String music) {
        playStatusEvent.action = PlayStatusEvent.Action.PREPARE;
        String read = (String) SP.Ting56.read(music, "");
        if (!StringUtil.isEmpty(read)) {
            playOfUrl(read);
        } else {
            Ting56Model.getInstance().getVoiceUrls(music)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {}
                        @Override
                        public void onError(Throwable e) {}
                        @Override
                        public void onNext(String s) {
                            SP.Ting56.save(music, s);
                            playOfUrl(s);
                        }
                    });
        }
    }

    private void playOfUrl(String musicUrl) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(musicUrl);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(mp -> {
                resume();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(int position){
        play(mMusics.get(playStatusEvent.position = position));
    }

    public void play(){
        play(getNowPlaying());
    }

    public void pause() {
        playStatusEvent.action = PlayStatusEvent.Action.PAUSED;
        mMediaPlayer.pause();
        audioWidget.controller().start();
        stopTrackingPosition();
        EventBus.getDefault().post(playStatusEvent);
    }

    public void stop() {
        playStatusEvent.action = PlayStatusEvent.Action.PAUSED;
        mMediaPlayer.stop();
        audioWidget.controller().start();
        stopTrackingPosition();
    }

    public void resume() {
        playStatusEvent.action = PlayStatusEvent.Action.PLAY;
        mMediaPlayer.start();
        audioWidget.controller().pause();
        startTrackingPosition();
        EventBus.getDefault().post(playStatusEvent);
    }

    public void seekTo(int seekTo){
        mMediaPlayer.seekTo(seekTo);
    }

    public void next() {
        play(getNextSong());
    }

    public void previous() {
        play(getPreviousSong());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        next();
    }

    private String getNowPlaying() {
        if (mMusics == null || mMusics.isEmpty()) {
            return null;
        }
        return mMusics.get(playStatusEvent.position);
    }

    private String getNextSong() {
        if (mMusics == null || mMusics.isEmpty()) {
            return null;
        }
        switch (mPlayMode) {
            case LOOP:
                return mMusics.get(getNextIndex());
            case RANDOM:
                return mMusics.get(getRandomIndex());
            case REPEAT:
                return mMusics.get(playStatusEvent.position);
        }
        return null;
    }

    private String getPreviousSong() {
        if (mMusics == null || mMusics.isEmpty()) {
            return null;
        }
        switch (mPlayMode) {
            case LOOP:
                return mMusics.get(getPreviousIndex());
            case RANDOM:
                return mMusics.get(getRandomIndex());
            case REPEAT:
                return mMusics.get(playStatusEvent.position);
        }
        return null;
    }


    public int getCurrentPosition() {
        if (getNowPlaying() != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public int getDuration() {
        if (getNowPlaying() != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    public void setPlayMode(PlayMode playMode) {
        mPlayMode = playMode;
    }

    private int getNextIndex() {
        playStatusEvent.position = (playStatusEvent.position + 1) % mMusics.size();
        return playStatusEvent.position;
    }

    private int getPreviousIndex() {
        playStatusEvent.position = (playStatusEvent.position - 1) % mMusics.size();
        return playStatusEvent.position;
    }

    private int getRandomIndex() {
        playStatusEvent.position = new Random().nextInt(mMusics.size()) % mMusics.size();
        return playStatusEvent.position;
    }

    public void release() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        audioWidget.controller().onControlsClickListener(null);
        audioWidget.controller().onWidgetStateChangedListener(null);
        audioWidget.hide();
        audioWidget = null;
    }

    private void startTrackingPosition() {
        timer = new Timer("MusicService Timer");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (audioWidget != null && mMediaPlayer.isPlaying()) {
                    audioWidget.controller().duration(mMediaPlayer.getDuration());
                    audioWidget.controller().position(mMediaPlayer.getCurrentPosition());
                }
            }
        }, 1000, 1000);
    }

    private void stopTrackingPosition() {
        if (timer == null)
            return;
        timer.cancel();
        timer.purge();
        timer = null;
    }
}