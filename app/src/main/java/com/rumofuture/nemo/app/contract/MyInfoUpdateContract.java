package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.presenter.NemoImageUploadPresenter;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public interface MyInfoUpdateContract {

    interface View extends NemoView<Presenter> {
        void showProgressBar(boolean show);

        void showUserAvatarUpdateSuccess(BmobFile avatar);
        void showUserAvatarUpdateFailed(String message);

        void showUserPortraitUpdateSuccess(BmobFile portrait);
        void showUserPortraitUpdateFailed(String message);

        void showUserInfoUpdateSuccess();
        void showUserInfoUpdateFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoImageUploadPresenter {
        void updateUserAvatar();
        void updateUserPortrait();

        void updateUserInfo(User user);
    }
}
