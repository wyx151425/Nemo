package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyBookUpdateContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public class MyBookUpdatePresenter implements MyBookUpdateContract.Presenter {

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
        mBookRepository.updateBook(book, null, new NemoCallback<Book>() {
            @Override
            public void onSuccess(Book data) {
                if (mView.isActive()) {
                    mView.showProgressBar(false);
                    mView.showBookUpdateSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showProgressBar(false);
                    mView.showBookUpdateFailed(message);
                }
            }
        });
    }
}
