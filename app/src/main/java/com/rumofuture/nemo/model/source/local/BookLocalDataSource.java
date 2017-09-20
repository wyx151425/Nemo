package com.rumofuture.nemo.model.source.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.BookDataSource;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class BookLocalDataSource implements BookDataSource {

    private static BookLocalDataSource sInstance;

    private Context mContext;

    public static BookLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new BookLocalDataSource(context);
        return sInstance;
    }

    private BookLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void saveBook(Book book, BookSaveCallback callback) {

    }

    @Override
    public void deleteBook(Book book, BookDeleteCallback callback) {

    }

    @Override
    public void updateBook(Book book, @Nullable BmobFile newCover, BookUpdateCallback callback) {

    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, boolean self, BookListGetCallback callback) {

    }

    @Override
    public void getBookListWithAuthor(int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getBookListByStyle(String style, int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getFavoriteBookList(User favor, int pageCode, BookListGetCallback callback) {

    }

    @Override
    public void getAuthorBookTotal(User author, boolean self, TotalGetCallback callback) {

    }

    @Override
    public void getAlbumBookTotal(Album album, TotalGetCallback callback) {

    }

    @Override
    public void getFavoriteBookTotal(User favor, TotalGetCallback callback) {

    }

    @Override
    public void searchBook(String keyword, BookListGetCallback callback) {

    }
}
