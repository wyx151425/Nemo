package com.rumofuture.nemo.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Follow;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class FollowRepository implements FollowDataSource {

    private static FollowRepository sInstance;
    private final FollowDataSource mFollowLocalDataSource;
    private final FollowDataSource mFollowRemoteDataSource;

    public static FollowRepository getInstance(
            @NonNull FollowDataSource followLocalDataSource,
            @NonNull FollowDataSource followRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new FollowRepository(followLocalDataSource, followRemoteDataSource);
        }
        return sInstance;
    }

    private FollowRepository(
            @NonNull FollowDataSource followLocalDataSource,
            @NonNull FollowDataSource followRemoteDataSource
    ) {
        mFollowLocalDataSource = followLocalDataSource;
        mFollowRemoteDataSource = followRemoteDataSource;
    }

    @Override
    public void saveFollow(Follow follow, NemoCallback<Follow> callback) {
        mFollowRemoteDataSource.saveFollow(follow, callback);
    }

    @Override
    public void deleteFollow(Follow follow, NemoCallback<Follow> callback) {
        mFollowRemoteDataSource.deleteFollow(follow, callback);
    }

    @Override
    public void getFollow(Follow follow, NemoCallback<Follow> callback) {
        mFollowRemoteDataSource.getFollow(follow, callback);
    }
}
