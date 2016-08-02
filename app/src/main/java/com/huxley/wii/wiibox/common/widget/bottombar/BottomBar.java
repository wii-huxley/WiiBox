package com.huxley.wii.wiibox.common.widget.bottombar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ResHelper;
import com.huxley.wii.wiibox.common.utils.Tools;

import java.util.HashMap;


/**
 * 底部导航栏
 * Created by huxley on 16/5/21.
 */
public class BottomBar extends FrameLayout implements View.OnClickListener {

    private static final long ANIMATION_DURATION = 150;
    private static final String TAG_BOTTOM_BAR_VIEW_INACTIVE = "BOTTOM_BAR_VIEW_INACTIVE";
    private static final String TAG_BOTTOM_BAR_VIEW_ACTIVE = "BOTTOM_BAR_VIEW_ACTIVE";

    private Context mContext;
    private BottomBarTab[] mItems;
    private OnBottomBarClickListener mListener;
    private ViewGroup mItemContainer;
    private View mBackgroundView;
    private View mBackgroundOverlay;
    private int mTenDp;
    private int mMaxFixedItemWidth;
    private int mInActiveShiftingItemWidth;
    private int mActiveShiftingItemWidth;
    private HashMap<Integer, Object> mBadgeMap;
    private HashMap<Integer, Integer> mColorMap;

    private int mCurrentTabPosition;
    private int mCurrentBackgroundColor;
    private int mDefaultBackgroundColor;

    public BottomBar(Context context) {
        this(context, null);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        mMaxFixedItemWidth = ResHelper.dpToPx(168);
        mTenDp = ResHelper.dpToPx(10);
    }

    public void setItemsFromMenu(@MenuRes int menuRes, OnBottomBarClickListener listener) {
        clearItems();
        mItems = ResHelper.inflateMenuFromResource((Activity) getContext(), menuRes);
        mListener = listener;
        updateItems(mItems);

        listener.onBottomBarSelected((mItems[mCurrentTabPosition]).getId());
    }

    private void updateItems(BottomBarTab[] items) {
        if (mItemContainer == null) {
            initializeViews();
        }
        int index = 0;
        View[] viewsToAdd = new View[items.length];
        for (BottomBarTab item : items) {
            View bottomBarTab = View.inflate(mContext, R.layout.bb_bottom_bar_item_shifting, null);
            ImageView icon = (ImageView) bottomBarTab.findViewById(R.id.bb_bottom_bar_icon);
            icon.setImageDrawable(ResHelper.tintDrawable(item.getIcon(), ColorStateList.valueOf(Color.WHITE)));
            bottomBarTab.setId(item.getId());


            TextView title = (TextView) bottomBarTab.findViewById(R.id.bb_bottom_bar_title);
            title.setText(item.getName());

            if (index == mCurrentTabPosition) {
                selectTab(bottomBarTab, false);
            } else {
                unSelectTab(bottomBarTab, false);
            }

            viewsToAdd[index] = bottomBarTab;

            bottomBarTab.setOnClickListener(this);
            index++;
        }

        int proposedItemWidth = Math.min(ResHelper.getScreenWidth() / mItems.length, mMaxFixedItemWidth);

        mInActiveShiftingItemWidth = (int) (proposedItemWidth * 0.9);
        mActiveShiftingItemWidth = (int) (proposedItemWidth + (proposedItemWidth * (mItems.length * 0.1)));

        for (View bottomBarView : viewsToAdd) {
            LinearLayout.LayoutParams params;
            if (TAG_BOTTOM_BAR_VIEW_ACTIVE.equals(bottomBarView.getTag())) {
                params = new LinearLayout.LayoutParams(mActiveShiftingItemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            } else {
                params = new LinearLayout.LayoutParams(mInActiveShiftingItemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            bottomBarView.setLayoutParams(params);
            mItemContainer.addView(bottomBarView);
        }
    }

    private void initializeViews() {
        View rootView = View.inflate(mContext, R.layout.bb_bottom_bar_item_container, null);
        mItemContainer = (ViewGroup) rootView.findViewById(R.id.bb_bottom_bar_item_container);
        mBackgroundView = rootView.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_background_view);
        mBackgroundOverlay = rootView.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_background_overlay);
        addView(rootView);
    }

    private void clearItems() {
        if (mItemContainer != null) {
            int childCount = mItemContainer.getChildCount();

            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    mItemContainer.removeView(mItemContainer.getChildAt(i));
                }
            }
        }
        if (mItems != null) {
            mItems = null;
        }
    }

    private void selectTab(View tab, boolean animate) {
        tab.setTag(TAG_BOTTOM_BAR_VIEW_ACTIVE);
        ImageView icon = (ImageView) tab.findViewById(R.id.bb_bottom_bar_icon);
        TextView title = (TextView) tab.findViewById(R.id.bb_bottom_bar_title);

        if (title == null) {
            return;
        }

        if (animate) {
            ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(title)
                    .setDuration(ANIMATION_DURATION)
                    .scaleX(1)
                    .scaleY(1);
            titleAnimator.alpha(1.0f);
            titleAnimator.start();

            ViewCompat.animate(tab)
                    .setDuration(ANIMATION_DURATION)
                    .translationY(-mTenDp)
                    .start();


            ViewCompat.animate(icon)
                    .setDuration(ANIMATION_DURATION)
                    .alpha(1.0f)
                    .start();

            //TODO 有待研究
//            handleBackgroundColorChange(tabPosition, tab);
        } else {
            ViewCompat.setScaleX(title, 1);
            ViewCompat.setScaleY(title, 1);
            ViewCompat.setTranslationY(tab, -mTenDp);


            ViewCompat.setAlpha(icon, 1.0f);
            ViewCompat.setAlpha(title, 1.0f);
        }
    }

    private void unSelectTab(View tab, boolean animate) {
        tab.setTag(TAG_BOTTOM_BAR_VIEW_INACTIVE);
        ImageView icon = (ImageView) tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_icon);
        TextView title = (TextView) tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_title);
        if (title == null) {
            return;
        }
        float scale = 0f;
        if (animate) {
            ViewPropertyAnimatorCompat titleAnimator = ViewCompat.animate(title)
                    .setDuration(ANIMATION_DURATION)
                    .scaleX(scale)
                    .scaleY(scale);

            titleAnimator.alpha(0);
            titleAnimator.start();
            ViewCompat.animate(tab)
                    .setDuration(ANIMATION_DURATION)
                    .translationY(0)
                    .start();

            ViewCompat.animate(icon)
                    .setDuration(ANIMATION_DURATION)
                    .alpha(0.6f)
                    .start();
        } else {
            ViewCompat.setScaleX(title, scale);
            ViewCompat.setScaleY(title, scale);
            ViewCompat.setTranslationY(tab, 0);


            ViewCompat.setAlpha(icon, 0.6f);
            ViewCompat.setAlpha(title, 0);
        }
    }

    private void handleBackgroundColorChange(int tabPosition, View tab) {

        if (mColorMap != null && mColorMap.containsKey(tabPosition)) {
            handleBackgroundColorChange(tab, mColorMap.get(tabPosition));
        } else {
            handleBackgroundColorChange(tab, mDefaultBackgroundColor);
        }
    }

    private void handleBackgroundColorChange(View tab, int color) {
        Tools.animateBGColorChange(tab,
                mBackgroundView,
                mBackgroundOverlay,
                color);
        mCurrentBackgroundColor = color;
    }

    private int findItemPosition(View viewToFind) {
        int position = 0;

        for (int i = 0; i < mItemContainer.getChildCount(); i++) {
            View candidate = mItemContainer.getChildAt(i);

            if (candidate.equals(viewToFind)) {
                position = i;
                break;
            }
        }

        return position;
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(TAG_BOTTOM_BAR_VIEW_INACTIVE)) {
            View oldTab = findViewWithTag(TAG_BOTTOM_BAR_VIEW_ACTIVE);

            unSelectTab(oldTab, true);
            selectTab(v, true);

            shiftingMagic(oldTab, v, true);
        }
    }

    private void shiftingMagic(View oldTab, View newTab, boolean animate) {
        if (oldTab instanceof FrameLayout) {
            // It's a badge, goddammit!
            oldTab = ((FrameLayout) oldTab).getChildAt(0);
        }

        if (newTab instanceof FrameLayout) {
            // It's a badge, goddammit!
            newTab = ((FrameLayout) newTab).getChildAt(0);
        }

        if (animate) {
            Tools.resizeTab(oldTab, oldTab.getWidth(), mInActiveShiftingItemWidth);
            Tools.resizeTab(newTab, newTab.getWidth(), mActiveShiftingItemWidth);
        } else {
            oldTab.getLayoutParams().width = mInActiveShiftingItemWidth;
            newTab.getLayoutParams().width = mActiveShiftingItemWidth;
        }
    }

    public interface OnBottomBarClickListener {
        void onBottomBarSelected(@IdRes int menuItemId);
        void onBottomBarReSelected(@IdRes int menuItemId);
    }
}
