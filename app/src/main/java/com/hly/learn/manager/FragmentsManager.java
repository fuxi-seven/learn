package com.hly.learn.manager;

import com.hly.learn.fragments.AnimatorFragment;
import com.hly.learn.fragments.AppListFragment;
import com.hly.learn.fragments.ConstraintFragment;
import com.hly.learn.fragments.GalleryFragment;
import com.hly.learn.fragments.HandlerFragment;
import com.hly.learn.fragments.InjectFragment;
import com.hly.learn.fragments.JniFragment;
import com.hly.learn.fragments.KeyboardFragment;
import com.hly.learn.fragments.KotlinFragment;
import com.hly.learn.fragments.MenuFragment;
import com.hly.learn.fragments.MouseFragment;
import com.hly.learn.fragments.NestedScrollFragment;
import com.hly.learn.fragments.ProxyFragment;
import com.hly.learn.fragments.RemoteControlFragment;
import com.hly.learn.fragments.RxJavaFragment;
import com.hly.learn.fragments.SelfDeterminedFragment;
import com.hly.learn.fragments.ViewModelLiveDataFragment;
import com.hly.learn.fragments.VolleyRequestFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentsManager {

    private static List<String> DEFAULT_LIST = new ArrayList<>();

    static {
        DEFAULT_LIST.add("网格");
        DEFAULT_LIST.add("网页");
        DEFAULT_LIST.add("DataBinding");
        DEFAULT_LIST.add("MVVM");
        DEFAULT_LIST.add("xml解析");
        DEFAULT_LIST.add("画廊");
        DEFAULT_LIST.add("自定义View");
        DEFAULT_LIST.add("约束布局");
        DEFAULT_LIST.add("JNI学习");
        DEFAULT_LIST.add("Kotlin");
        DEFAULT_LIST.add("Animator");
        DEFAULT_LIST.add("RxJava");
        DEFAULT_LIST.add("RecyclerViewPager");
        DEFAULT_LIST.add("Handler");
        DEFAULT_LIST.add("Java代理及Hook");
        DEFAULT_LIST.add("网络请求");
        DEFAULT_LIST.add("注解实现");
        DEFAULT_LIST.add("NestedScrollView");
    }

    public static Fragment getFragment(int position) {
        switch (position) {
            case 0:
                return new MenuFragment();
            case 1:
                return new MouseFragment();
            case 2:
                return new KeyboardFragment();
            case 3:
                return new ViewModelLiveDataFragment();
            case 4:
                return new RemoteControlFragment();
            case 5:
                return new GalleryFragment();
            case 6:
                return new SelfDeterminedFragment();
            case 7:
                return new ConstraintFragment();
            case 8:
                return new JniFragment();
            case 9:
                return new KotlinFragment();
            case 10:
                return new AnimatorFragment();
            case 11:
                return new RxJavaFragment();
            case 12:
                return new AppListFragment();
            case 13:
                return new HandlerFragment();
            case 14:
                return new ProxyFragment();
            case 15:
                return new VolleyRequestFragment();
            case 16:
                return new InjectFragment();
            case 17:
                return new NestedScrollFragment();
            default:
                return null;
        }
    }

    public static int getCount() {
        return DEFAULT_LIST.size();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public static String getTitle(int index) {
        return DEFAULT_LIST.get(index);
    }
}
