package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface MyEmailBindContract {

    interface View extends NemoView<MyEmailBindContract.Presenter> {
        void showProgressBar(boolean show);

        void showEmailError(Integer stringId);

        void showEmailBindSuccess(String prompt);
        void showEmailBindFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void bindEmail(String email);
    }
}
