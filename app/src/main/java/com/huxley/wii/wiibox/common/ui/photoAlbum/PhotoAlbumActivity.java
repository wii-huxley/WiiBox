package com.huxley.wii.wiibox.common.ui.photoAlbum;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.beans.FolderBean;
import com.huxley.wii.wiibox.common.helper.ToastHelper;
import com.huxley.wii.wiibox.common.widget.ListImageDirPopupWindow;
import com.huxley.wii.wiitools.base.BaseActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PhotoAlbumActivity extends BaseActivity implements View.OnClickListener {

    private static final int DATA_LOADED = 0x110;

    private RelativeLayout mBottomLy;
    private TextView mDirName;
    private TextView mDirCount;

    private File mCurrentDir;
    private int mMaxCount;
    private ProgressDialog mProgressDialog;

    private List<FolderBean> mFolderBeans = new ArrayList<>();

    private List<String> mImgs;
    private GridView mGridView;
    private PhotoListAdapter mPhotoListAdapter;

    private ListImageDirPopupWindow mDirPopupWindow;

    private Handler mUIHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == DATA_LOADED) {
                mProgressDialog.dismiss();
                //绑定输入到view中
                dataToView();
                initDirPopupWindow();
            }
        }
    };

    private void initDirPopupWindow() {
        mDirPopupWindow = new ListImageDirPopupWindow(this, mFolderBeans);
        mDirPopupWindow.setOnDismissListener(this::lightOn);
        mDirPopupWindow.setOnDirSelectListener(folderBean -> {
            mCurrentDir = new File(folderBean.dir);
            mImgs = new ArrayList<>(Arrays.asList(mCurrentDir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png");
                }
            })));
            mPhotoListAdapter.upData(mImgs, mCurrentDir.getAbsolutePath());
            mDirCount.setText(String.valueOf(mImgs.size()));
            mDirName.setText(folderBean.name);
            mDirPopupWindow.dismiss();
        });
    }

    private void dataToView() {
        if (mCurrentDir == null) {
            ToastHelper.showInfo("未扫描到任何图片");
            return;
        }
        mImgs = new ArrayList<>(Arrays.asList(mCurrentDir.list()));
        mPhotoListAdapter = new PhotoListAdapter(this, mImgs, mCurrentDir.getAbsolutePath());
        mGridView.setAdapter(mPhotoListAdapter);

        mDirCount.setText(String.valueOf(mMaxCount));
        mDirName.setText(mCurrentDir.getName());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_list;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);

        initView();
        initDatas();
        initEvent();
    }

    private void initEvent() {
        mBottomLy.setOnClickListener(this);
    }

    /**
     * 利用ContentProvider扫描手机中所有的图片
     */
    private void initDatas() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastHelper.showInfo("当前存储卡不可用");
            return;
        }
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(){
            @Override
            public void run() {
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = PhotoAlbumActivity.this.getContentResolver();
                Cursor cursor = cr.query(
                        mImgUri,
                        null,
                        MediaStore.Images.Media.MIME_TYPE + " = ? or " + MediaStore.Images.Media.MIME_TYPE + " = ?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED
                );
                Set<String> mDirPaths = new HashSet<>();
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null) {
                        continue;
                    }
                    String dirPath = parentFile.getAbsolutePath();
                    FolderBean folderBean;
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        folderBean = new FolderBean();
                        folderBean.setDir(dirPath);
                        folderBean.firstImgPath = path;
                    }
                    if (parentFile.list() == null) {
                        continue;
                    }
                    int picSize = parentFile.list((dir, filename) -> filename.endsWith(".jpg") ||
                            filename.endsWith(".jpeg") || filename.endsWith(".png")).length;
                    folderBean.count = picSize;
                    mFolderBeans.add(folderBean);
                    if (picSize > mMaxCount) {
                        mMaxCount = picSize;
                        mCurrentDir = parentFile;
                    }
                }
                cursor.close();
                //通知handler扫描完成
                mUIHandler.sendEmptyMessage(DATA_LOADED);
            }
        }.start();
  }

    private void initView() {
        mGridView = $(R.id.gridView);
        mBottomLy = $(R.id.rl_bottom);
        mDirName = $(R.id.tvDirName);
        mDirCount = $(R.id.tvDirCount);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_bottom:
                mDirPopupWindow.setAnimationStyle(R.style.dir_popupWindow_anim);
                mDirPopupWindow.showAsDropDown(mBottomLy, 0 ,0);
                lightOff();
                break;
        }
    }

    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变亮
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }
}
