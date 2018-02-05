package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoAlbumBookListContract;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.AlbumDataSource;
import com.rumofuture.nemo.model.source.BookDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public class NemoAlbumBookListPresenter implements NemoAlbumBookListContract.Presenter {

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
        mBookRepository.getBookListByStyle(style, pageCode, new NemoCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> data) {
                if (mView.isActive()) {
                    mView.showAlbumBooksGetSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showAlbumBooksGetFailed(message);
                }
            }
        });
        mAlbumRepository.getAlbumByStyle(style, new NemoCallback<Album>() {
            @Override
            public void onSuccess(Album data) {
                mAlbum = data;
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }
}
