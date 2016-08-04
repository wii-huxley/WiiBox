package com.huxley.wii.wiibox.mvp.main.androidtools;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.UIHelper;
import com.huxley.wii.wiibox.common.widget.flowlayout.FlowLayout;
import com.huxley.wii.wiibox.common.widget.flowlayout.TagAdapter;
import com.huxley.wii.wiibox.common.widget.flowlayout.TagFlowLayout;
import com.huxley.wii.wiitools.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class AndroidToolsFragment extends BaseFragment implements TagFlowLayout.OnTagClickListener {

    private String[] mVals = {"相册", "五子棋", "扫雷", "ExpandingPager", "TagFlowLayout", "treeview",
            "索引", "NavigationTabBar", "stepView", "alignedText", "BlurView", "html-image-switch"};
    private TagFlowLayout mFlowLayout;

    public AndroidToolsFragment() {
        // Required empty public constructor
    }

    public static AndroidToolsFragment newInstance() {
        return new AndroidToolsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_android_tools;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        mFlowLayout.setOnTagClickListener(this);
    }

    private void initData() {
    }

    private void initView() {
        Toolbar toolbar = $(R.id.toolbar);
        toolbar.setTitle("AndroidTools");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        mFlowLayout = $(R.id.flowLayout);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.child_flow_textview, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        switch (position) {
            case 0://相册
                UIHelper.startPhotoAlbumActivity(getActivity());
                break;
            case 1://五子棋
                UIHelper.startGobangActivity(getActivity());
                break;
            case 2://扫雷

                break;
            case 3://树形布局
                UIHelper.setTreeViewActivity(getActivity());
                break;
            case 4://ExpandingPager
                UIHelper.startExpandingPagerActivity(getActivity());
                break;
            case 5://TagFlowLayou
                UIHelper.startCategoryActivity(getActivity());
                break;
            case 6:
//                UIHelper.startIndexableListViewActivity(getActivity());
                break;
            case 7:
                UIHelper.setNavigationTabBarActivity(getActivity());
                break;
            case 8:
                UIHelper.setStepViewActivity(getActivity());
                break;
            case 9://alignedText
                UIHelper.setAlignedTextActivity(getActivity());
                break;
            case 10: //BlurView
                UIHelper.startBlurViewActivity(getActivity());
                break;
            case 11:
                UIHelper.openWebView("file:///android_asset/image-switch/index.html", "html-image-switch", getContext());
                break;
        }
        return false;
    }
}
