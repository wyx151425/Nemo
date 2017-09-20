package com.rumofuture.nemo.presenter;

import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.app.contract.NemoAlbumBookListContract;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public class NemoAlbumBookListPresenter implements NemoAlbumBookListContract.Presenter, BookDataSource.BookListGetCallback {

    private NemoAlbumBookListContract.View mView;
    private BookDataSource mBookRepository;

    public NemoAlbumBookListPresenter(NemoAlbumBookListContract.View view, BookDataSource bookRepository) {
        mView = view;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAlbumBookList(String style, int pageCode) {
        mBookRepository.getBookListByStyle(style, pageCode, this);
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showAlbumBooksGetSuccess(bookList);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAlbumBooksGetFailed(e);
        }
    }
}
