package com.rumofuture.nemo.app;

import android.support.multidex.MultiDexApplication;

import com.rumofuture.nemo.model.entity.Device;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.exception.BmobException;
import rx.functions.Action1;

/**
 * Created by WangZhenqi on 2016/12/24.
 */

public class NemoApplication extends MultiDexApplication {

    private static final String APPLICATION_ID = "abb5e03f6ca7fa31873723c10eb48099";

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, APPLICATION_ID);

        // 使用推送服务时的初始化操作
        BmobInstallationManager.getInstance().initialize(new InstallationListener<BmobInstallation>() {
            @Override
            public void done(BmobInstallation bmobInstallation, BmobException e) {

            }
        });
        // 启动推送服务
        BmobPush.startWork(this);
    }
}
