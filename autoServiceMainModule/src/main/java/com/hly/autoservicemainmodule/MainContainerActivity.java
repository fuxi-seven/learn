package com.hly.autoservicemainmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hly.autoservicecommonlibrary.tab.ITabPage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainContainerActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;

    private List<Fragment> mFragmentList = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context,MainContainerActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container_activity);

        mNavigation = findViewById(R.id.navigation);
        serviceLoad();
        initEvent();

        mNavigation.setSelectedItemId(mNavigation.getMenu().getItem(0).getItemId());
    }

    private void serviceLoad(){
        //加载Fragment 类型的ServiceLoader
        ServiceLoader<Fragment> serviceLoader = ServiceLoader.load(Fragment.class);

        for (Iterator<Fragment> iterator = serviceLoader.iterator(); iterator.hasNext();) {
            final Fragment fragment = iterator.next();

            //获取ITabPage 注解的类
            ITabPage property = fragment.getClass().getAnnotation(ITabPage.class);
            if (property == null) {
                continue;
            }
            Menu menu = mNavigation.getMenu();
            MenuItem item = menu.add(property.tabName());
            int drawable = getResources().getIdentifier(property.iconName(), "drawable", getPackageName());
            item.setIcon(drawable);
            mFragmentList.add(fragment);
        }

    }

    private void initEvent(){
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int size = mNavigation.getMenu().size();
                int index = 0;

                for (int i = 0; i < size; i++) {
                    if (mNavigation.getMenu().getItem(i) == item) {
                        index = i;
                        break;
                    }
                }

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, mFragmentList.get(index))
                        .commit();

                return true;
            }
        });
    }
}
