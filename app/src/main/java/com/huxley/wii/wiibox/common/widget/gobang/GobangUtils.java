package com.huxley.wii.wiibox.common.widget.gobang;

import android.graphics.Point;

import com.thefinestartist.utils.log.LogUtil;

import java.util.List;

/**
 * 五子棋工具类
 * Created by huxley on 16/6/10.
 */
public class GobangUtils {

    private static final int MAX_COUNT_IN_LINE = 5;

    public static boolean checkFiveInLine(List<Point> points) {
        for (Point p : points) {
            int x = p.x;
            int y = p.y;
            if (checkHorizontal(x, y, points)) {
                LogUtil.i("checkHorizontal");
                return true;
            }
            if (checkVertical(x, y, points)) {
                LogUtil.i("checkVertical");
                return true;
            }
            if (checkLeftDiagonal(x, y, points)) {
                LogUtil.i("checkLeftDiagonal");
                return true;
            }
            if (checkRightDiagonal(x, y, points)) {
                LogUtil.i("checkRightDiagonal");
                return true;
            }
        }
        return false;
    }

    /**
     * 判断x, y位置的棋子，是否横向有相邻的五个一致。
     */
    private static boolean checkHorizontal(int x, int y, List<Point> points) {
        int count = 1;
        //左

        LogUtil.i("检测点 ： (" + x + ", " + y + ")");
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y))) {
                LogUtil.i(new Point(x - i, y).toString());
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        //右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y))) {
                LogUtil.i(new Point(x - i, y).toString());
                count++;
            } else {
                break;
            }
        }
        return count == MAX_COUNT_IN_LINE;
    }

    /**
     * 判断x, y位置的棋子，是否纵向有相邻的五个一致。
     */
    private static boolean checkVertical(int x, int y, List<Point> points) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        //右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }
        return count == MAX_COUNT_IN_LINE;
    }

    /**
     * 判断x, y位置的棋子，是否左斜有相邻的五个一致。
     */
    private static boolean checkLeftDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        //右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + 1, y - i))) {
                count++;
            } else {
                break;
            }
        }
        return count == MAX_COUNT_IN_LINE;
    }

    /**
     * 判断x, y位置的棋子，是否右斜有相邻的五个一致。
     */
    private static boolean checkRightDiagonal(int x, int y, List<Point> points) {
        int count = 1;
        //左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }
        if (count == MAX_COUNT_IN_LINE) {
            return true;
        }
        //右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }
        return count == MAX_COUNT_IN_LINE;
    }

}
