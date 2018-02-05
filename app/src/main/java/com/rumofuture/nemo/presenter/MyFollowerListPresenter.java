package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyFollowerListContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MyFollowerListPresenter implements MyFollowerListContract.Presenter {

    private MyFollowerListContract.View mView;
    private UserDataSource mUserRepository;

    public MyFollowerListPresenter(
            @NonNull MyFollowerListContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMyFollowerList(int pageCode) {
        mUserRepository.getFollowerList(
                BmobUser.getCurrentUser(User.class), pageCode, new NemoCallback<List<User>>() {
                    @Override
                    public void onSuccess(List<User> data) {
                        if (mView.isActive()) {
                            mView.showMyFollowerListGetSuccess(data);
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        if (mView.isActive()) {
                            mView.showMyFollowerListGetFailed(message);
                        }
                    }
                }
        );
        mUserRepository.getFollowerTotal(
                BmobUser.getCurrentUser(User.class), new NemoCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        User currentUser = BmobUser.getCurrentUser(User.class);
                        if (!Objects.equals(currentUser.getFollower(), data)) {
                            currentUser.setFollower(data);
                            mUserRepository.updateUserInfo(currentUser, new NemoCallback<User>() {
                                @Override
                                public void onSuccess(User data) {

                                }

                                @Override
                                public void onFailed(String message) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                }
        );
    }
}
