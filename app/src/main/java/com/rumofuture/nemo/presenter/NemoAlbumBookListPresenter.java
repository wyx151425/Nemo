package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.AlbumDataSource;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.app.contract.NemoAlbumBookListContract;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public class NemoAlbumBookListPresenter implements NemoAlbumBookListContract.Presenter, BookDataSource.BookListGetCallback,
        BookDataSource.TotalGetCallback, AlbumDataSource.AlbumUpdateCallback, AlbumDataSource.AlbumGetCallback {

    private NemoAlbumBookListContract.View mView;
    private BookDataSource mBookRepository;
    private AlbumDataSource mAlbumRepository;

    private Album mAlbum;

    public NemoAlbumBookListPresenter(
            @NonNull NemoAlbumBookListContract.View view,
            @NonNull BookDataSource bookRepository,
            @NonNull AlbumDataSource albumRepository
    ) {
        mView = view;
        mBookRepository = bookRepository;
        mAlbumRepository = albumRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAlbumBookList(String style, int pageCode) {
        mBookRepository.getBookListByStyle(style, pageCode, this);
        mAlbumRepository.getAlbumByStyle(style, this);
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

    @Override
    public void onAlbumGetSuccess(Album album) {
        mAlbum = album;
        mBookRepository.getAlbumBookTotal(album, this);
    }

    @Override
    public void onAlbumGetFailed(BmobException e) {

    }

    @Override
    public void onTotalGetSuccess(Integer total) {
        mAlbum.setBookTotal(total);
        mAlbumRepository.updateAlbum(mAlbum, this);
    }

    @Override
    public void onTotalGetFailed(BmobException e) {

    }

    @Override
    public void onAlbumUpdateSuccess(Album album) {

    }

    @Override
    public void onAlbumUpdateFailed(BmobException e) {

    }
}
