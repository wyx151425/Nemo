package com.rumofuture.nemo.presenter;


import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyBookListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.schema.UserSchema;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class MyBookListPresenter implements MyBookListContract.Presenter, UserDataSource.UserInfoUpdateCallback,
        BookDataSource.BookListGetCallback, BookDataSource.BookDeleteCallback, BookDataSource.TotalGetCallback {

    private MyBookListContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;

    public MyBookListPresenter(
            @NonNull MyBookListContract.View view,
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
    public void getMyBookList(int pageCode) {
        mBookRepository.getBookListByAuthor(
                BmobUser.getCurrentUser(User.class), pageCode, true, this
        );
        mBookRepository.getAuthorBookTotal(
                BmobUser.getCurrentUser(User.class), true, this
        );
    }

    @Override
    public void deleteBook(Book book) {
        if (0 < book.getPageTotal()) {
            mView.showBookDeleteFailed(new BmobException("删除失败：请先删除漫画册内所有漫画分页"));
        } else {
            mView.showProgressBar(true);
            mBookRepository.deleteBook(book, this);
        }
    }

    @Override
    public void updateMyBookTotalOnCreate() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.increment(UserSchema.Table.Cols.BOOK_TOTAL);
        mUserRepository.updateUserInfo(currentUser, this);
    }

    @Override
    public void updateMyBookTotalOnDelete() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.increment(UserSchema.Table.Cols.BOOK_TOTAL, -1);
        mUserRepository.updateUserInfo(currentUser, this);
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showBookListGetSuccess(bookList);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookListGetFailed(e);
        }
    }

    @Override
    public void onBookDeleteSuccess(Book book) {
        if (mView.isActive()) {
            mView.showBookDeleteSuccess(book);
            mView.showProgressBar(false);
        }
        updateMyBookTotalOnDelete();
    }

    @Override
    public void onBookDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookDeleteFailed(e);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (!Objects.equals(currentUser.getBookTotal(), total)) {
            currentUser.setBookTotal(total);
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
