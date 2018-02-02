package com.rumofuture.nemo.presenter;


import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
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

public class MyBookListPresenter implements MyBookListContract.Presenter {

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
                BmobUser.getCurrentUser(User.class), pageCode, true, new NemoCallback<List<Book>>() {
                    @Override
                    public void onSuccess(List<Book> data) {
                        if (mView.isActive()) {
                            mView.showBookListGetSuccess(data);
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        if (mView.isActive()) {
                            mView.showBookListGetFailed(message);
                        }
                    }
                }
        );
        mBookRepository.getAuthorBookTotalNumber(
                BmobUser.getCurrentUser(User.class), true, new NemoCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        User currentUser = BmobUser.getCurrentUser(User.class);
                        if (!Objects.equals(currentUser.getBook(), data)) {
                            currentUser.setBook(data);
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

    @Override
    public void deleteBook(Book book) {
        if (0 < book.getPage()) {
            mView.showBookDeleteFailed("删除失败：请先删除漫画册内所有漫画分页");
        } else {
            mView.showProgressBar(true);
            mBookRepository.deleteBook(book, new NemoCallback<Book>() {
                @Override
                public void onSuccess(Book data) {
                    if (mView.isActive()) {
                        mView.showBookDeleteSuccess(data);
                        mView.showProgressBar(false);
                    }
                    updateMyBookTotalOnDelete();
                }

                @Override
                public void onFailed(String message) {
                    if (mView.isActive()) {
                        mView.showBookDeleteFailed(message);
                        mView.showProgressBar(false);
                    }
                }
            });
        }
    }

    @Override
    public void updateMyBookTotalOnCreate() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.increment(UserSchema.Table.Cols.BOOK);
        mUserRepository.updateUserInfo(currentUser, new NemoCallback<User>() {
            @Override
            public void onSuccess(User data) {

            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    @Override
    public void updateMyBookTotalOnDelete() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        currentUser.increment(UserSchema.Table.Cols.BOOK, -1);
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
