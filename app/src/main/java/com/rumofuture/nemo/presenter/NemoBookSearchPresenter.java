package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.app.contract.NemoBookSearchContract;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/18.
 */

public class NemoBookSearchPresenter implements NemoBookSearchContract.Presenter, BookDataSource.BookListGetCallback {

    private NemoBookSearchContract.View mView;
    private BookDataSource mBookRepository;

    public NemoBookSearchPresenter(
            @NonNull NemoBookSearchContract.View view,
            @NonNull BookDataSource bookRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void searchBook(String keyword) {
        mView.showProgressBar(true);
        mBookRepository.searchBook(keyword, this);
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showBookSearchSuccess(bookList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookSearchFailed(e);
            mView.showProgressBar(false);
        }
    }
}
