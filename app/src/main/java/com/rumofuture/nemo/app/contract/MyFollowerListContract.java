package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/8.
 */

public interface MyFollowerListContract {

    interface View extends NemoView<MyFollowerListContract.Presenter> {
        void showMyFollowerListGetSuccess(List<User> followerList);
        void showMyFollowerListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getMyFollowerList(int pageCode);
    }
}
