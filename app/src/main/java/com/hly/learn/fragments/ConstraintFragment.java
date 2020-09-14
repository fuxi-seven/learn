package com.hly.learn.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hly.learn.R;

import androidx.constraintlayout.widget.Group;

public class ConstraintFragment extends BaseFragment implements View.OnClickListener{
    @Override
    public int getLayoutId() {
        return R.layout.constrain_layout;
    }

    @Override
    public void initData(View view) {
        mGroup = view.findViewById(R.id.group);
        Button login = view.findViewById(R.id.button2);
        Button setup = view.findViewById(R.id.button3);
        login.setOnClickListener(this);
        setup.setOnClickListener(this);
        mEditText1 = view.findViewById(R.id.editTextTextPersonName);
        mEditText2 = view.findViewById(R.id.editTextTextPersonName2);
    }

    Group mGroup;
    EditText mEditText1;
    EditText mEditText2;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                if (mEditText1.getText().toString().equals("xinzhiayun") && mEditText2.getText().toString().equals("881127")) {
                    mGroup.setVisibility(View.VISIBLE);
                    mGroup.requestLayout();
                }
                break;
            case R.id.button3:
                mGroup.setVisibility(View.INVISIBLE);
                mGroup.requestLayout();
                break;
            default:
                break;
        }
    }
}
