package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public interface MyBookUpdateContract {

    interface View extends NemoView<MyBookUpdateContract.Presenter> {
        void showProgressBar(boolean show);

        void showBookUpdateSuccess(Book book);
        void showBookUpdateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void updateBook(Book book);
    }
}
