package com.huxley.wii.wiibox.common.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.huxley.wii.wiibox.common.Constant;
import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.beans.FolderBean;
import com.huxley.wii.wiibox.common.helper.ResHelper;
import com.huxley.wii.wiibox.common.ui.photoAlbum.DirListAdapter;

import java.util.List;

/**
 * Created by huxley on 16/6/17.
 */
public class ListImageDirPopupWindow extends PopupWindow {

    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private ListView mListView;
    private List<FolderBean> mDatas;
    private OnDirSelectListener mListener;

    public ListImageDirPopupWindow(Context context, List<FolderBean> datas) {
        calWidthAndHeight(context);

        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_photo_list, null);
        mDatas = datas;

        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_OUTSIDE:
                        dismiss();
                        return true;
                }
                return false;
            }
        });

        initView(context);
        initEvent();
    }

    private void initEvent() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onSelected(mDatas.get(position));
                }
            }
        });
    }

    private void initView(Context context) {
        mListView = (ListView) mConvertView.findViewById(R.id.lvDir);
        mListView.setAdapter(new DirListAdapter(context, mDatas));
    }

    /**
     * 计算popupWindow的宽度和高度
     * @param context
     */
    private void calWidthAndHeight(Context context) {
        mWidth = ResHelper.getScreenWidth();
        mHeight = (int) (ResHelper.getScreenHeight() * Constant.GOLDEN_RATIO);
    }

    public void setOnDirSelectListener(OnDirSelectListener listener) {
        mListener = listener;
    }

    public interface OnDirSelectListener{
        void onSelected(FolderBean folderBean);
    }
}
