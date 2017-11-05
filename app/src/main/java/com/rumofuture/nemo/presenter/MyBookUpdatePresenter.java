package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyBookUpdateContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public class MyBookUpdatePresenter implements MyBookUpdateContract.Presenter, BookDataSource.BookUpdateCallback {

    private MyBookUpdateContract.View mView;
    private BookDataSource mBookRepository;

    public MyBookUpdatePresenter(
            @NonNull MyBookUpdateContract.View view,
            @NonNull BookDataSource bookRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void updateBook(Book book) {
        mView.showProgressBar(true);
        mBookRepository.updateBook(book, null, this);
    }

    @Override
    public void onBookUpdateSuccess(Book book) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showBookUpdateSuccess(book);
        }
    }

    @Override
    public void onBookUpdateFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showBookUpdateFailed(e);
        }
    }
}
