package com.huxley.wii.wiitools.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huxley.wii.wiitools.R;
import com.huxley.wii.wiitools.common.Utils.StringUtil;
import com.huxley.wii.wiitools.base.BaseFragment;
import com.huxley.wii.wiitools.common.helper.ResHelper;
import com.huxley.wii.wiitools.common.helper.SoftUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 *
 * Created by huxley on 16/7/16.
 */
public class TitleFragment extends BaseFragment {

    public static final int TYPE_SEARCH = 1;
    public static final int TYPE_RIGHT_TITLE = 2;
    public static final int TYPE_BACK = 3;

    private int currentType;

    private Toolbar toolbar;
    private TextView title_text_left;
    private TextView title_text_center;
    private RelativeLayout title_search;
    private TextView title_text_right;
    private EditText et_search;
    private ViewGroup.LayoutParams lp_search;
    private ViewGroup.MarginLayoutParams lp_iv;
    private ImageView iv_search;
    private OnSearchListener mListener;

    private boolean openSearch;
    private boolean isSearchRun;
    private float searchChangeWidth;
    private int searchCloseWidth;

    @Override
    protected int getLayoutId() {
        return R.layout.wii_toolbar;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
    }

    private void initView() {
        toolbar = $(R.id.toolbar);
        title_text_left = $(R.id.title_text_left);
        title_text_center = $(R.id.title_text_center);
        title_search = $(R.id.title_search);
        title_text_right = $(R.id.title_text_right);
        openSearch = false;
        toolbar.setTitle("");
        toolbar.setPadding(0,0,0,0);
    }

    public void setType(int type) {
        if (currentType == TYPE_SEARCH && openSearch) {
            hideSearch("");
        }

        switch (type) {
            case TYPE_SEARCH:
                searchType();
                break;
            case TYPE_RIGHT_TITLE:
                rightTitleType();
                break;
            case TYPE_BACK:
                backType();
                break;
        }
        this.currentType = type;
    }

    private void backType() {
        title_text_right.setVisibility(View.GONE);
        title_search.setVisibility(View.GONE);
        title_text_left.setVisibility(View.GONE);
        title_text_center.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.ic_back);
    }

    public void addBackListener(final Activity activity) {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "id = " + v.getId());
                activity.finish();
            }
        });
    }

    private void rightTitleType() {
        title_text_center.setVisibility(View.GONE);
        title_text_right.setVisibility(View.GONE);
        title_search.setVisibility(View.GONE);
        title_text_left.setVisibility(View.VISIBLE);
    }

    private void searchType() {
        title_text_center.setVisibility(View.GONE);
        title_text_right.setVisibility(View.GONE);
        title_search.setVisibility(View.VISIBLE);
        title_text_left.setVisibility(View.VISIBLE);

        if (et_search == null) {
            et_search = $(R.id.et_search);
            iv_search = $(R.id.iv_search);

            searchCloseWidth = ResHelper.dpToPx(66);
            searchChangeWidth = (ResHelper.getScreenWidth() - searchCloseWidth) / 100.0f;

            lp_search = title_search.getLayoutParams();//获取item的布局参数
            lp_iv = (ViewGroup.MarginLayoutParams)iv_search.getLayoutParams();

            iv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSearchRun) {
                        if (openSearch) {
                            hideSearch(et_search.getText().toString());
                        } else {
                            showSearch();
                        }
                    }
                }
            });
            et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    switch (actionId) {
                        case EditorInfo.IME_ACTION_SEARCH:
                            String content = et_search.getText().toString();
                            if (!isSearchRun) {
                                hideSearch(content);
                            }
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private void showSearch() {
        isSearchRun = true;
        ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(300);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                lp_search.width = (int) (animatedValue * searchChangeWidth) + searchCloseWidth;
                title_search.setLayoutParams(lp_search);
                ViewHelper.setAlpha(title_text_left, 1 - animatedValue / 100.0f);
                lp_iv.rightMargin = animatedValue / 10;
                iv_search.setLayoutParams(lp_iv);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                openSearch = true;
                isSearchRun = false;
                et_search.setHint(R.string.hint_search_ting);
                et_search.setText("");
            }
        });
        SoftUtils.showSoftInput(getActivity());
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
    }

    private void hideSearch(String content) {
        isSearchRun = true;
        et_search.setHint("");
        et_search.setText("");
        ValueAnimator animator = ValueAnimator.ofInt(100, 0).setDuration(300);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                lp_search.width = (int) (animatedValue * searchChangeWidth) + searchCloseWidth;
                title_search.setLayoutParams(lp_search);
                ViewHelper.setAlpha(title_text_left, 1 - animatedValue / 100.0f);
                lp_iv.rightMargin = animatedValue / 10;
                iv_search.setLayoutParams(lp_iv);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                openSearch = false;
                isSearchRun = false;
            }
        });
        if (mListener != null && ! StringUtil.isEmpty(content)) {
            mListener.onSearch(content);
        }
        SoftUtils.hideSoftInput(getActivity());
    }

    public void setTitle(String title) {
        switch (currentType) {
            case TYPE_SEARCH:
            case TYPE_RIGHT_TITLE:
                title_text_left.setText(title);
                break;
            case TYPE_BACK:
                title_text_center.setText(title);
                break;
        }
    }

    public void setSupportActionBar(AppCompatActivity compatActivity) {
        compatActivity.setSupportActionBar(toolbar);
    }

    public void setOnSearchListener(OnSearchListener mListener) {
        this.mListener = mListener;
    }

    public interface OnSearchListener{
        void onSearch(String content);
    }
}
