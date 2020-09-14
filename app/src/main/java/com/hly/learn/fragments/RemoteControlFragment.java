package com.hly.learn.fragments;

import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;

import com.hly.learn.util.Bean;
import com.hly.learn.R;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RemoteControlFragment extends BaseFragment {

    List<Bean> beanList = new ArrayList<>();
    private Handler mHandler = new UIHandler(this);
    private TextView mDisplayText;

    @Override
    public int getLayoutId() {
        return R.layout.remotecontrol_layout;
    }

    @Override
    public void initData(View view) {
        mDisplayText = view.findViewById(R.id.display_text);
        mDisplayText.setMovementMethod(ScrollingMovementMethod.getInstance());
        pullParseXml();
    }

    /**
     * UI update handler
     */
    private static class UIHandler extends Handler {

        private static final int MSG_UPDATE_UI = 1;
        private static final int MSG_UPDATE_ERR_UI = 2;

        private final WeakReference<RemoteControlFragment> mActivity;

        private UIHandler(RemoteControlFragment activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            RemoteControlFragment activity = mActivity.get();
            if (activity == null) {
                Log.e("XML", "activity is null, handleMessage return");
                super.handleMessage(msg);
                return;
            }
            switch (msg.what) {
                case MSG_UPDATE_UI:
                    activity.displayText(false);
                    break;
                case MSG_UPDATE_ERR_UI:
                    activity.displayText(true);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    private void displayText(boolean isError) {
        if (isError) {
            mDisplayText.setText("解析失败");
        } else {
            mDisplayText.setText(getParseContent());
        }
    }

    private StringBuffer getParseContent() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < beanList.size(); i++) {
            stringBuffer.append(beanList.get(i).toString());
        }
        return stringBuffer;
    }

    private void pullParseXml() {
        final Message message = new Message();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://bbs.csdn.net/recommend_tech_topics.atom");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        Log.e("XML", "请求成功");
                        InputStream is = conn.getInputStream();
                        beanList = getNewsInfo(is);
                        Log.e("XML", beanList.size() + "");
                        // 成功获取数据 给主线程发消息
                        message.what = 1;
                    } else {
                        Log.e("XML", "code is : " + code);
                        message.what = 2;
                    }
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    // 获取数据失败，给主线程发消息，处理数据
                    message.what = 2;
                    mHandler.sendMessage(message);
                    e.printStackTrace();
                }

            }
        }).start();

    }

    // 返回信息集合
    public static List<Bean> getNewsInfo(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser(); // 获取Pull解析器
        parser.setInput(is, "utf-8");
        List<Bean> list = null;
        Bean bean = null;

        // 得到当前事件的类型
        int type = parser.getEventType();

        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                // XML文档的开始START_DOCUMENT 例如：<?xml version="1.0" encoding="UTF-8"?> 0
                case XmlPullParser.START_DOCUMENT:
                    list = new ArrayList<>();
                    break;
                // XML文档节点开始START_TAG 例如：<entry> 2
                case XmlPullParser.START_TAG:
                    if ("entry".equals(parser.getName())) {
                        //Log.e("XML", "<ebtry>");
                        bean = new Bean();
                    } else if ("id".equals(parser.getName())) {
                        String path = parser.nextText();
                        if (bean != null) {
                            bean.setPath(path);
                        }
                        //Log.e("XML", "path == " + path + "parse == " + parser.getName());
                    } else if ("published".equals(parser.getName())) {
                        String published = parser.nextText();
                        if (bean != null) {
                            bean.setPublished(published);
                        }
                        //Log.e("XML", "published == " + published + "parse == " + parser.getName
                        // ());
                    } else if ("updated".equals(parser.getName())) {
                        String updated = parser.nextText();
                        if (bean != null) {
                            bean.setUpdated(updated);
                        }
                        //Log.e("XML", "updated == " + updated + "parse == " + parser.getName());
                    } else if ("title".equals(parser.getName())) {
                        String title = parser.nextText();
                        if (bean != null) {
                            bean.setTitle(title);
                        }
                        //Log.e("XML", "title == " + title + "parse == " + parser.getName());
                    } else if ("summary".equals(parser.getName())) {
                        String summary = parser.nextText();
                        if (bean != null) {
                            bean.setSummary(summary);
                        }
                        //Log.e("XML", "summary == " + summary + "parse == " + parser.getName());
                    } else if ("author".equals(parser.getName())) {
                        String author = parser.nextText();
                        if (bean != null) {
                            bean.setAuthor(author);
                        }
                        //Log.e("XML", "author == " + author + "parse == " + parser.getName());
                    }
                    break;
                // XML文档的结束节点 如</entry> 3
                case XmlPullParser.END_TAG:
                    if ("entry".equals(parser.getName())) {
                        //Log.e("XML", "解析xml一个节点完成" + parser.getName());
                        // 处理完一个entry标签
                        list.add(bean);
                        bean = null;
                    }
                    break;
            }
            type = parser.next(); // 解析下一个节点
        }
        return list;
    }
}
