package com.huxley.wii.wiibox.common.widget.gobang;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * 五子棋view
 * Created by huxley on 16/6/10.
 */
public class GobangPanel extends View {

    private int mPanelWidth;
    private float mLineHeight;
    private int MAX_LINE = 10;

    private Paint mPaint = new Paint();

//    private Bitmap mWhitePiece;
//    private Bitmap mBlackPiece;

    private boolean mIsGameOver;
    private boolean mIsWhiteWinner;

    private float ratioPieceOfLineHeight = 3.0f / 4;

    private int radiu;

    //白棋先手，当前轮到白棋
    private boolean mIsWhite = true;
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    private GobangListener mLintener;

    public GobangPanel(Context context) {
        this(context, null);
    }

    public GobangPanel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GobangPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(0x88000000);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getMode(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        } else {
            width = Math.min(widthSize, heightSize);
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mPanelWidth = w;
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;
        radiu = (int) (mLineHeight * ratioPieceOfLineHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsGameOver) {
            return false;
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                Point p = new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
                if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {
                    return false;
                }

                if (mIsWhite) {
                    mWhiteArray.add(p);
                } else {
                    mBlackArray.add(p);
                }
                invalidate();
                mIsWhite = !mIsWhite;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    private void checkGameOver() {
        boolean whiteWin = GobangUtils.checkFiveInLine(mWhiteArray);
        boolean blackWin = GobangUtils.checkFiveInLine(mBlackArray);
        if (whiteWin || blackWin) {
            mIsGameOver = true;
            if (mLintener != null) {
                mLintener.onWin(whiteWin);
            }
            mIsWhiteWinner = whiteWin;
        }
    }

    private void drawPieces(Canvas canvas) {
        for (int i = 0; i < mWhiteArray.size(); i++) {
            mPaint.setColor(0xffffffff);
            Point whitePoint = mWhiteArray.get(i);
            float x = (whitePoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight;
            float y = (whitePoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight;
            canvas.drawOval(x, y, x + radiu, y + radiu,mPaint);
        }
        for (int i = 0; i < mBlackArray.size(); i++) {
            mPaint.setColor(0xff000000);
            Point blackPoint = mBlackArray.get(i);
            float x = (blackPoint.x + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight;
            float y = (blackPoint.y + (1 - ratioPieceOfLineHeight) / 2) * mLineHeight;
            canvas.drawOval(x, y, x + radiu, y + radiu,mPaint);
        }
    }

    private void drawBoard(Canvas canvas) {
        mPaint.setColor(0x88000000);
        int w = mPanelWidth;
        float lineHeight = mLineHeight;
        for (int i = 0; i < MAX_LINE; i++) {
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, endX, y, mPaint);
            canvas.drawLine(y, startX, y, endX, mPaint);
        }
    }

    private static final String INSTANCE = "instance";
    private static final String INSTANCE_GAME_OVER = "instance_game_over";
    private static final String INSTANCE_WHITE_ARRAY = "instance_white_array";
    private static final String INSTANCE_BLACK_ARRAY = "instance_black_array";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAME_OVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAME_OVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void reStart() {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver = false;
        mIsWhiteWinner = false;
        invalidate();
    }

    public void setWuZiQiLintener(GobangListener listener) {
        mLintener = listener;
    }
}