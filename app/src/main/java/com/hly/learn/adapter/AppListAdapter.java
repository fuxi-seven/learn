package com.hly.learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hly.learn.R;
import com.hly.learn.util.AppItemInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> {

    private Context mContext;
    private List<List<AppItemInfo>> mAppList = new ArrayList<>();

    public AppListAdapter(Context context) {
        mContext = context;
    }

    public void updateData(List<List<AppItemInfo>> appList) {
        mAppList.clear();
        mAppList.addAll(appList);
        notifyDataSetChanged();
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gridview_layout, parent, false);
        return new AppListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        if (mAppList != null) {
            holder.mGridView.setAdapter(new AppItemInfoAdapter(mContext, mAppList.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    static class AppListViewHolder extends RecyclerView.ViewHolder {

        GridView mGridView;

        public AppListViewHolder(View itemView) {
            super(itemView);
            mGridView = itemView.findViewById(R.id.grid_view);
        }
    }
}
