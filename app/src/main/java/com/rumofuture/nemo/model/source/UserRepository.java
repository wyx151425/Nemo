package com.rumofuture.nemo.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class UserRepository implements UserDataSource {

    private static UserRepository sInstance;
    private final UserDataSource mUserLocalDataSource;
    private final UserDataSource mUserRemoteDataSource;

    public static UserRepository getInstance(
            @NonNull UserDataSource userLocalDataSource,
            @NonNull UserDataSource userRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new UserRepository(userLocalDataSource, userRemoteDataSource);
        }
        return sInstance;
    }

    private UserRepository(
            @NonNull UserDataSource userLocalDataSource,
            @NonNull UserDataSource userRemoteDataSource
    ) {
        mUserLocalDataSource = userLocalDataSource;
        mUserRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public void login(User user, NemoCallback<User> callback) {
        mUserRemoteDataSource.login(user, callback);
    }

    @Override
    public void register(User user, NemoCallback<User> callback) {
        mUserRemoteDataSource.register(user, callback);
    }

    @Override
    public void updateUserAvatar(BmobFile newAvatar, NemoCallback<BmobFile> callback) {
        mUserRemoteDataSource.updateUserAvatar(newAvatar, callback);
    }

    @Override
    public void updateUserPortrait(BmobFile newPortrait, NemoCallback<BmobFile> callback) {
        mUserRemoteDataSource.updateUserPortrait(newPortrait, callback);
    }

    @Override
    public void updateUserInfo(User user, NemoCallback<User> callback) {
        mUserRemoteDataSource.updateUserInfo(user, callback);
    }

    @Override
    public void getAuthorList(int pageIndex, NemoCallback<List<User>> callback) {
        mUserRemoteDataSource.getAuthorList(pageIndex, callback);
    }

    @Override
    public void getFollowAuthorList(User follower, int pageIndex, NemoCallback<List<User>> callback) {
        mUserRemoteDataSource.getFollowAuthorList(follower, pageIndex, callback);
    }

    @Override
    public void getFollowerList(User author, int pageIndex, NemoCallback<List<User>> callback) {
        mUserRemoteDataSource.getFollowerList(author, pageIndex, callback);
    }

    @Override
    public void getFollowAuthorTotal(User follower, NemoCallback<Integer> callback) {
        mUserRemoteDataSource.getFollowAuthorTotal(follower, callback);
    }

    @Override
    public void getFollowerTotal(User author, NemoCallback<Integer> callback) {
        mUserRemoteDataSource.getFollowerTotal(author, callback);
    }

    @Override
    public void searchAuthor(String keyword, NemoCallback<List<User>> callback) {
        mUserRemoteDataSource.searchAuthor(keyword, callback);
    }
}
