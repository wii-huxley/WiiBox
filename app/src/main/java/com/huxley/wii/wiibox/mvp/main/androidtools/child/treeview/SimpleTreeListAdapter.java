package com.huxley.wii.wiibox.mvp.main.androidtools.child.treeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huxley.wii.wiibox.R;
import com.huxley.wii.wiibox.common.widget.treeview.adapter.TreeListViewAdapter;
import com.huxley.wii.wiibox.common.widget.treeview.bean.Node;

import java.util.List;

/**
 * Created by huxley on 16/6/24.
 */
public class SimpleTreeListAdapter<T> extends TreeListViewAdapter {

    public SimpleTreeListAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getCovertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_tree, parent, false);
            holder = new ViewHolder();
            holder.mIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            holder.mText = (TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (node.icon == -1) {
            holder.mIcon.setVisibility(View.INVISIBLE);
        } else {
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageResource(node.icon);
        }
        holder.mText.setText(node.name);
        return convertView;
    }

    private class ViewHolder{
        ImageView mIcon;
        TextView mText;
    }
}
