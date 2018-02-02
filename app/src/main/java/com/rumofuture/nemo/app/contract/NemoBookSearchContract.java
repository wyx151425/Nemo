package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/18.
 */

public interface NemoBookSearchContract {

    interface View extends NemoView<NemoBookSearchContract.Presenter> {
        void showProgressBar(boolean show);

        void showBookSearchSuccess(List<Book> bookList);
        void showBookSearchFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void searchBook(String keyword);
    }
}
