package com.huxley.wii.wiibox.mvp.main.translate;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.mvp.main.translate.model.BaiduTranslateInfo;
import com.huxley.wii.wiibox.mvp.main.translate.model.YouDaoTranslateInfo;
import com.huxley.wii.wiitools.common.Utils.StringUtil;
import com.huxley.wii.wiitools.base.BaseNetFragment;

import java.util.List;

/**
 *
 */
public class TranslateFragment extends BaseNetFragment implements TranslateContract.View{

    private TranslateContract.Presenter mTranslatePresenter;
    private EditText et_translate_content;
    private Button btn_baidu_translate;
    private Button btn_youdao_translate;
    private TextView tvTranslateContent;

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        addView(R.layout.fragment_translate);
        initView();
        initListener();
    }

    private void initView() {
        et_translate_content = $1(R.id.et_translate_content);
        btn_baidu_translate = $1(R.id.btn_baidu_translate);
        btn_youdao_translate = $1(R.id.btn_youdao_translate);
        tvTranslateContent = $1(R.id.tvTranslateContent);
    }

    private void initListener() {
        View.OnClickListener mClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    public void setYoudaoTranslateContent(YouDaoTranslateInfo youDaoTranslateInfo) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\t・\t%s\t:\n", youDaoTranslateInfo.query));
        List<String> translation = youDaoTranslateInfo.translation;
        if (translation != null && translation.size() > 0) {
            for (String content : translation) {
                builder.append(String.format("\t\t\t\t%s\n", content));
            }
        }
        YouDaoTranslateInfo.BasicBean basic = youDaoTranslateInfo.basic;
        if (basic != null) {
            builder.append("\t・\t基本解释\t:\n");
            if (basic.phonetic != null) {
                builder.append(String.format("\t\t\t\t-\t拼音\t:\n\t\t\t\t\t\t\t%s\n", basic.phonetic));
            }
            List<String> explains = basic.explains;
            if (explains != null && explains.size() > 0) {
                builder.append("\t\t\t\t-\t翻译\t:\n");
                for (String content : explains) {
                    builder.append(String.format("\t\t\t\t\t\t\t%s\n", content));
                }
            }
        }
        List<YouDaoTranslateInfo.WebBean> web = youDaoTranslateInfo.web;
        if (web != null && web.size() > 0) {
            builder.append("\t・\t网络解释\t:\n");
            for (YouDaoTranslateInfo.WebBean w : web) {
                if (w != null) {
                    builder.append(String.format("\t\t\t\t-\t%s\t:\n", w.key));
                    List<String> value = w.value;
                    if (value != null && value.size() > 0) {
                        for (String v : value) {
                            builder.append(String.format("\t\t\t\t\t\t\t%s\n", v));
                        }
                    }
                }
            }
        }
        tvTranslateContent.setText(builder.toString());
    }

    @Override
    public void setBaiduTranslateContent(BaiduTranslateInfo baiduTranslateInfo) {
        List<BaiduTranslateInfo.TransResultBean> trans_result = baiduTranslateInfo.trans_result;
        StringBuilder builder = new StringBuilder();
        if (trans_result != null && trans_result.size() > 0) {
            for (BaiduTranslateInfo.TransResultBean tran : trans_result) {
                builder.append(String.format("\t・\t%s\t:\t\n\t\t\t\t\t%s\n", tran.src, tran.dst));
            }
        }
        tvTranslateContent.setText(builder.toString());
    }
}
