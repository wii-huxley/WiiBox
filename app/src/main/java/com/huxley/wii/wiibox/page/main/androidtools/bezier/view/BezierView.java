package com.huxley.wii.wiibox.page.main.androidtools.bezier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *
 * Created by LeiJin01 on 2016/8/18.
 */
public class BezierView extends View {

    private Paint mPaintBezier;
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;

    private float[][] mAuxiliaryPoints = new float[2][2];

    private int currentPoint;

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private int mOrder;

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
        currentPoint = 0;
        mOrder = 1;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX = w / 4;
        mStartPointY = h / 2;

        mEndPointX = w / 4 * 3;
        mEndPointY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);


        canvas.drawText("起始点", mStartPointX, mStartPointY, mPaintAuxiliaryText);
        canvas.drawText("终止点", mEndPointX, mEndPointY, mPaintAuxiliaryText);
        // 辅助线
//        canvas.drawLine(mStartPointX, mStartPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
//        canvas.drawLine(mEndPointX, mEndPointY, mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
        // 二阶贝塞尔曲线

        if (mOrder == 2) {
            // 辅助点
//            canvas.drawPoint(mAuxiliaryX, mAuxiliaryY, mPaintAuxiliary);
//            canvas.drawText("控制点", mAuxiliaryX, mAuxiliaryY, mPaintAuxiliaryText);
            mPath.quadTo(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mEndPointX, mEndPointY);
        } else {
            mPath.cubicTo(mAuxiliaryPoints[0][0], mAuxiliaryPoints[0][1], mAuxiliaryPoints[1][0], mAuxiliaryPoints[1][1], mEndPointX, mEndPointY);
        }

        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mAuxiliaryPoints[currentPoint][0] = event.getX();
                mAuxiliaryPoints[currentPoint][1] = event.getY();
                invalidate();
        }
        return true;
    }

    public void setOrder(int order) {
        this.mOrder = order;
    }

    public void setCurrentPoint(int position) {
        currentPoint = position;
    }
}
