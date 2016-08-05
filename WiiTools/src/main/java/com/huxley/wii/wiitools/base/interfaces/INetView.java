package com.huxley.wii.wiitools.base.interfaces;

/**
 * Created by LeiJin01 on 2016/8/5.
 */
public interface INetView<D> {

    void showLoading();

    void dismissLoading();

    void showNotNet();

    void showError(Throwable e);

    void showContent(D data, boolean isRefresh);
}