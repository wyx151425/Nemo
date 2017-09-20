package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/15.
 */

public interface NemoMainDiscoverContract {

    interface View extends NemoView<NemoMainDiscoverContract.Presenter> {
        void showProgressBar(boolean show);

        void showAuthorListGetSuccess(List<User> authorList);
        void showAuthorListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAuthorList(int pageCode);
    }
}
