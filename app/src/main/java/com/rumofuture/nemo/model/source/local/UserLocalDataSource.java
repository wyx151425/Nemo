package com.rumofuture.nemo.model.source.local;

import android.content.Context;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class UserLocalDataSource implements UserDataSource {

    private Context mContext;
    private static UserLocalDataSource sInstance;

    public static UserLocalDataSource getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserLocalDataSource(context);
        return sInstance;
    }

    private UserLocalDataSource(Context context) {
        mContext = context;
    }


    @Override
    public void login(User user, NemoCallback<User> callback) {

    }

    @Override
    public void register(User user, NemoCallback<User> callback) {

    }

    @Override
    public void updateUserAvatar(BmobFile newAvatar, NemoCallback<BmobFile> callback) {

    }

    @Override
    public void updateUserPortrait(BmobFile newPortrait, NemoCallback<BmobFile> callback) {

    }

    @Override
    public void updateUserInfo(User user, NemoCallback<User> callback) {

    }

    @Override
    public void getAuthorList(int pageIndex, NemoCallback<List<User>> callback) {

    }

    @Override
    public void getFollowAuthorList(User follower, int pageIndex, NemoCallback<List<User>> callback) {

    }

    @Override
    public void getFollowerList(User author, int pageIndex, NemoCallback<List<User>> callback) {

    }

    @Override
    public void getFollowAuthorTotal(User follower, NemoCallback<Integer> callback) {

    }

    @Override
    public void getFollowerTotal(User author, NemoCallback<Integer> callback) {

    }

    @Override
    public void searchAuthor(String keyword, NemoCallback<List<User>> callback) {

    }
}
