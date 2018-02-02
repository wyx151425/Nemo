package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoBookSearchContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/18.
 */

public class NemoBookSearchPresenter implements NemoBookSearchContract.Presenter {

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
        mBookRepository.searchBook(keyword, new NemoCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> data) {
                if (mView.isActive()) {
                    mView.showBookSearchSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showBookSearchFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }
}
