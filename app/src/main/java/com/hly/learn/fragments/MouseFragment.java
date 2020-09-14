package com.hly.learn.fragments;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hly.learn.R;


public class MouseFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.mouse_layout;
    }

    @Override
    public void initData(View view) {
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadUrl("http://www.baidu.com");
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
    }
}
