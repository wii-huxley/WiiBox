package com.huxley.wii.wiibox.page.loginRegister.register;

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
import com.huxley.wii.wiibox.page.loginRegister.LoginRegisterActivity;
import com.huxley.wii.wiibox.page.loginRegister.model.UserInfo;
import com.huxley.wii.wiibox.page.loginRegister.model.UserInfoEvent;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.Utils.StringUtil;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;

import org.greenrobot.eventbus.EventBus;

import cn.iwgang.countdownview.CountdownView;

/**
 * LoginFragment
 * Created by LeiJin01 on 2016/8/11.
 */
public class RegisterFragment extends BaseFragment implements RegisterContact.View {

    private RegisterContact.Presenter mPresenter;
    public  FloatingActionButton      fabLogin;
    private CardView                  cvAdd;
    private EditText                  et_phone_num;
    private EditText                  et_phone_code;
    private Button                    btn_go;
    private boolean                   isInputPhoneNum;
    private boolean                   isInputPhoneCode;
    private CountdownView             timeView;
    private Button                    btnSendCode;

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
        et_phone_num = $(R.id.et_phone_num);
        et_phone_code = $(R.id.et_phone_code);
        btn_go = $(R.id.btn_go);
        fabLogin = $(R.id.fabLogin);
        cvAdd = $(R.id.cv_add);
        timeView = $(R.id.timeView);
        btnSendCode = $(R.id.btnSendCode);
    }

    private void initListener() {
        et_phone_num.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isInputPhoneNum != editable.length() > 0) {
                    isInputPhoneNum = editable.length() > 0;
                    changeBtnState();
                }
                btnSendCode.setEnabled(StringUtil.isPhone(editable.toString()));
            }
        });
        et_phone_code.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isInputPhoneCode != editable.length() > 0) {
                    isInputPhoneCode = editable.length() > 0;
                    changeBtnState();
                }
            }
        });
        fabLogin.setOnClickListener(v -> animateRevealClose());
        btn_go.setOnClickListener(v -> mPresenter.signOrLoginByMobilePhone(et_phone_num.getText().toString(), et_phone_code.getText().toString()));
        btnSendCode.setOnClickListener(v -> {
            mPresenter.requestSMSCode(et_phone_num.getText().toString());
            showTimeView();
        });
        timeView.setOnCountdownEndListener(cv -> {
            dismissTimeView();
        });
    }

    private void dismissTimeView() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(timeView, timeView.getWidth() / 2,
                timeView.getHeight() / 2, timeView.getWidth() / 2, 0);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                timeView.setVisibility(View.GONE);

                Animator mAnimator = ViewAnimationUtils.createCircularReveal(btnSendCode, btnSendCode.getWidth() / 2,
                        btnSendCode.getHeight() / 2, 0, btnSendCode.getWidth() / 2);
                mAnimator.setDuration(300);
                mAnimator.setInterpolator(new AccelerateInterpolator());
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        btnSendCode.setVisibility(View.VISIBLE);
                    }
                });
                mAnimator.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        mAnimator.start();
    }

    private void showTimeView() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(btnSendCode, btnSendCode.getWidth() / 2,
                btnSendCode.getHeight() / 2, btnSendCode.getWidth() / 2, 0);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                btnSendCode.setVisibility(View.GONE);

                Animator mAnimator = ViewAnimationUtils.createCircularReveal(timeView, timeView.getWidth() / 2,
                        timeView.getHeight() / 2, 0, timeView.getWidth() / 2);
                mAnimator.setDuration(300);
                mAnimator.setInterpolator(new AccelerateInterpolator());
                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        timeView.start(60000);
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                        timeView.setVisibility(View.VISIBLE);
                    }
                });
                mAnimator.start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        mAnimator.start();
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fabLogin.getWidth() / 2, cvAdd.getHeight());
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
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fabLogin.getWidth() / 2);
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
        btn_go.setEnabled(isInputPhoneNum && isInputPhoneCode);
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
        SnackbarHelper.showInfo(rootView, R.string.loginRegister_register_fail);
    }

    @Override
    public void showContent(UserInfo data, boolean isRefresh) {
        if (data == null) {
            data = UserInfo.getCurrentUser(UserInfo.class);
        }
        if (data == null) {
            SnackbarHelper.showInfo(rootView, R.string.str_prompt_un_know_error);
            return;
        }
        EventBus.getDefault().post(new UserInfoEvent(data));
        getActivity().finish();
    }
}
