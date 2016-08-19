package com.huxley.wii.wiibox.page.main.androidtools.bezier;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.CheckBox;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.page.main.androidtools.bezier.view.BezierView;
import com.huxley.wii.wiitools.base.BaseFragment;

import static com.huxley.wii.wiitools.common.Utils.NonNull.checkNotNull;

public class BezierFragment extends BaseFragment implements BezierContract.View {

    private BezierContract.Presenter mBezierPresenter;
    private BezierView bezierView;
    private CheckBox cbOrder;

    public static BezierFragment newInstance() {
        return new BezierFragment();
    }

    @Override
    public void setPresenter(@NonNull BezierContract.Presenter presenter) {
        mBezierPresenter = checkNotNull(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bezier;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initListener();
    }

    private void initListener() {
        cbOrder.setOnCheckedChangeListener((compoundButton, b) -> {
            cbOrder.setText(b ? R.string.bezier_second_order : R.string.bezier_third_order);
            bezierView.setOrder(b);
        });
    }

    private void initView() {
        bezierView = $(R.id.bezierView);
        cbOrder = $(R.id.cbOrder);
    }
}
