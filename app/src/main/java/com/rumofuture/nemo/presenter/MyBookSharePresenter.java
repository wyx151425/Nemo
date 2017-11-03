package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyBookShareContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.schema.UserSchema;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public class MyBookSharePresenter implements MyBookShareContract.Presenter, UserDataSource.UserInfoUpdateCallback, BookDataSource.BookSaveCallback {

    private MyBookShareContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;

    public MyBookSharePresenter(
            @NonNull MyBookShareContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull BookDataSource bookRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void shareBook(Book book) {
        mView.showProgressBar(true);
        mBookRepository.saveBook(book, this);
    }

    @Override
    public void onBookSaveSuccess(Book book) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showBookShareSuccess(book);
        }

        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.increment(UserSchema.Table.Cols.BOOK_TOTAL);
        mUserRepository.updateUserInfo(currentUser, this);
    }

    @Override
    public void onBookSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showBookShareFailed(e);
        }
    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }
}
