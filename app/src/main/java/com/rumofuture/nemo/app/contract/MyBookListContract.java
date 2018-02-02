package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public interface MyBookListContract {

    interface View extends NemoView<MyBookListContract.Presenter> {
        void showProgressBar(boolean show);

        void showBookListGetSuccess(List<Book> bookList);
        void showBookListGetFailed(String message);

        void showBookDeleteSuccess(Book book);
        void showBookDeleteFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void deleteBook(Book book);
        void updateMyBookTotalOnCreate();
        void updateMyBookTotalOnDelete();
        void getMyBookList(int pageIndex);
    }
}
