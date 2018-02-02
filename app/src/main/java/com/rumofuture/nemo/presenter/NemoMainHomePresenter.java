package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoMainContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class NemoMainHomePresenter implements NemoMainContract.Presenter {

    private NemoMainContract.View mView;
    private BookDataSource mBookRepository;

    public NemoMainHomePresenter(
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
        mBookRepository.getBookListWithAuthor(pageCode, new NemoCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> data) {
                if (mView.isActive()) {
                    mView.showBookListGetSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showBookListGetFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }
}
