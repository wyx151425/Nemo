package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyEmailBindContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public class MyEmailBindPresenter implements MyEmailBindContract.Presenter {

    private MyEmailBindContract.View mView;

    public MyEmailBindPresenter(@NonNull MyEmailBindContract.View view) {
        mView = view;
    }

    @Override
    public void start() {

    }

    @Override
    public void bindEmailRequest(final String email) {
        if (!email.contains("@")) {
            mView.showEmailError(0);
            return;
        }

        BmobUser.requestEmailVerify(email, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(null == e){
                    mView.showEmailBindRequestSuccess("请求验证邮件成功，请到" + email + "邮箱中进行激活。");
                }else{
                    mView.showEmailBindRequestFailed(e);
                }
            }
        });
    }
}
