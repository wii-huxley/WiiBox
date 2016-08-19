package com.huxley.wii.wiibox.page.main.androidtools.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.helper.ResHelper;

/**
 * BezierView
 * Created by LeiJin01 on 2016/8/18.
 */
public class BezierView extends View {

    private static final int TouchPointSize = 20;
    private static final String BEZIER_START_POINT = ResHelper.getString(R.string.bezier_start_point);
    private static final String BEZIER_END_POINT = ResHelper.getString(R.string.bezier_end_point);
    private static final String BEZIER_AUXILIARY_POINT = ResHelper.getString(R.string.bezier_auxiliary_point);
    private static final String BEZIER_AUXILIARY_POINT_1 = BEZIER_AUXILIARY_POINT + " - 1";
    private static final String BEZIER_AUXILIARY_POINT_2 = BEZIER_AUXILIARY_POINT + " - 2";

    private              Paint mPaintBezier;
    private              Paint mPaintAuxiliary;
    private              Paint mPaintAuxiliaryText;

    private float[][] mAuxiliaryPoints = new float[2][2];
    private boolean isInit;

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private boolean isSecondOrder;

    private Path mPath = new Path();

    public BezierView(Context context) {
        this(context, null, 0);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isInit = false;
        isSecondOrder = true;
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);

        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(2);

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(20);
    }

    private float[] mFirstInitPoint;
    private float[] mSecondInitPoint;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2;

        mFirstInitPoint = new float[]{w / 4, h / 4};
        mSecondInitPoint = new float[]{w / 4 * 3, h / 4};

        if (!isInit) {
            initAuxiliaryPoint();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);

        canvas.drawText(BEZIER_START_POINT, mStartPointX, mStartPointY, mPaintAuxiliaryText);
        canvas.drawText(BEZIER_END_POINT, mEndPointX, mEndPointY, mPaintAuxiliaryText);

        if (isSecondOrder) {
            // 辅助点
            canvas.drawPoint(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliary);
            canvas.drawText(BEZIER_AUXILIARY_POINT, mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliaryText);
            // 辅助线
            canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliary);
            canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliary);
            // 二阶贝塞尔曲线
            mPath.quadTo(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mEndPointX, mEndPointY);
        } else {
            // 辅助点
            canvas.drawPoint(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliary);
            canvas.drawText(BEZIER_AUXILIARY_POINT_1, mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliaryText);
            canvas.drawPoint(mAuxiliaryPoints[1][0], mAuxiliaryPoints[1][1], mPaintAuxiliary);
            canvas.drawText(BEZIER_AUXILIARY_POINT_2, mAuxiliaryPoints[1][0], mAuxiliaryPoints[1][1], mPaintAuxiliaryText);
            // 辅助线
            canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mPaintAuxiliary);
            canvas.drawLine(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mAuxiliaryPoints[1][0], mAuxiliaryPoints[1][1], mPaintAuxiliary);
            canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryPoints[1][0], mAuxiliaryPoints[1][1], mPaintAuxiliary);
            // 二阶贝塞尔曲线
            mPath.cubicTo(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mAuxiliaryPoints[1][0], mAuxiliaryPoints[1][1], mEndPointX, mEndPointY);
        }
        canvas.drawPath(mPath, mPaintBezier);
    }

    private int movePosition = -1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (Math.abs(event.getX() - mAuxiliaryPoints[0][0]) < TouchPointSize && Math.abs(event.getY() - mAuxiliaryPoints[0][1]) < TouchPointSize) {
                    movePosition = 0;
                } else if (!isSecondOrder && Math.abs(event.getX() - mAuxiliaryPoints[1][0]) < TouchPointSize && Math.abs(event.getY() - mAuxiliaryPoints[1][1]) < TouchPointSize) {
                    movePosition = 1;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (movePosition >= 0) {
                    float x = event.getX();
                    float y = event.getY();
                    if (x > 0 && x < getWidth() && y > 0 && y <getHeight()) {
                        mAuxiliaryPoints[movePosition][0] = x;
                        mAuxiliaryPoints[movePosition][1] = y;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                movePosition = -1;
                break;
        }
        return true;
    }

    public void setOrder(boolean isSecondOrder) {
        this.isSecondOrder = isSecondOrder;
        initAuxiliaryPoint();
        invalidate();
    }

    private void initAuxiliaryPoint() {
        if (isSecondOrder) {
            mAuxiliaryPoints[0] = mFirstInitPoint;
        } else {
            mAuxiliaryPoints[0] = mFirstInitPoint;
            mAuxiliaryPoints[1] = mSecondInitPoint;
        }
        isInit = true;
    }
}
