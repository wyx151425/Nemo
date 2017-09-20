package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.app.contract.NemoAuthorSearchContract;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class NemoAuthorSearchPresenter implements NemoAuthorSearchContract.Presenter, UserDataSource.UserListGetCallback {

    private NemoAuthorSearchContract.View mView;
    private UserDataSource mUserRepository;

    public NemoAuthorSearchPresenter(
            @NonNull NemoAuthorSearchContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchAuthor(String keyword) {
        mView.showProgressBar(true);
        mUserRepository.searchAuthor(keyword, this);
    }

    @Override
    public void onUserListGetSuccess(List<User> userList) {
        if (mView.isActive()) {
            mView.showAuthorSearchSuccess(userList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorSearchFailed(e);
            mView.showProgressBar(false);
        }
    }
}
