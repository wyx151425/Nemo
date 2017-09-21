package com.rumofuture.nemo.model.entity;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by WangZhenqi on 2017/9/21.
 */

public class Device extends BmobInstallation {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
