package com.rumofuture.nemo.model.source;

import android.support.annotation.Nullable;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public interface BookDataSource {

    int PAGE_LIMIT = 32;

    // 保存漫画册的方法
    void saveBook(Book book, NemoCallback<Book> callback);
    // 删除漫画册的方法
    void deleteBook(Book book, NemoCallback<Book> callback);
    // 更新漫画册的方法
    void updateBook(Book book, @Nullable BmobFile newCover, NemoCallback<Book> callback);

    // 直接获取漫画册信息和漫画册所属漫画家信息的方法
    void getBookListWithAuthor(int pageIndex, NemoCallback<List<Book>> callback);
    // 根据漫画家获得属于此漫画家的漫画册的方法
    void getBookListByAuthor(User author, int pageIndex, boolean self, NemoCallback<List<Book>> callback);
    // 根据漫画册风格获取指定风格的漫画册
    void getBookListByStyle(String style, int pageIndex, NemoCallback<List<Book>> callback);
    // 获取用户收藏的漫画册
    void getFavoriteBookList(User favor, int pageIndex, NemoCallback<List<Book>> callback);

    void getAuthorBookTotal(User author, boolean isAuthor, NemoCallback<Integer> callback);
    void getAlbumBookTotal(Album album, NemoCallback<Integer> callback);
    void getFavoriteBookTotal(User favor, NemoCallback<Integer> callback);

    void searchBook(String keyword, NemoCallback<List<Book>> callback);
}
