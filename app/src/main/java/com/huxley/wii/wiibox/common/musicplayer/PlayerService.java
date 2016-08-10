package com.huxley.wii.wiibox.common.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PlayerService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        MusicPlayer.getPlayer(this).release();
    }

    //接收EventBus post过来的PlayEvent
    @Subscribe
    public void onEvent(PlayEvent playEvent) {
        if (playEvent == null) {
            return;
        }
        switch (playEvent.action) {
            case PREPARE: // 设置资源
                MusicPlayer.getPlayer(this).prepare(playEvent.musics, playEvent.position, playEvent.tag);
                break;
            case PLAY: //开始播放
                if (playEvent.position >= 0) {
                    MusicPlayer.getPlayer(this).setMusics(playEvent.tag, playEvent.musics);
                    MusicPlayer.getPlayer(this).play(playEvent.position);
                } else {
                    MusicPlayer.getPlayer(this).play();
                }
                break;
            case PAUSED: //暂停
                MusicPlayer.getPlayer(this).pause();
                break;
            case RESUME: //继续播放
                MusicPlayer.getPlayer(this).resume();
                break;
            case STOP: //停止
                MusicPlayer.getPlayer(this).stop();
                break;
            case NEXT: //下一曲
                MusicPlayer.getPlayer(this).next();
                break;
            case PREVIOUS: //上一曲
                MusicPlayer.getPlayer(this).previous();
                break;
            case SEEK:
                MusicPlayer.getPlayer(this).seekTo(playEvent.seekTo);
                break;
        }
    }
}