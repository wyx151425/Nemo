package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/5/8.
 */

public interface NemoPasswordUpdateContract {

    interface View extends NemoView<NemoPasswordUpdateContract.Presenter> {
        void showProgressView(boolean show);

        void showUserPasswordModifySuccess();
        void showUserPasswordModifyFailed(String message);

        void showMobilePhoneNumberError(int stringId);
        void showPasswordError(int stringId);

        void showRequestSMSCodeSuccess(Integer smsId);
        void showRequestSMSCodeFailed(String message);

        void showSMSCodeRequestTime(String time);
        void showSMSCodeRequestTimeOut(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void requestSMSCode(String mobilePhoneNumber);
        void modifyPassword(String newPassword, String smsCode);
    }
}
