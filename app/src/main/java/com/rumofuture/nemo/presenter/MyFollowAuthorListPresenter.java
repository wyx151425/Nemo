package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyFollowAuthorListContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFollowAuthorListPresenter implements MyFollowAuthorListContract.Presenter {

    private MyFollowAuthorListContract.View mView;
    private UserDataSource mUserRepository;

    public MyFollowAuthorListPresenter(
            @NonNull MyFollowAuthorListContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMyFollowAuthorList(int pageCode) {
        mUserRepository.getFollowAuthorList(
                BmobUser.getCurrentUser(User.class), pageCode, new NemoCallback<List<User>>() {
                    @Override
                    public void onSuccess(List<User> data) {
                        if (mView.isActive()) {
                            mView.showFollowUserListGetSuccess(data);
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        if (mView.isActive()) {
                            mView.showFollowUserListGetFailed(message);
                        }
                    }
                }
        );
        mUserRepository.getFollowAuthorTotal(
                BmobUser.getCurrentUser(User.class), new NemoCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        User currentUser = BmobUser.getCurrentUser(User.class);
                        if (!Objects.equals(currentUser.getFollow(), data)) {
                            currentUser.setFollow(data);
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
