package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoAuthorSearchContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class NemoAuthorSearchPresenter implements NemoAuthorSearchContract.Presenter {

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
        mUserRepository.searchAuthor(keyword, new NemoCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> data) {
                if (mView.isActive()) {
                    mView.showAuthorSearchSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showAuthorSearchFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }
}
