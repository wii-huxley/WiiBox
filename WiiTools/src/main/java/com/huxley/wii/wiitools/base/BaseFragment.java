package com.huxley.wii.wiitools.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * fragment 基类
 * Created by huxley on 16/3/1.
 */
public class BaseFragment extends Fragment {

    protected View           rootView;
    protected LayoutInflater mInflater;
    private   KProgressHUD   hud;

    protected int getLayoutId() {
        return 0;
    }

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

    protected void created(Bundle savedInstanceState) {

    }

    protected void create(Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public <V extends View> V $(int id) {
        return (V) rootView.findViewById(id);
    }

    protected void isLoading(boolean loading) {
        if (loading) {
            if (hud == null) {
                hud = KProgressHUD.create(getContext()).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            }
            hud.show();
        } else {
            if (hud == null) {
                return;
            }
            hud.dismiss();
        }
    }
}
