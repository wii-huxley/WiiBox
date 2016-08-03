package com.huxley.wii.wiibox.common.musicplayer;

import java.util.List;

/**
 *|=================================================================================================
 *|PREPARE // 准备播放资源
 *|     PlayEvent playEvent = new PlayEvent(PREPARE);
 *|     1. playEvent.musics = musics; //play后，从第一首的00:00开始播放
 *|     2. playEvent.musics = musics; //play后，从第position首的seekTo开始播放
 *|        playEvent.position = position;
 *|        playEvent.seekTo = seekTo;
 *|-------------------------------------------------------------------------------------------------
 *|PLAY // 播放
 *|     PlayEvent playEvent = new PlayEvent(PLAY);
 *|     1. 构造后 position = -1 ，根据准备的播放资源直接播放
 *|     2. playEvent.position = position // 播放指定位置的歌曲
 *|-------------------------------------------------------------------------------------------------
 *|PAUSED // 暂停
 *|     new PlayEvent(PAUSED);
 *|-------------------------------------------------------------------------------------------------
 *|RESUME // 继续播放
 *|     new PlayEvent(RESUME);
 *|-------------------------------------------------------------------------------------------------
 *|STOP // 停止播放 (一般情况用不到) 停止后，seekTo应该为0
 *|     new PlayEvent(STOP);
 *|-------------------------------------------------------------------------------------------------
 *|NEXT // 下一首
 *|     new PlayEvent(NEXT);
 *|-------------------------------------------------------------------------------------------------
 *|PREVIOUS // 上一首
 *|     new PlayEvent(PREVIOUS);
 *|-------------------------------------------------------------------------------------------------
 *|SEEK // seekTo
 *|     PlayEvent playEvent = new PlayEvent(SEEK);
 *|     playEvent.seekTo = seekTo;
 *|=================================================================================================
 * Created by MASAILA on 16/5/13.
 */
public class PlayEvent<M> {

    public enum Action {
        PREPARE, PLAY, PAUSED, STOP, RESUME, NEXT, PREVIOUS, SEEK
    }

    /** 事件的状态 */
    public Action action;
    /** 歌曲列表 */
    public List<M> musics;

    public int position;

    public int seekTo;

    public String tag;

    public PlayEvent(Action action) {
        this.action = action;
        this.position = -1;
    }
}
