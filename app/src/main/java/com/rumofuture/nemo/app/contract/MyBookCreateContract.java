package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.presenter.NemoImageUploadPresenter;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public interface MyBookCreateContract {

    interface View extends NemoView<MyBookCreateContract.Presenter> {
        void showBookInfoError(int stringId);
        void showBookCoverHasChosen(String imagePath);

        void showProgressBar(boolean show);

        void showBookCreateSuccess(Book book);
        void showBookCreateFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoImageUploadPresenter {
        void getAuthorization();
        void createBook(Book book);
    }
}
