package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.NemoMainContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class NemoMainPresenter implements NemoMainContract.Presenter, BookDataSource.BookListGetCallback {

    private NemoMainContract.View mView;
    private BookDataSource mBookRepository;

    public NemoMainPresenter(
            @NonNull NemoMainContract.View view,
            @NonNull BookDataSource bookRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getBookList(int pageCode) {
        if (0 == pageCode) {
            mView.showProgressBar(true);
        }
        mBookRepository.getBookListWithAuthor(pageCode, this);
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showBookListGetSuccess(bookList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookListGetFailed(e);
            mView.showProgressBar(false);
        }
    }
}
