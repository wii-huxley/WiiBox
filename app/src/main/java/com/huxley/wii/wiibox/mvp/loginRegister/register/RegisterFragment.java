package com.huxley.wii.wiibox.mvp.loginRegister.register;

import android.os.Bundle;
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
public class RegisterFragment extends BaseFragment implements RegisterContact.View{

    private RegisterContact.Presenter mPresenter;
    private EditText et_password;
    private EditText et_username;
    private Button bt_go;
    private boolean isInputPassword;
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
        bt_go = $(R.id.bt_go);
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
