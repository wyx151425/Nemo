package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public interface PageDataSource {

    int PAGE_LIMIT = 64;

    void savePage(Page page, NemoCallback<Page> callback);
    void deletePage(Page page, NemoCallback<Page> callback);
    void updatePage(Page page, BmobFile newImage, NemoCallback<Page> callback);
    void getPageListByBook(Book book, int pageIndex, NemoCallback<List<Page>> callBack);
    void getPageTotalNumber(Book book, NemoCallback<Integer> callback);
}
