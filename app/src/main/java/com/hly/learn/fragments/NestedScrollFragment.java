package com.hly.learn.fragments;

import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hly.learn.R;
import com.hly.learn.view.RecyclerviewFragment;
import com.hly.learn.view.ViewPager2Adapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class NestedScrollFragment extends BaseFragment {

    private NestedScrollView mNestedScrollView;
    private LinearLayout mTabView;
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private String[] tabs = new String[]{"菜系推荐", "菜单", "主播美食", "排行榜"};

    @Override
    public int getLayoutId() {
        return R.layout.nestedscroll_layout;
    }

    @Override
    public void initData(View view) {
        mNestedScrollView = view.findViewById(R.id.nestScrollView);
        mTabView = view.findViewById(R.id.tableLayout_viewpager);
        mTabLayout = mTabView.findViewById(R.id.table_layout);
        mViewPager = mTabView.findViewById(R.id.view_pager);
        ViewPager2Adapter viewPagerAdapter = new ViewPager2Adapter(this.getActivity(), getFragmentList());
        mViewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(mTabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabs[position]);
                    }
                }).attach();
        //通过post确保view已经绘制完成
        mTabView.post(new Runnable() {
            @Override
            public void run() {
                //实现吸顶效果
                mTabView.getLayoutParams().height = mNestedScrollView.getMeasuredHeight();
                mTabView.requestLayout();
            }
        });
    }

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new RecyclerviewFragment());
        fragmentList.add(new RecyclerviewFragment());
        fragmentList.add(new RecyclerviewFragment());
        fragmentList.add(new RecyclerviewFragment());
        return fragmentList;
    }
}
