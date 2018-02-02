package com.rumofuture.nemo.model.source;

import android.support.annotation.Nullable;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * @author 王振琦 2017/1/29
 */
public interface BookDataSource {

    int PAGE_LIMIT = 32;

    /**
     * 保存漫画册的方法
     *
     * @param book     漫画册
     * @param callback 回调接口
     */
    void saveBook(Book book, NemoCallback<Book> callback);

    /**
     * 删除漫画册的方法
     *
     * @param book     漫画册
     * @param callback 回调接口
     */
    void deleteBook(Book book, NemoCallback<Book> callback);

    /**
     * 更新漫画册的方法
     *
     * @param book     漫画册
     * @param newCover 新封面文件
     * @param callback 回调接口
     */
    void updateBook(Book book, @Nullable BmobFile newCover, NemoCallback<Book> callback);

    /**
     * 直接获取 漫画册信息和漫画册所属漫画家信息 的方法
     *
     * @param pageIndex 分页索引
     * @param callback  回调接口
     */
    void getBookListWithAuthor(int pageIndex, NemoCallback<List<Book>> callback);

    /**
     * 根据漫画作者获得属于此漫画家的漫画册 的方法
     *
     * @param author    作者
     * @param pageIndex 分页索引
     * @param own       是否查询自己的漫画册 非作者本人时 被禁止展示和私有的漫画册不选择在内
     * @param callback  回调接口
     */
    void getBookListByAuthor(User author, int pageIndex, boolean own, NemoCallback<List<Book>> callback);

    /**
     * 根据漫画册风格获取指定风格的漫画册
     *
     * @param style     风格
     * @param pageIndex 分页索引
     * @param callback  回调接口
     */
    void getBookListByStyle(String style, int pageIndex, NemoCallback<List<Book>> callback);

    /**
     * 获取用户收藏的漫画册
     *
     * @param favor     收藏者
     * @param pageIndex 分页索引
     * @param callback  回调接口
     */
    void getFavoriteBookList(User favor, int pageIndex, NemoCallback<List<Book>> callback);

    /**
     * 获取漫画册总数
     *
     * @param author   作者
     * @param own      是否查询自己的漫画册 非作者本人时 被禁止展示和私有的漫画册不统计在内
     * @param callback 回调接口
     */
    void getAuthorBookTotalNumber(User author, boolean own, NemoCallback<Integer> callback);

    /**
     * 获取专辑的漫画册总数
     *
     * @param album    专辑
     * @param callback 回调接口
     */
    void getAlbumBookTotalNumber(Album album, NemoCallback<Integer> callback);

    /**
     * 获取收藏的漫画册总数
     *
     * @param favor    收藏者
     * @param callback 回调接口
     */
    void getFavoriteBookTotalNumber(User favor, NemoCallback<Integer> callback);

    /**
     * 根据关键词搜索漫画册
     *
     * @param keyword  关键词
     * @param callback 回调接口
     */
    void searchBook(String keyword, NemoCallback<List<Book>> callback);
}
