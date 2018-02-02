package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoMainDiscoverContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/15.
 */

public class NemoMainDiscoverPresenter implements NemoMainDiscoverContract.Presenter {

    private NemoMainDiscoverContract.View mView;
    private UserDataSource mUserRepository;

    public NemoMainDiscoverPresenter(
            @NonNull NemoMainDiscoverContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorList(int pageCode) {
        if (0 == pageCode) {
            mView.showProgressBar(true);
        }
        mUserRepository.getAuthorList(pageCode, new NemoCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                if (mView.isActive()) {
                    mView.showAuthorListGetSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showAuthorListGetFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }
}
