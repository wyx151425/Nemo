package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public interface NemoPageListContract {

    interface View extends NemoView<NemoPageListContract.Presenter> {
        void showPageListGetSuccess(List<Page> pageList);
        void showPageListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getBookPageList(Book book, int pageCode);
    }
}
