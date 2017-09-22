package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyFollowAuthorListContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFollowAuthorListPresenter implements MyFollowAuthorListContract.Presenter, UserDataSource.UserListGetCallback,
        UserDataSource.TotalGetCallback, UserDataSource.UserInfoUpdateCallback {

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
                BmobUser.getCurrentUser(User.class), pageCode, this
        );
        mUserRepository.getFollowAuthorTotal(
                BmobUser.getCurrentUser(User.class), this
        );
    }

    @Override
    public void onUserListGetSuccess(List<User> userList) {
        if (mView.isActive()) {
            mView.showFollowUserListGetSuccess(userList);
        }
    }

    @Override
    public void onUserListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFollowUserListGetFailed(e);
        }
    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.setFollowTotal(total);
        mUserRepository.updateUserInfo(currentUser, this);
    }

    @Override
    public void onTotalGetFailed(BmobException e) {

    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }
}
