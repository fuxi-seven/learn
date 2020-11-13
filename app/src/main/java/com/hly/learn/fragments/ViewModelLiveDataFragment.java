package com.hly.learn.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hly.learn.R;
import com.hly.learn.databinding.LivedataLayoutBinding;
import com.hly.learn.viewmodel.MainViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class ViewModelLiveDataFragment extends BaseFragment {

    private MainViewModel mMainViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.livedata_layout;
    }

    @Override
    public void initData(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LivedataLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.livedata_layout, container, false);
        View view = binding.getRoot();
        mMainViewModel = ViewModelProviders.of(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);
        binding.setViewModel(mMainViewModel);
        binding.setLifecycleOwner(this);// <-- this enables MutableLiveData to be update on your UI
        return view;
    }
}
