package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public interface MyFavoriteBookListContract {

    interface View extends NemoView<MyFavoriteBookListContract.Presenter> {
        void showMyFavoriteBookListGetSuccess(List<Book> bookList);
        void showMyFavoriteBookListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getMyFavoriteBookList(int pageCode);
    }
}
