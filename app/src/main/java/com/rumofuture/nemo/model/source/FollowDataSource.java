package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Follow;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface FollowDataSource {
    void saveFollow(Follow follow, NemoCallback<Follow> callback);
    void deleteFollow(Follow follow, NemoCallback<Follow> callback);
    void getFollow(Follow follow, NemoCallback<Follow> callback);
}
