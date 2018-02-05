package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public interface NemoLoginContract {

    interface View extends NemoView<NemoLoginContract.Presenter> {
        void showProgressBar(boolean show);

        void showMobilePhoneNumberError(Integer stringId);
        void showPasswordError(Integer stringId);

        void showLoginSuccess(User user);
        void showLoginFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void login(String mobilePhoneNumber, String password);
    }
}
