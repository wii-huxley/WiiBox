package com.huxley.wii.wiibox.mvp.loginRegister.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.mvp.loginRegister.LoginRegisterActivity;
import com.huxley.wii.wiitools.base.BaseFragment;


/**
 * LoginFragment
 * Created by LeiJin01 on 2016/8/11.
 */
public class LoginFragment extends BaseFragment implements LoginContact.View{

    private LoginContact.Presenter mPresenter;
    private EditText et_password;
    private EditText et_username;
    private Button bt_go;
    private FloatingActionButton fabRegister;
    private boolean isInputPassword;
    private boolean isInputUsername;
    public CardView cv;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initListener();
    }

    private void initView() {
        et_password = $(R.id.et_password);
        et_username = $(R.id.et_username);
        bt_go = $(R.id.bt_go);
        fabRegister = $(R.id.fabRegister);
        cv = $(R.id.cv);
    }

    private void initListener() {
        et_password.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (isInputPassword != editable.length() > 0) {
                    isInputPassword = editable.length() > 0;
                    changeBtnState();
                }
            }
        });
        et_username.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (isInputUsername != editable.length() > 0) {
                    isInputUsername = editable.length() > 0;
                    changeBtnState();
                }
            }
        });
        fabRegister.setOnClickListener(view -> animateRevealClose());
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cv, cv.getWidth(), fabRegister.getTop() + fabRegister.getHeight() / 2, fabRegister.getWidth() / 2, cv.getWidth());
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cv.setVisibility(View.VISIBLE);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cv, cv.getWidth(), fabRegister.getTop() + fabRegister.getHeight() / 2, cv.getWidth(), fabRegister.getWidth() / 2)
                .setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cv.setVisibility(View.INVISIBLE);
                ((LoginRegisterActivity)getActivity()).jumpRegisterUI(fabRegister);
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        mAnimator.start();
    }

    private void changeBtnState() {
        bt_go.setEnabled(isInputPassword && isInputUsername);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNotNet() {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showContent(Object data, boolean isRefresh) {

    }
}
