package com.hly.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hly.learn.R;
import com.hly.learn.util.AppItemInfo;

import java.util.ArrayList;
import java.util.List;

public class AppItemInfoAdapter extends BaseAdapter {

    private List<AppItemInfo> mAppItemInfoList = new ArrayList<>();
    private Context mContext;

    public AppItemInfoAdapter(Context context, List<AppItemInfo> infoList) {
        mContext = context;
        mAppItemInfoList.clear();
        mAppItemInfoList.addAll(infoList);
    }

    @Override
    public int getCount() {
        return mAppItemInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAppItemInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final AppItemInfo itemInfo = mAppItemInfoList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.app_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.image = convertView.findViewById(R.id.iv_launch);
            viewHolder.name = convertView.findViewById(R.id.tv_launch);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.image.setImageDrawable(itemInfo.icon);
        viewHolder.name.setText(itemInfo.title);
        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(itemInfo.intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView image;
        TextView name;
    }
}
