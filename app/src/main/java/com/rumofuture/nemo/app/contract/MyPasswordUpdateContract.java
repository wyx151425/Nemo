package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/5/6.
 */

public interface MyPasswordUpdateContract {

    interface View extends NemoView<MyPasswordUpdateContract.Presenter> {
        void showUserPasswordUpdateSuccess();
        void showUserPasswordUpdateFailed(String message);

        void showPasswordError(int stringId);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void updatePassword(String oldPassword, String newPassword);
    }
}
