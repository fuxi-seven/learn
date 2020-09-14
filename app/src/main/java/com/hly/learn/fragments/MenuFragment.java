package com.hly.learn.fragments;

import android.view.View;
import android.widget.GridView;

import com.hly.learn.R;
import com.hly.learn.adapter.MenuAdapter;

public class MenuFragment extends BaseFragment {

    private GridView mGridView;

    @Override
    public int getLayoutId() {
        return R.layout.menu_layout;
    }

    @Override
    public void initData(View view) {
        mGridView = view.findViewById(R.id.gridView);
        MenuAdapter adapter = new MenuAdapter(mContext);
        mGridView.setAdapter(adapter);
    }
}
