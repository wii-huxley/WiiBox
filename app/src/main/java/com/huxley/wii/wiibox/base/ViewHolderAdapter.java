package com.huxley.wii.wiibox.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by huxley on 16/2/29.
 */
public abstract class ViewHolderAdapter<VH extends ViewHolderAdapter.ViewHolder,B> extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<B> beans;

    public ViewHolderAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        beans = new ArrayList<>();
    }

    public ViewHolderAdapter(Context context, List<B> beans) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public B getItem(int position) {
        return beans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH viewHolder;
        if (convertView == null) {
            viewHolder = onCreateViewHolder(parent, position);
            viewHolder.view.setTag(viewHolder);
        } else {
            viewHolder = (VH) convertView.getTag();
        }
        onBindViewHolder(viewHolder, position);
        return viewHolder.view;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int position);
    public abstract void onBindViewHolder(VH holder, int position);

    public View inflate(int resLayout, ViewGroup parent) {
        return inflater.inflate(resLayout, parent, false);
    }

    public List<B> getBeans() {
        return beans;
    }

    public void setBeans(List<B> beans) {
        this.beans.addAll(beans);
    }

    public static class ViewHolder {
        View view;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            this.view = view;
        }
    }
}
