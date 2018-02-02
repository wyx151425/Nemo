package com.rumofuture.nemo.presenter;

import android.text.TextUtils;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoLoginContract;
import com.rumofuture.nemo.model.entity.Device;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class NemoLoginPresenter implements NemoLoginContract.Presenter {

    private NemoLoginContract.View mView;
    private UserDataSource mUserRepository;

    public NemoLoginPresenter(NemoLoginContract.View view, UserDataSource userRepository) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String mobilePhoneNumber, String password) {
        mView.showMobilePhoneNumberError(null);
        mView.showPasswordError(null);

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            if (mView.isActive()) {
                mView.showMobilePhoneNumberError(R.string.prompt_invalid_user_password);
            }
            return;
        }

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
            mUserRepository.login(user, new NemoCallback<User>() {
                @Override
                public void onSuccess(User data) {
                    updateDeviceUser(data);
                    if (mView.isActive()) {
                        mView.showProgressBar(false);
                        mView.showLoginSuccess(data);
                    }
                }

                @Override
                public void onFailed(String message) {
                    if (mView.isActive()) {
                        mView.showProgressBar(false);
                        mView.showloginfailed(message);
                    }
                }
            });
        }
    }

    private void updateDeviceUser(final User user) {
        BmobQuery<Device> query = new BmobQuery<>();
        final String id = BmobInstallationManager.getInstallationId();
        query.addWhereEqualTo("installationId", id);
        query.findObjects(new FindListener<Device>() {
            @Override
            public void done(List<Device> deviceList, BmobException e) {
                if (null == e) {
                    if (deviceList.size() > 0) {
                        Device device = deviceList.get(0);
                        device.setUser(user);
                        device.update();
                    }
                }
            }
        });
    }

    private boolean isMobilePhoneNumberValid(String mobilePhoneNumber) {
        return mobilePhoneNumber.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
}
