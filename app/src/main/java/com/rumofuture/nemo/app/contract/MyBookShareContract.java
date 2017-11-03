package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public interface MyBookShareContract {

    interface View extends NemoView<MyBookShareContract.Presenter> {
        void showProgressBar(boolean show);
        void showBookShareSuccess(Book book);
        void showBookShareFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void shareBook(Book book);
    }
}
