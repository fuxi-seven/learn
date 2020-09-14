package com.hly.learn.view;

import com.hly.learn.manager.FragmentsManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentsAdapter extends FragmentPagerAdapter{

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentsManager.getFragment(position);
    }

    @Override
    public int getCount() {
        return FragmentsManager.getCount();
    }

    /**tab对应的title，在setupWithViewPager(mViewPager)里面使用
    for (int i = 0; i < adapterCount; i++) {
        addTab(newTab().setText(pagerAdapter.getPageTitle(i)), false);
    }*/
    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentsManager.getTitle(position);
    }

}
