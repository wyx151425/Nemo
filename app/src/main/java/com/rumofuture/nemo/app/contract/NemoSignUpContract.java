package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/5/6.
 */

public interface NemoSignUpContract {

    interface View extends NemoView<NemoSignUpContract.Presenter> {
        void showProgressBar(boolean show);

        void showNameError(int stringId);
        void showMobilePhoneNumberError(int stringId);
        void showPasswordError(int stringId);

        void showRequestSMSCodeSuccess(Integer smsId);
        void showRequestSMSCodeFailed(BmobException e);

        void showSMSCodeRequestTime(String time);
        void showSMSCodeRequestTimeOut(String message);

        void showSignUpSuccess(User user);
        void showSignUpFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void requestSMSCode(String mobilePhoneNumber);
        void signUp(String name, String mobilePhoneNumber, String password, String smsCode);
    }
}
