package com.rumofuture.nemo.model.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class BookRepository implements BookDataSource {

    private static BookRepository sInstance;

    private final BookDataSource mBookLocalDataSource;
    private final BookDataSource mBookRemoteDataSource;

    public static BookRepository getInstance(
            @NonNull BookDataSource nemoLocalDataSource,
            @NonNull BookDataSource nemoRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new BookRepository(nemoLocalDataSource, nemoRemoteDataSource);
        }
        return sInstance;
    }

    private BookRepository(
            @NonNull BookDataSource bookLocalDataSource,
            @NonNull BookDataSource bookRemoteDataSource
    ) {
        mBookLocalDataSource = bookLocalDataSource;
        mBookRemoteDataSource = bookRemoteDataSource;
    }

    @Override
    public void saveBook(Book book, NemoCallback<Book> callback) {
        mBookRemoteDataSource.saveBook(book, callback);
    }

    @Override
    public void deleteBook(Book book, NemoCallback<Book> callback) {
        mBookRemoteDataSource.deleteBook(book, callback);
    }

    @Override
    public void updateBook(Book book, @Nullable BmobFile newCover, NemoCallback<Book> callback) {
        mBookRemoteDataSource.updateBook(book, newCover, callback);
    }

    @Override
    public void getBookListWithAuthor(int pageIndex, NemoCallback<List<Book>> callback) {
        mBookRemoteDataSource.getBookListWithAuthor(pageIndex, callback);
    }

    @Override
    public void getBookListByAuthor(User author, int pageIndex, boolean self, NemoCallback<List<Book>> callback) {
        mBookRemoteDataSource.getBookListByAuthor(author, pageIndex, );
    }

    @Override
    public void getBookListByStyle(String style, int pageIndex, NemoCallback<List<Book>> callback) {

    }

    @Override
    public void getFavoriteBookList(User favor, int pageIndex, NemoCallback<List<Book>> callback) {

    }

    @Override
    public void getAuthorBookTotal(User author, Integer type, NemoCallback<Integer> callback) {

    }

    @Override
    public void getAlbumBookTotal(Album album, NemoCallback<Integer> callback) {

    }

    @Override
    public void getFavoriteBookTotal(User favor, NemoCallback<Integer> callback) {

    }

    @Override
    public void searchBook(String keyword, NemoCallback<List<Book>> callback) {

    }
}
