package com.rumofuture.nemo.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class PageRepository implements PageDataSource {

    private static PageRepository sInstance;
    private final PageDataSource mPageLocalDataSource;
    private final PageDataSource mPageRemoteDataSource;

    public static PageRepository getInstance(
            @NonNull PageDataSource pageLocalDataSource,
            @NonNull PageDataSource pageRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new PageRepository(pageLocalDataSource, pageRemoteDataSource);
        }
        return sInstance;
    }

    private PageRepository(
            @NonNull PageDataSource pageLocalDataSource,
            @NonNull PageDataSource pageRemoteDataSource
    ) {
        mPageLocalDataSource = pageLocalDataSource;
        mPageRemoteDataSource = pageRemoteDataSource;
    }

    @Override
    public void savePage(Page page, NemoCallback<Page> callback) {
        mPageRemoteDataSource.savePage(page, callback);
    }

    @Override
    public void deletePage(Page page, NemoCallback<Page> callback) {
        mPageRemoteDataSource.deletePage(page, callback);
    }

    @Override
    public void updatePage(Page page, BmobFile newImage, NemoCallback<Page> callback) {
        mPageRemoteDataSource.updatePage(page, newImage, callback);
    }

    @Override
    public void getPageListByBook(Book book, int pageIndex, NemoCallback<List<Page>> callBack) {
        mPageRemoteDataSource.getPageListByBook(book, pageIndex, callBack);
    }

    @Override
    public void getPageTotalNumber(Book book, NemoCallback<Integer> callback) {
        mPageRemoteDataSource.getPageTotalNumber(book, callback);
    }
}
