package com.rumofuture.nemo.presenter;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoPageListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;
import com.rumofuture.nemo.model.source.PageDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class NemoBookPageListPresenter implements NemoPageListContract.Presenter {

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
        mPageRepository.getPageListByBook(book, pageCode, new NemoCallback<List<Page>>() {
            @Override
            public void onSuccess(List<Page> data) {
                if (mView.isActive()) {
                    mView.showPageListGetSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showPageListGetFailed(message);
                }
            }
        });
    }
}
