package com.huxley.wii.wiitools.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment 基类
 * Created by huxley on 16/3/1.
 */
public class BaseFragment extends Fragment{

    protected View rootView;
    protected LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        create(savedInstanceState);
        if (getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        }
        mInflater = LayoutInflater.from(getContext());
        created(savedInstanceState);
        return rootView;
    }

    protected int getLayoutId(){
        return 0;
    }

    protected void created(Bundle savedInstanceState) {

    }

    protected void create(Bundle savedInstanceState) {

    }

    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater result = getActivity().getLayoutInflater();
        return result;
    }

    public <V extends View> V $(int id) {
        return (V)rootView.findViewById(id);
    }
}
