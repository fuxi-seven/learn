package com.hly.learn.view;

import android.view.View;

import com.hly.learn.R;
import com.hly.learn.adapter.NestedItemAdapter;
import com.hly.learn.fragments.BaseFragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerviewFragment extends BaseFragment {

    RecyclerView mRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.nested_recycler_layout;
    }

    @Override
    public void initData(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(60, 0));
        NestedItemAdapter itemAdapter = new NestedItemAdapter(mContext);
        mRecyclerView.setAdapter(itemAdapter);
    }
}
