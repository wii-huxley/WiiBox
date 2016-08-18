package com.huxley.wii.wiibox.common;

/**
 * 常量类
 * <p>
 * Created by huxley on 16/2/29.
 */
public interface Constant {

    String LOG_NAME     = "wii";
    double GOLDEN_RATIO = (Math.sqrt(5) - 1) / 2;

    interface Key {
        String HISTORY_LIST   = "historyList";
        String strKey         = "key.str";
        String IMAGE_VIEW     = "imageView";
        String POSITION_X     = "positionX";
        String POSITION_Y     = "positionY";
        String POSITION_MUSIC = "positionMusic";
        String DATA           = "data";
        String POSITION       = "position";
        String URL            = "url";
        String NAME           = "name";
        String DATE           = "date";
        String ID             = "id";
    }

    interface RequestCode {

        int GANK_DETAIL_DATA = 1001;
    }

    enum Result {
        SUCCESS, FAIL, NORMAL
    }

    interface Data {
    }

    interface Debug {

        boolean TEST_LOG = true;
        String  TAG_LOG  = "huxley_log";
    }
}
