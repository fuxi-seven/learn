package com.hly.learn.fragments;

import android.view.View;
import android.widget.Button;

import com.hly.learn.R;
import com.hly.learn.view.SelfDeterminedLayout;

import java.util.ArrayList;
import java.util.List;

public class SelfDeterminedFragment extends BaseFragment {

    private SelfDeterminedLayout mSelfDeterminedLayout;
    private Button mUpdateTxt;
    private List<String> mHotWordList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.self_determined_layout;
    }

    @Override
    public void initData(View view) {
        mSelfDeterminedLayout = view.findViewById(R.id.selfDetermine);
        initHotWord(1);
        mSelfDeterminedLayout.addView(mHotWordList);
        mUpdateTxt = view.findViewById(R.id.update);
        mUpdateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateTxt.getText().equals("切换")) {
                    initHotWord(0);
                    mSelfDeterminedLayout.addView(mHotWordList);
                    mUpdateTxt.setText("切回");
                } else {
                    initHotWord(1);
                    mSelfDeterminedLayout.addView(mHotWordList);
                    mUpdateTxt.setText("切换");
                }
                mSelfDeterminedLayout.invalidate();
            }
        });
    }

    private void initHotWord(int num) {
        if (num == 1) {
            mHotWordList.clear();
            mHotWordList.add("2020值得记忆");
            mHotWordList.add("侄子侄女可继承叔伯遗产");
            mHotWordList.add("素媛案罪犯出狱后被限制饮酒");
            mHotWordList.add("欧阳娜娜 陈飞宇");
            mHotWordList.add("灯芯绒穿搭");
            mHotWordList.add("父亲回应未给女儿打狂犬疫苗原因");
            mHotWordList.add("柳州袋装螺蛳粉产销超百亿元");
            mHotWordList.add("魏晨再唱成全画面回放王鸥");
            mHotWordList.add("外交部回应美或将80家中企列入黑名单");
            mHotWordList.add("舒克和贝塔慎重结婚的原因");
        } else {
            mHotWordList.clear();
            mHotWordList.add("舒克和贝塔慎重结婚的原因");
            mHotWordList.add("热依扎3天瘦了7斤");
            mHotWordList.add("刘昊然 我是唱歌不跑调的刘顶流");
            mHotWordList.add("威廉王子最新全家福");
            mHotWordList.add("郫都区新增确诊病例所在小区升为中风险");
            mHotWordList.add("明星大侦探");
            mHotWordList.add("美团杀熟门当事人回应");
            mHotWordList.add("江苏一公园出现手机充电座椅");
            mHotWordList.add("周深唱了明侦所有恐怖童谣");
            mHotWordList.add("郭炜炜");
            mHotWordList.add("新冠疫苗");
        }
    }
}
