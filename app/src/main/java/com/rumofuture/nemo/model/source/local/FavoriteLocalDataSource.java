package com.rumofuture.nemo.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Favorite;
import com.rumofuture.nemo.model.source.FavoriteDataSource;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class FavoriteLocalDataSource implements FavoriteDataSource {

    private static FavoriteLocalDataSource sInstance;
    private final Context mContext;

    public static FavoriteLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new FavoriteLocalDataSource(context);
        }
        return sInstance;
    }

    private FavoriteLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void saveFavorite(Favorite favorite, NemoCallback<Favorite> callback) {

    }

    @Override
    public void deleteFavorite(Favorite favorite, NemoCallback<Favorite> callback) {

    }

    @Override
    public void getFavorite(Favorite favorite, NemoCallback<Favorite> callback) {

    }
}
