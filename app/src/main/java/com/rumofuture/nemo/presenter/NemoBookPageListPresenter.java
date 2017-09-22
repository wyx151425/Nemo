package com.rumofuture.nemo.presenter;

import com.rumofuture.nemo.app.contract.NemoPageListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;
import com.rumofuture.nemo.model.source.PageDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class NemoBookPageListPresenter implements NemoPageListContract.Presenter, PageDataSource.PageListGetCallback {

    private NemoPageListContract.View mView;
    private PageDataSource mPageRepository;

    public NemoBookPageListPresenter(NemoPageListContract.View view, PageDataSource pageRepository) {
        mView = view;
        mPageRepository = pageRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getBookPageList(Book book, int pageCode) {
        mPageRepository.getPageListByBook(book, pageCode, this);
    }

    @Override
    public void onPageListGetSuccess(List<Page> pageList) {
        if (mView.isActive()) {
            mView.showPageListGetSuccess(pageList);
        }
    }

    @Override
    public void onPageListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showPageListGetFailed(e);
        }
    }
}
