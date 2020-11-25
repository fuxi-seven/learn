package com.hly.learn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hly.learn.hook.HookUtils;
import com.hly.learn.view.FragmentsAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentsAdapter mFragmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tableLayout);
        mFragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentsAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
