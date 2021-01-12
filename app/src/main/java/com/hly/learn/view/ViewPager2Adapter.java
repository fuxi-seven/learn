package com.hly.learn.view;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * adapter for viewpager2
 */
public class ViewPager2Adapter extends FragmentStateAdapter {

    List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPager2Adapter(@NonNull FragmentActivity fragment, List<Fragment> fragmentList) {
        super(fragment);
        mFragmentList.clear();
        mFragmentList.addAll(fragmentList);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
