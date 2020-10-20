package com.hly.learn.fragments;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hly.learn.R;

import java.lang.ref.WeakReference;

public class HandlerFragment extends BaseFragment implements View.OnClickListener{

    private UIHandler mUIHandler = new UIHandler(this);
    private NoUIHandler mNoUIHandler;
    private Button mStartBtn;
    private Button mSendBtn;
    private Button mEndBtn;

    @Override
    public int getLayoutId() {
        return R.layout.handler_layout;
    }

    @Override
    public void initData(View view) {
        mStartBtn = view.findViewById(R.id.start_btn);
        mSendBtn = view.findViewById(R.id.send_btn);
        mEndBtn = view.findViewById(R.id.end_btn);
        mStartBtn.setOnClickListener(this);
        mSendBtn.setOnClickListener(this);
        mEndBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                createHandlerInThread(this);
                break;
            case R.id.send_btn:
                mNoUIHandler.sendEmptyMessage(NoUIHandler.MSG_HANDLE);
                break;
            case R.id.end_btn:
                mNoUIHandler.getLooper().quitSafely();
                break;
            default:
                break;
        }
    }

    private static class UIHandler extends Handler {
        private final static int MSG_UPDATE_UI = 1;

        private final WeakReference<HandlerFragment> mFragment;

        private UIHandler(HandlerFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            HandlerFragment fragment = mFragment.get();

            switch (msg.what) {
                case MSG_UPDATE_UI:
                    fragment.updateUI((int)msg.obj);
                    break;
                default:
                    break;
            }
        }
    }

    private static class NoUIHandler extends Handler {
        private final static int MSG_HANDLE = 1;

        private final WeakReference<HandlerFragment> mFragment;

        private NoUIHandler(HandlerFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }
        @Override
        public void handleMessage(Message msg) {
            HandlerFragment fragment = mFragment.get();

            switch (msg.what) {
                case MSG_HANDLE:
                    fragment.handleMsg();
                    break;
                default:
                    break;
            }
        }
    }

    private void createHandlerInThread(final HandlerFragment handlerFragment) {
        new Thread() {
            public void run() {
                mUIHandler.sendMessage(mUIHandler.obtainMessage(UIHandler.MSG_UPDATE_UI,0));
                Looper.prepare();
                mNoUIHandler = new NoUIHandler(handlerFragment);
                Looper.loop();
                //调用mNoUIHandler.getLooper().quitSafely()后，才会执行以下逻辑
                mUIHandler.sendMessage(mUIHandler.obtainMessage(UIHandler.MSG_UPDATE_UI,2));
            }
        }.start();
    }

    private void updateUI(int msg) {
        String text;
        if (msg == 0) {
            text = "子线程创建了Handler";
        } else if (msg == 1) {
            text = "子线程Handler收到消息";
        } else {
            text = "子线程结束了Looper";
        }
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
        }
    }

    private void handleMsg() {
        //判断调用方是否为主线程,如果是子线程就弹出toast
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mUIHandler.sendMessage(mUIHandler.obtainMessage(UIHandler.MSG_UPDATE_UI,1));
        }
    }
}
