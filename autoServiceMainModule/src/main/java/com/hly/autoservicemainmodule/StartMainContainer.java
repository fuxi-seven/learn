package com.hly.autoservicemainmodule;

import android.content.Context;

import com.google.auto.service.AutoService;
import com.hly.autoservicecommonlibrary.startmain.IStartMainContainer;

@AutoService(IStartMainContainer.class)
public class StartMainContainer implements IStartMainContainer {
    @Override
    public void startMainActivity(Context context) {
        MainContainerActivity.start(context);
    }
}
