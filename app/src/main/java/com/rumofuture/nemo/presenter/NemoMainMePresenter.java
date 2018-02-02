package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class NemoMainMePresenter {

    private UserDataSource mUserRepository;

    public NemoMainMePresenter(
            @NonNull UserDataSource userRepository
    ) {
        mUserRepository = userRepository;
    }


    public void start() {

    }

    public void getAuthorization() {
        User user = BmobUser.getCurrentUser(User.class);
        user.setStatus(2);
        mUserRepository.updateUserInfo(user, new NemoCallback<User>() {
            @Override
            public void onSuccess(User data) {

            }

            @Override
            public void onFailed(String message) {
                BmobUser.getCurrentUser(User.class).setStatus(1);
            }
        });
    }
}
