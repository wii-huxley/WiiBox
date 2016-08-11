package com.huxley.wii.wiibox.mvp.loginRegister.login;

import android.os.Bundle;
import android.view.View;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.mvp.loginRegister.register.RegisterFragment;
import com.huxley.wii.wiibox.mvp.loginRegister.register.RegisterPresenter;
import com.huxley.wii.wiitools.base.BaseActivity;
import com.huxley.wii.wiitools.common.helper.FragmentHelper;

/**
 *
 * Created by LeiJin01 on 2016/8/11.
 */
public class LoginActivity extends BaseActivity {

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);


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
        new RegisterPresenter(registerFragment);
    }

    public void jumpRegisterUI(View sharedElementView) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loginFragmentContent, registerFragment)
                .addSharedElement(sharedElementView, sharedElementView.getTransitionName())
                .commit();
    }
}
