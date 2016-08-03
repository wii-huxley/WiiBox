package com.huxley.wii.wiibox.common.musicplayer;

import com.huxley.wii.wiitools.common.Utils.GsonUtils;

/**
 *
 * Created by LeiJin01 on 2016/7/25.
 */
public class PlayStatusEvent {

    public PlayStatusEvent(Action action) {
        this(action, 0);
    }

    public PlayStatusEvent(Action action, int position) {
        this.action = action;
        this.position = position;
    }

    public enum Action {
        NONE, PREPARE, PLAY, PAUSED
    }

    public Action action;

    public int position;

    public String tag;

    @Override
    public String toString() {
        return GsonUtils.get().toJson(this);
    }
}
