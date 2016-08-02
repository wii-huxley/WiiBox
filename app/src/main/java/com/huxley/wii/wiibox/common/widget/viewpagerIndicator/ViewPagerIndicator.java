package com.huxley.wii.wiibox.common.widget.viewpagerIndicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ResHelper;
import com.huxley.wii.wiibox.common.widget.WiiViewPager;
import com.huxley.wii.wiibox.common.utils.CheckEmptyUtils;

import java.util.List;

/**
 * Created by huxley on 16/6/15.
 */
public class ViewPagerIndicator extends LinearLayout{

    private static final float RADIO_TRIANGLE_WIDTH = 1/6f;
    private static final int COUNT_DEFAULT_TAB = 4;
    private static final int COLOR_TEXT_NORMAL = 0x77ffffff;
    private static final int COLOR_TEXT_HIGH_LIGHT = 0xffffffff;
    /**
     * 三角形底边的最大值
     */
    private static final int DIMENSION_TRIANGLE_WIDTH_MAX = (int) (ResHelper.getScreenWidth() / 3 * RADIO_TRIANGLE_WIDTH);

    private Paint mPaint;
    private Path mPath;

    private int mTriangleWidth;
    private int mTriangleHeight;
    private int mInitTranslationX;
    private int mTranslationX;
    private int mTabVisibleCount;

    private List<String> mTitles;

    private ViewPager mViewPager;
    private WiiViewPager mWiiViewPager;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取可见Tab的数量
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        mTabVisibleCount = ta.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, COUNT_DEFAULT_TAB);
        if (mTabVisibleCount < 0) {
            mTabVisibleCount = COUNT_DEFAULT_TAB;
        }
        ta.recycle();
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ResHelper.getColor(R.color.color_fff));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGLE_WIDTH);
        mTriangleWidth = Math.max(DIMENSION_TRIANGLE_WIDTH_MAX, mTriangleWidth);
        mTriangleHeight = mTriangleWidth / 2;
        mInitTranslationX = w / mTabVisibleCount / 2 - mTriangleWidth / 2;

        initTriangle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int cCount = getChildCount();
        if (cCount == 0) {
            return;
        }
        for (int i = 0; i < cCount; i++) {
            View view = getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) view.getLayoutParams();
            lp.weight = 0;
            lp.width = ResHelper.getScreenWidth() / mTabVisibleCount;
            view.setLayoutParams(lp);
        }
        setItemClickEvent();
    }

    /**
     * 初始化三角形
     */
    private void initTriangle() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }

    /**
     * 指示器跟随手指进行滚动
     * @param position
     * @param offset
     */
    public void scroll(int position, float offset) {
        int tabWidth = getWidth() / mTabVisibleCount;
        mTranslationX = (int) (tabWidth * (offset + position));
        //容器移动，再tab处于移动至最后一个时
        if (position >= (mTabVisibleCount) && offset > 0 && getChildCount() > mTabVisibleCount) {
            if (mTabVisibleCount != 1) {
                this.scrollTo((int) ((position - (mTabVisibleCount - 2)) * tabWidth + tabWidth * offset), 0);
            } else {
                this.scrollTo((int) (tabWidth * (position + offset)), 0);
            }
        }
        invalidate();
    }

    public void setTabItemTitles(List<String> titles) {
        if (!CheckEmptyUtils.list(titles)) {
            this.removeAllViews();
            this.mTitles = titles;
            for (String title : mTitles) {
                addView(generateTextView(title));
            }
            setItemClickEvent();
        }
    }

    /**
     * 设置可见的tab的数量
     * @param count
     */
    public void setVisibleTabCount(int count) {
        mTabVisibleCount = count;
    }

    /**
     * 根据title创建Tab
     * @return
     */
    private View generateTextView(String title) {
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width = ResHelper.getScreenWidth() / mTabVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(COLOR_TEXT_NORMAL);
        tv.setLayoutParams(lp);
        return tv;
    }

    /**
     * 设置关联的ViewPager
     * @param viewPager
     * @param pos
     */
    public void setViewPager(ViewPager viewPager, int pos) {
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(pos);
        highLightTextView(pos);
    }

    public void setViewPager(WiiViewPager wiiViewPager, int position) {
        this.mWiiViewPager = wiiViewPager;
        mWiiViewPager.setPageChangedListener(new WiiViewPager.MyPageChangedListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }
        });
    }

    /**
     * 重置TAB文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView)view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    /**
     * 高亮某个Tab的文本
     * @param position
     */
    private void highLightTextView(int position) {
        resetTextViewColor();
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView)view).setTextColor(COLOR_TEXT_HIGH_LIGHT);
        }
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(finalI);
                    }
                }
            });
        }
    }
}
