package com.rumofuture.nemo.model.source.local;

import android.content.Context;
import android.support.annotation.Nullable;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.BookDataSource;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class BookLocalDataSource implements BookDataSource {

    private static BookLocalDataSource sInstance;

    private Context mContext;

    public static BookLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BookLocalDataSource(context);
        }
        return sInstance;
    }

    private BookLocalDataSource(Context context) {
        mContext = context;
    }

    @Override
    public void saveBook(Book book, NemoCallback<Book> callback) {

    }

    @Override
    public void deleteBook(Book book, NemoCallback<Book> callback) {

    }

    @Override
    public void updateBook(Book book, @Nullable BmobFile newCover, NemoCallback<Book> callback) {

    }

    @Override
    public void getBookListWithAuthor(int pageIndex, NemoCallback<List<Book>> callback) {

    }

    @Override
    public void getBookListByAuthor(User author, int pageIndex, boolean own, NemoCallback<List<Book>> callback) {

    }

    @Override
    public void getBookListByStyle(String style, int pageIndex, NemoCallback<List<Book>> callback) {

    }

    @Override
    public void getFavoriteBookList(User favor, int pageIndex, NemoCallback<List<Book>> callback) {

    }

    @Override
    public void getAuthorBookTotalNumber(User author, boolean own, NemoCallback<Integer> callback) {

    }

    @Override
    public void getAlbumBookTotalNumber(Album album, NemoCallback<Integer> callback) {

    }

    @Override
    public void getFavoriteBookTotalNumber(User favor, NemoCallback<Integer> callback) {

    }

    @Override
    public void searchBook(String keyword, NemoCallback<List<Book>> callback) {

    }
}
