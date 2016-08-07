package com.huxley.wii.wiitools.common.factory;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huxley.wii.wiitools.R;
import com.huxley.wii.wiitools.base.WiiApplication;
import com.huxley.wii.wiitools.common.Utils.StringUtil;
import com.huxley.wii.wiitools.common.helper.ResHelper;
import com.huxley.wii.wiitools.common.helper.UIHelper;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.squareup.picasso.Picasso;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huxley on 16/8/6.
 */

public class DialogFactory {

    public static DialogPlus newPhotoDialog(Context context, String url) {
        List<String> urls = new ArrayList<>();
        urls.add(url);
        return DialogPlus.newDialog(context)
                .setAdapter(new CommonAdapter<String>(context, R.layout.wii_dialog_photo, urls) {
                    @Override
                    public void convert(ViewHolder holder, String s) {
                        Picasso.with(WiiApplication.getContext())
                                .load(StringUtil.isEmp(s))
                                .resize(ResHelper.getScreenWidth(), ResHelper.getScreenHeight())
                                .centerInside()
                                .into((ImageView) holder.getView(R.id.photoView));
                    }
                })
                .setContentHolder(new ListHolder())
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .create();
    }

    public static <D> DialogPlus newInstance(Context context, String title, String content, boolean expanded, List<D> data, final DataHandler<D> handler, OnItemClickListener listener) {

        View headerView = UIHelper.getView(context, R.layout.wii_dialog_header);
        TextView tvTitle = (TextView) headerView.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) headerView.findViewById(R.id.tvContent);
        tvTitle.setText(title);
        tvContent.setText(content);

        return DialogPlus.newDialog(context)
                .setAdapter(new CommonAdapter<D>(context, R.layout.wii_diglog_listitem, data) {
                    @Override
                    public void convert(com.zhy.base.adapter.ViewHolder holder, D d) {
                        if (d instanceof String) {
                            holder.setText(R.id.tvName, (String) d);
                        } else if (handler != null) {
                            holder.setText(R.id.tvName, handler.hand(d));
                        } else {
                            throw new NullPointerException("create DataHandler");
                        }
                    }
                })
                .setContentHolder(new ListHolder())
                .setCancelable(true)
                .setGravity(Gravity.BOTTOM)
                .setHeader(headerView)
                .setOnItemClickListener(listener)
                .setExpanded(expanded)
                .create();
    }

    public interface DataHandler<D>{
        String hand(D d);
    }
}
