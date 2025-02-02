package com.rumofuture.nemo.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyInfoUpdateContract;
import com.rumofuture.nemo.app.manager.ImageChooseManager;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.view.fragment.MyInfoEditFragment;
import com.smile.filechoose.api.ChosenImage;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class MyInfoUpdatePresenter implements MyInfoUpdateContract.Presenter, NemoCallback<BmobFile> {

    private static final int NO_REQUEST_CODE = 0;
    private static final int AVATAR_UPDATE_REQUEST_CODE = 1;
    private static final int PORTRAIT_UPDATE_REQUEST_CODE = 2;

    private MyInfoUpdateContract.View mView;
    private UserDataSource mUserRepository;

    private ImageChooseManager mImageChooseManager;
    private ChosenImage mAvatarImage;
    private ChosenImage mPortraitImage;

    private int requestCode = NO_REQUEST_CODE;

    public MyInfoUpdatePresenter(
            @NonNull MyInfoUpdateContract.View view,
            @NonNull UserDataSource userRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void start() {
        mImageChooseManager = new ImageChooseManager((Fragment) mView, this);
    }

    @Override
    public void updateUserAvatar() {
        start();
        requestCode = AVATAR_UPDATE_REQUEST_CODE;
        chooseImage();
    }

    @Override
    public void updateUserPortrait() {
        start();
        requestCode = PORTRAIT_UPDATE_REQUEST_CODE;
        chooseImage();
    }

    @Override
    public void updateUserInfo(User user) {
        mView.showProgressBar(true);
        mUserRepository.updateUserInfo(user, new NemoCallback<User>() {
            @Override
            public void onSuccess(User data) {
                if (mView.isActive()) {
                    mView.showUserInfoUpdateSuccess();
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showUserInfoUpdateFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }

    @Override
    public void chooseImage() {
        mImageChooseManager.chooseImage(((MyInfoEditFragment) mView).getActivity());
    }

    @Override
    public void releaseImageChooseManager() {
        start();
        if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
            mAvatarImage = null;
        } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
            mPortraitImage = null;
        }
        requestCode = NO_REQUEST_CODE;
    }

    @Override
    public void submitChoice(int requestCode, Intent data) {
        mImageChooseManager.submitChoice(requestCode, data);
    }

    @Override
    public void setChosenImage(final ChosenImage chosenImage) {
        ((MyInfoEditFragment) mView).getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
                    mAvatarImage = chosenImage;
                    if (mAvatarImage != null) {
                        mView.showProgressBar(true);
                        mUserRepository.updateUserAvatar(
                                new BmobFile(new File(mAvatarImage.getFilePathOriginal())),
                                MyInfoUpdatePresenter.this
                        );
                    }
                } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
                    mPortraitImage = chosenImage;
                    if (mPortraitImage != null) {
                        mView.showProgressBar(true);
                        mUserRepository.updateUserPortrait(
                                new BmobFile(new File(mPortraitImage.getFilePathOriginal())),
                                MyInfoUpdatePresenter.this
                        );
                    }
                }
            }
        });
    }

    @Override
    public void onSuccess(BmobFile image) {
        if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserAvatarUpdateSuccess(image);
        } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserPortraitUpdateSuccess(image);
        }
        mView.showProgressBar(false);
        releaseImageChooseManager();
    }

    @Override
    public void onFailed(String message) {
        if (AVATAR_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserAvatarUpdateFailed(message);
        } else if (PORTRAIT_UPDATE_REQUEST_CODE == requestCode) {
            mView.showUserPortraitUpdateFailed(message);
        }
        mView.showProgressBar(false);
        releaseImageChooseManager();
    }
}
