package com.rumofuture.nemo.presenter;

import android.text.TextUtils;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.app.contract.NemoLogInContract;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class NemoLogInPresenter implements NemoLogInContract.Presenter, UserDataSource.UserLogInCallback {

    private NemoLogInContract.View mView;
    private UserDataSource mUserRepository;

    public NemoLogInPresenter(NemoLogInContract.View view, UserDataSource userRepository) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void logIn(String mobilePhoneNumber, String password) {

        mView.showMobilePhoneNumberError(null);
        mView.showPasswordError(null);

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            if (mView.isActive()) {
                mView.showMobilePhoneNumberError(R.string.prompt_invalid_user_password);
            }
            return;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mobilePhoneNumber)) {
            if (mView.isActive()) {
                mView.showMobilePhoneNumberError(R.string.prompt_field_required);
            }
            return;
        } else if (!isMobilePhoneNumberValid(mobilePhoneNumber)) {
            if (mView.isActive()) {
                mView.showMobilePhoneNumberError(R.string.prompt_invalid_user_mobile_phone_number);
            }
            return;
        }

        if (mView.isActive()) {
            mView.showProgressBar(true);
            User user = new User();
            user.setUsername(mobilePhoneNumber);
            user.setPassword(password);
            mUserRepository.logIn(user, this);
        }
    }

    private boolean isMobilePhoneNumberValid(String mobilePhoneNumber) {
        return mobilePhoneNumber.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    @Override
    public void onUserLogInSuccess(User user) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showLogInSuccess(user);
        }
    }

    @Override
    public void onUserLogInFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showProgressBar(false);
            mView.showLogInFailed(e);
        }
    }
}
