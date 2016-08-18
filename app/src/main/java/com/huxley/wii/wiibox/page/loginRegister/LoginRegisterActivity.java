package com.huxley.wii.wiibox.page.loginRegister;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.page.loginRegister.login.LoginFragment;
import com.huxley.wii.wiibox.page.loginRegister.login.LoginPresenter;
import com.huxley.wii.wiibox.page.loginRegister.model.RegisterModel;
import com.huxley.wii.wiibox.page.loginRegister.register.RegisterFragment;
import com.huxley.wii.wiibox.page.loginRegister.register.RegisterPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;
import com.huxley.wii.wiitools.common.helper.ResHelper;

/**
 *
 * Created by LeiJin01 on 2016/8/11.
 */
public class LoginRegisterActivity extends BaseActivity {

    private RegisterFragment registerFragment;
    private LoginFragment loginFragment;
    private boolean isHidd;
    private FrameLayout loginFragmentContent;
    private static final int topMargin = ResHelper.dpToPx(100);
    private boolean isOpen;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_register;
    }


    @Override
    protected void create(Bundle savedInstanceState) {
        super.create(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Fade());
        getWindow().setEnterTransition(new Fade());
        getWindow().setReturnTransition(new Fade());
        getWindow().setReenterTransition(new Fade());
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        loginFragmentContent = $(R.id.loginFragmentContent);
        loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.loginFragmentContent);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            FragmentHelper.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.loginFragmentContent);
        }
        new LoginPresenter(loginFragment);

        registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentById(R.id.loginFragmentContent);
        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();
        }
        new RegisterPresenter(registerFragment, new RegisterModel());

        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (isKeyboardShown(view) && !isHidd) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) loginFragmentContent.getLayoutParams();
                ValueAnimator animator = ValueAnimator.ofInt(topMargin, 0).setDuration(300);
                animator.start();
                animator.addUpdateListener(animation -> {
                    lp.topMargin = (int) animation.getAnimatedValue();
                    loginFragmentContent.setLayoutParams(lp);
                });
                isHidd = true;
            } else if (!isKeyboardShown(view) && isHidd) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) loginFragmentContent.getLayoutParams();
                ValueAnimator animator = ValueAnimator.ofInt(0, topMargin).setDuration(300);
                animator.start();
                animator.addUpdateListener(animation -> {
                    lp.topMargin = (int) animation.getAnimatedValue();
                    loginFragmentContent.setLayoutParams(lp);
                });
                isHidd = false;
            }
        });
    }


    private boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    public void jumpRegisterUI(View sharedElementView) {
        if (!isOpen) {
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.login_register_fab_transition);
            registerFragment.setSharedElementEnterTransition(transition);
            transition.addListener(new Transition.TransitionListener() {
                public void onTransitionStart(Transition transition) {}
                public void onTransitionCancel(Transition transition) {}
                public void onTransitionPause(Transition transition) {}
                public void onTransitionResume(Transition transition) {}
                @Override
                public void onTransitionEnd(Transition transition) {
                    registerFragment.animateRevealShow();
                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentContent, registerFragment)
                    .addSharedElement(sharedElementView, sharedElementView.getTransitionName())
                    .commit();
            isOpen = true;
        }
    }

    public void jumpLoginUI() {
        if (isOpen) {
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.login_register_fab_transition);
            loginFragment.setSharedElementEnterTransition(transition);
            transition.addListener(new Transition.TransitionListener() {
                public void onTransitionStart(Transition transition) {
                    loginFragment.cv.setVisibility(View.INVISIBLE);
                }
                public void onTransitionCancel(Transition transition) {}
                public void onTransitionPause(Transition transition) {}
                public void onTransitionResume(Transition transition) {}
                @Override
                public void onTransitionEnd(Transition transition) {
                    loginFragment.animateRevealShow();
                }
            });
            getSupportFragmentManager().beginTransaction().replace(R.id.loginFragmentContent, loginFragment)
                    .addSharedElement(registerFragment.fabLogin, registerFragment.fabLogin.getTransitionName())
                    .commit();
            isOpen = false;
        }
    }

    @Override
    protected boolean back(int keyCode, KeyEvent event) {
        if (isOpen) {
            registerFragment.animateRevealClose();
            return true;
        }
        return super.back(keyCode, event);
    }
}
