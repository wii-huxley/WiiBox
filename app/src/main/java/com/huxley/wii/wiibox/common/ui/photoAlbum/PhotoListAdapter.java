package com.huxley.wii.wiibox.common.ui.photoAlbum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.base.ViewHolderAdapter;
import com.huxley.wii.wiibox.common.helper.ResHelper;
import com.huxley.wii.wiibox.common.utils.ImageLoader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huxley on 16/6/16.
 */
public class PhotoListAdapter extends ViewHolderAdapter<PhotoListAdapter.PhotoListHolder, String> {

    private static Set<String> mSeletedImg = new HashSet<>();

    private String mDirPath;

    public PhotoListAdapter(Context context, List<String> beans, String dirPath) {
        super(context, beans);
        this.mDirPath = dirPath;
    }

    @Override
    public PhotoListHolder onCreateViewHolder(ViewGroup parent, int position) {
        return new PhotoListHolder(inflate(R.layout.item_photo_list, parent));
    }

    @Override
    public void onBindViewHolder(final PhotoListHolder holder, int position) {
        holder.mImg.setImageResource(R.drawable.pictures_no);
        holder.mSelect.setImageResource(R.drawable.picture_unselected);
        holder.mImg.setColorFilter(null);

        holder.mImg.setMaxWidth(ResHelper.getScreenWidth() / 3);

        final String fliePath = mDirPath + "/" + getItem(position);
        ImageLoader.getInstance(3, ImageLoader.Type.LIFO).loadImage(fliePath, holder.mImg);
        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSeletedImg.contains(fliePath)) {//已经被选择
                    mSeletedImg.remove(fliePath);
                    holder.mImg.setColorFilter(null);
                    holder.mSelect.setImageResource(R.drawable.picture_unselected);
                } else {//未被选择
                    mSeletedImg.add(fliePath);
                    holder.mImg.setColorFilter(ResHelper.getColor(R.color.color_7000));
                    holder.mSelect.setImageResource(R.drawable.pictures_selected);
                }
            }
        });

        if (mSeletedImg.contains(fliePath)) {//已经被选择
            holder.mImg.setColorFilter(ResHelper.getColor(R.color.color_7000));
            holder.mSelect.setImageResource(R.drawable.pictures_selected);
        }
    }

    public void upData(List<String> beans, String dirPath) {
        this.mDirPath = dirPath;
        List<String> datas = getBeans();
        datas.clear();
        datas.addAll(beans);
        notifyDataSetChanged();
    }

    class PhotoListHolder extends ViewHolderAdapter.ViewHolder{

        ImageView mImg;
        ImageButton mSelect;

        public PhotoListHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.iv);
            mSelect = (ImageButton) view.findViewById(R.id.ibSelect);
        }
    }
}
