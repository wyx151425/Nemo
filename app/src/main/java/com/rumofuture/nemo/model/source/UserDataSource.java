package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

public interface UserDataSource {

    int PAGE_LIMIT = 32;

    void login(User user, NemoCallback<User> callback);
    void register(User user, NemoCallback<User> callback);

    void updateUserAvatar(BmobFile newAvatar, NemoCallback<BmobFile> callback);
    void updateUserPortrait(BmobFile newPortrait, NemoCallback<BmobFile> callback);
    void updateUserInfo(User user, NemoCallback<User> callback);

    void getAuthorList(int pageIndex, NemoCallback<List<User>> callback);
    void getFollowAuthorList(User follower, int pageIndex, NemoCallback<List<User>> callback);
    void getFollowerList(User author, int pageIndex, NemoCallback<List<User>> callback);

    void getFollowAuthorTotal(User follower, NemoCallback<Integer> callback);
    void getFollowerTotal(User author, NemoCallback<Integer> callback);

    void searchAuthor(String keyword, NemoCallback<List<User>> callback);
}
