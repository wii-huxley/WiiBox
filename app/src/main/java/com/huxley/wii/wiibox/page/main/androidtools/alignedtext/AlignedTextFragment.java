package com.huxley.wii.wiibox.page.main.androidtools.alignedtext;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.LinearLayout;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.TextHelper;
import com.huxley.wii.wiitools.common.helper.WiiUIHelper;
import com.huxley.wii.wiitools.view.WiiTextView;
import com.thefinestartist.utils.log.LogUtil;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;


/**
 *
 */
public class AlignedTextFragment extends BaseFragment implements AlignedTextContract.View {

    private AlignedTextContract.Presenter mPresenter;

    private String[] datas;

    public static AlignedTextFragment newInstance() {
        AlignedTextFragment fragment = new AlignedTextFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_aligned_text;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initData();
        initView();
    }

    private void initData() {
        datas = new String[]{
                "以为",
                "以为以",
                "以为以为",
                "以为以以为"
        };
    }

    private void initView() {
        LinearLayout container = $(R.id.container);
        WiiTextView[] textViews = new WiiTextView[datas.length];
        for (int i = 0; i < datas.length; i++) {
            LogUtil.i(datas[i]);
            container.addView(textViews[i] = WiiUIHelper.getTextView(getContext())
                    .setWiiText(datas[i]));
        }
        TextHelper.aligned(textViews);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull AlignedTextContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
