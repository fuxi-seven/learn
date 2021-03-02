package com.hly.learn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hly.learn.viewmodel.ViewModel;
import com.hly.learn.databinding.KeyboardLayoutBinding;

import com.hly.learn.R;

import androidx.databinding.DataBindingUtil;

public class KeyboardFragment extends BaseFragment {

    private ViewModel mViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.keyboard_layout;
    }

    @Override
    public void initData(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        KeyboardLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.keyboard_layout, container, false);
        View view = binding.getRoot();
        //注意此处每次配置更新后都会重新创建新的ViewModel，不会保存数据
        mViewModel = new ViewModel(mContext);
        binding.setGuide(mViewModel);
        return view;
    }
}
