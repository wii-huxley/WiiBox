package com.huxley.wii.wiitools.common.helper;

import com.huxley.wii.wiitools.exception.EmptyException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by huxley on 16/7/7.
 */
public class ExceptionHelper {

    public static boolean isNoNetException(Throwable e) {
        return e instanceof SocketTimeoutException || e instanceof UnknownHostException || e instanceof ConnectException;
    }

    public static boolean isEmptyException(Throwable e) {
        return e instanceof EmptyException;
    }
}
