package com.hly.learn.fragments;

import android.view.View;
import android.widget.Button;

import com.hly.learn.R;
import com.hly.learn.view.SelfDeterminedLayout;

public class SelfDeterminedFragment extends BaseFragment {

    private SelfDeterminedLayout mSelfDeterminedLayout;
    private Button mUpdateTxt;

    @Override
    public int getLayoutId() {
        return R.layout.self_determined_layout;
    }

    @Override
    public void initData(View view) {
        mSelfDeterminedLayout = view.findViewById(R.id.selfDetermine);
        mSelfDeterminedLayout.addView(2);
        mUpdateTxt = view.findViewById(R.id.update);
        mUpdateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateTxt.getText().equals("增加")) {
                    mSelfDeterminedLayout.addView(4);
                    mUpdateTxt.setText("减少");
                } else {
                    mSelfDeterminedLayout.addView(2);
                    mUpdateTxt.setText("增加");
                }
                mSelfDeterminedLayout.invalidate();
            }
        });
    }
}
