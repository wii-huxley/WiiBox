package com.huxley.wii.wiibox.mvp.main.translate;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.Utils.StringUtil;
import com.huxley.wii.wiitools.common.helper.SnackbarHelper;

/**
 *
 */
public class TranslateFragment extends BaseFragment implements TranslateContract.View{

    private TranslateContract.Presenter mTranslatePresenter;
    private EditText et_translate_content;
    private Button btn_baidu_translate;
    private Button btn_youdao_translate;
    private TextView tvTranslateContent;

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_translate;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initListener();
    }

    private void initView() {
        Toolbar toolbar = UIHelper.createToolbar((AppCompatActivity) getActivity(), rootView);
        toolbar.setTitle(R.string.str_translate);

        et_translate_content = $(R.id.et_translate_content);
        btn_baidu_translate = $(R.id.btn_baidu_translate);
        btn_youdao_translate = $(R.id.btn_youdao_translate);
        tvTranslateContent = $(R.id.tvTranslateContent);
    }

    private void initListener() {
        View.OnClickListener mClickListener = v -> {
            String content = et_translate_content.getText().toString();
            if (StringUtil.isEmpty(content)) {
                ToastHelper.showInfo(R.string.str_translate_content_is_empty);
                return;
            }
            switch (v.getId()) {
                case R.id.btn_baidu_translate: // 百度翻译
                    mTranslatePresenter.baiduTranslate(content);
                    break;
                case R.id.btn_youdao_translate: // 有道翻译
                    mTranslatePresenter.youdaoTranslate(content);
                    break;
            }
        };
        btn_baidu_translate.setOnClickListener(mClickListener);
        btn_youdao_translate.setOnClickListener(mClickListener);
    }


    @Override
    public void setPresenter(TranslateContract.Presenter presenter) {
        mTranslatePresenter = presenter;
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
        SnackbarHelper.showInfo(rootView, R.string.str_error);
    }

    @Override
    public void showContent(String content, boolean isRefresh) {
        tvTranslateContent.setText(content);
    }
}
