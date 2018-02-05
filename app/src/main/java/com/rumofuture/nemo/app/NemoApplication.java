package com.rumofuture.nemo.app;

import android.support.multidex.MultiDexApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by WangZhenqi on 2016/12/24.
 */
public class NemoApplication extends MultiDexApplication {

    private static final String APPLICATION_ID = "abb5e03f6ca7fa31873723c10eb48099";

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, APPLICATION_ID);
    }
}
