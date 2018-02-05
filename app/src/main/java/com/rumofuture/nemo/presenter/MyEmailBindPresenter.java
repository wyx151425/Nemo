package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyEmailBindContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public class MyEmailBindPresenter implements MyEmailBindContract.Presenter {

    private MyEmailBindContract.View mView;
    private UserDataSource mUserRepository;

    private String mEmail;

    public MyEmailBindPresenter(
            @NonNull MyEmailBindContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void bindEmail(final String email) {
        if (!email.contains("@")) {
            mView.showEmailError(R.string.prompt_email_format_error);
            return;
        }

        mEmail = email;
        User user = BmobUser.getCurrentUser(User.class);
        user.setEmail(email);
        mView.showProgressBar(true);
        mUserRepository.updateUserInfo(user, new NemoCallback<User>() {
            @Override
            public void onSuccess(User data) {
                BmobUser.requestEmailVerify(mEmail, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (mView.isActive()) {
                            mView.showProgressBar(false);
                            if (null == e) {
                                mView.showEmailBindSuccess("验证邮件已发送，请到" + mEmail + "邮箱中进行激活。");
                            } else {
                                mView.showEmailBindFailed(e.getMessage());
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showProgressBar(false);
                    mView.showEmailBindFailed(message);
                }
            }
        });
    }
}
