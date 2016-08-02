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
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                MusicPlayer.getPlayer(this).prepare(playEvent.musics, playEvent.position);
                break;
            case PLAY: //开始播放
                if (playEvent.position >= 0) {
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