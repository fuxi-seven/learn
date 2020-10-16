package com.hly.learn.fragments;

import static android.os.Process.myUserHandle;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.view.View;

import com.hly.learn.R;
import com.hly.learn.adapter.AppListAdapter;
import com.hly.learn.util.AppItemInfo;
import com.hly.learn.view.LinePagerIndicatorDecoration;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class AppListFragment extends BaseFragment {

    private static final int APPS_COUNTS_PER_PAGE = 16;
    private List<AppItemInfo> mAppList = new ArrayList<>();
    private List<List<AppItemInfo>> mRecyclerList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.recyclerview_layout;
    }

    @Override
    public void initData(View view) {
        RecyclerViewPager appListRecyclerViewPager = (RecyclerViewPager) view.findViewById(R.id.app_list_recyclerview);
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        appListRecyclerViewPager.setLayoutManager(layout);
        appListRecyclerViewPager.addItemDecoration(new LinePagerIndicatorDecoration(mContext));
        final AppListAdapter appListAdapter = new AppListAdapter(mContext);
        appListRecyclerViewPager.setAdapter(appListAdapter);
        appListRecyclerViewPager.setHasFixedSize(true);
        loadData();
        appListAdapter.updateData(mRecyclerList);
    }

    private void loadData() {
        LauncherApps launcherApps = (LauncherApps) mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        final List<LauncherActivityInfo> apps = launcherApps.getActivityList(null,
                myUserHandle());
        for (int i = 0; i < apps.size(); i++) {
            mAppList.add(new AppItemInfo(apps.get(i), myUserHandle()));
        }
        int size = mAppList.size();
        int yu = size % APPS_COUNTS_PER_PAGE;
        int zheng = size / APPS_COUNTS_PER_PAGE;
        if (size <= APPS_COUNTS_PER_PAGE) {
            mRecyclerList.add(mAppList);
        } else if (yu != 0) {
            for (int i = 0; i <= zheng; i++) {
                if (i == zheng) {
                    mRecyclerList.add(mAppList.subList(i * APPS_COUNTS_PER_PAGE, zheng * APPS_COUNTS_PER_PAGE + yu));
                } else {
                    mRecyclerList.add(mAppList.subList(i * APPS_COUNTS_PER_PAGE, (i + 1) * APPS_COUNTS_PER_PAGE));
                }
            }
        } else {
            for (int i = 0; i < zheng; i++) {
                mRecyclerList.add(mAppList.subList(i * APPS_COUNTS_PER_PAGE, (i + 1) * APPS_COUNTS_PER_PAGE));
            }
        }
    }
}
