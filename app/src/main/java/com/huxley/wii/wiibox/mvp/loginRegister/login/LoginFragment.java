package com.huxley.wii.wiibox.mvp.loginRegister.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.huxley.wii.wiibox.R;
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
        fabRegister.setOnClickListener(view -> ((LoginActivity)getActivity()).jumpRegisterUI(fabRegister));
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
