package com.huxley.wii.wiibox.mvp.loginRegister.register;

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
import com.huxley.wii.wiibox.mvp.loginRegister.model.UserInfo;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;

/**
 * LoginFragment
 * Created by LeiJin01 on 2016/8/11.
 */
public class RegisterFragment extends BaseFragment implements RegisterContact.View{

    private RegisterContact.Presenter mPresenter;
    public FloatingActionButton fabLogin;
    private CardView cvAdd;
    private EditText et_password;
    private EditText et_username;
    private EditText et_repeatpassword;
    private Button btn_go;
    private boolean isInputPassword;
    private boolean isInputRepeatPassword;
    private boolean isInputUsername;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void setPresenter(RegisterContact.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
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
        et_repeatpassword = $(R.id.et_repeatpassword);
        btn_go = $(R.id.btn_go);
        fabLogin = $(R.id.fabLogin);
        cvAdd = $(R.id.cv_add);
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
        et_repeatpassword.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isInputRepeatPassword != editable.length() > 0) {
                    isInputRepeatPassword = editable.length() > 0;
                    changeBtnState();
                }
            }
        });
        fabLogin.setOnClickListener(v -> animateRevealClose());
        btn_go.setOnClickListener(v -> mPresenter.register());
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fabLogin.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fabLogin.getWidth() / 2);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                ((LoginRegisterActivity) getActivity()).jumpLoginUI();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        mAnimator.start();
    }

    private void changeBtnState() {
        btn_go.setEnabled(isInputPassword && isInputUsername && isInputRepeatPassword);
    }

    @Override
    public void showLoading() {
        isLoading(true);
    }

    @Override
    public void dismissLoading() {
        isLoading(false);
    }

    @Override
    public void showNotNet() {
        SnackbarHelper.showNoNetInfo(rootView);
    }

    @Override
    public void showError(Throwable e) {
        SnackbarHelper.showInfo(rootView, R.string.str_prompt_loading_fail);
    }

    @Override
    public void showContent(UserInfo data, boolean isRefresh) {

    }
}
