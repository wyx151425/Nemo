package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public interface MyFollowAuthorListContract {

    interface View extends NemoView<MyFollowAuthorListContract.Presenter> {
        void showFollowUserListGetSuccess(List<User> authorList);
        void showFollowUserListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getMyFollowAuthorList(int pageCode);
    }
}
