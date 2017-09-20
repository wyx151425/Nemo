package com.rumofuture.nemo.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.source.AlbumDataSource;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class AlbumLocalDataSource implements AlbumDataSource {

    private final Context mContext;
    private static AlbumLocalDataSource sInstance;

    public static AlbumLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new AlbumLocalDataSource(context);
        }
        return sInstance;
    }

    private AlbumLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void updateAlbum(Album album, AlbumUpdateCallback callback) {

    }

    @Override
    public void getAlbumByStyle(String style, AlbumGetCallback callback) {

    }
}
