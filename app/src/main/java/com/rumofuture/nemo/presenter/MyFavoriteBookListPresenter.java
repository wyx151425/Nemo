package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyFavoriteBookListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFavoriteBookListPresenter implements MyFavoriteBookListContract.Presenter, UserDataSource.UserInfoUpdateCallback,
        BookDataSource.BookListGetCallback, BookDataSource.TotalGetCallback {

    private MyFavoriteBookListContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;

    public MyFavoriteBookListPresenter(
            @NonNull MyFavoriteBookListContract.View view,
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
    public void getMyFavoriteBookList(int pageCode) {
        mBookRepository.getFavoriteBookList(
                BmobUser.getCurrentUser(User.class), pageCode, this
        );
        mBookRepository.getFavoriteBookTotal(
                BmobUser.getCurrentUser(User.class), this
        );
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showMyFavoriteBookListGetSuccess(bookList);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showMyFavoriteBookListGetFailed(e);
        }
    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (!Objects.equals(currentUser.getFavoriteTotal(), total)) {
            currentUser.setFavoriteTotal(total);
            mUserRepository.updateUserInfo(currentUser, this);
        }
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
