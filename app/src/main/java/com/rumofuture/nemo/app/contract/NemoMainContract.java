package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public interface NemoMainContract {

    interface View extends NemoView<NemoMainContract.Presenter> {
        void showProgressBar(boolean show);

        void showBookListGetSuccess(List<Book> bookList);
        void showBookListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getBookList(int pageCode);
    }
}
