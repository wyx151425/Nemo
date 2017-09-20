package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public interface NemoLogInContract {

    interface View extends NemoView<NemoLogInContract.Presenter> {
        void showProgressBar(boolean show);

        void showMobilePhoneNumberError(Integer stringId);
        void showPasswordError(Integer stringId);

        void showLogInSuccess(User user);
        void showLogInFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void logIn(String mobilePhoneNumber, String password);
    }
}
