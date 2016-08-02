package com.huxley.wii.wiibox.common.ui.photoAlbum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.base.ViewHolderAdapter;
import com.huxley.wii.wiibox.beans.FolderBean;
import com.huxley.wii.wiibox.common.utils.ImageLoader;

import java.util.List;

/**
 * Created by huxley on 16/6/16.
 */
public class DirListAdapter extends ViewHolderAdapter<DirListAdapter.DirListHolder, FolderBean> {

    public DirListAdapter(Context context, List<FolderBean> beans) {
        super(context, beans);
    }

    @Override
    public DirListHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new DirListHolder(inflate(R.layout.item_popup_photo_list, parent));
    }

    @Override
    public void onBindViewHolder(final DirListHolder holder, int position) {
        FolderBean item = getItem(position);
        holder.mImg.setImageResource(R.drawable.pictures_no);
        ImageLoader.getInstance().loadImage(item.firstImgPath, holder.mImg);
        holder.mDirName.setText(item.name);
        holder.mDirCount.setText(String.valueOf(item.count));
    }

    class DirListHolder extends ViewHolderAdapter.ViewHolder {

        ImageView mImg;
        TextView mDirName, mDirCount;

        public DirListHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.id_dir_item_image);
            mDirName = (TextView) view.findViewById(R.id.id_dir_item_name);
            mDirCount = (TextView) view.findViewById(R.id.id_dir_item_count);
        }
    }
}
