package com.rumofuture.nemo.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.model.entity.Album;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class AlbumRepository implements AlbumDataSource {

    private static AlbumRepository sInstance;
    private final AlbumDataSource mAlbumLocalDataSource;
    private final AlbumDataSource mAlbumRemoteDataSource;

    public static AlbumRepository getInstance(
            @NonNull AlbumDataSource albumLocalDataSource,
            @NonNull AlbumDataSource albumRemoteDataSource
    ) {
        if (null == sInstance) {
            sInstance = new AlbumRepository(albumLocalDataSource, albumRemoteDataSource);
        }
        return sInstance;
    }

    private AlbumRepository(
            @NonNull AlbumDataSource albumLocalDataSource,
            @NonNull AlbumDataSource albumRemoteDataSource
    ) {
        mAlbumLocalDataSource = albumLocalDataSource;
        mAlbumRemoteDataSource = albumRemoteDataSource;
    }

    @Override
    public void updateAlbum(Album album, AlbumUpdateCallback callback) {
        mAlbumRemoteDataSource.updateAlbum(album, callback);
    }

    @Override
    public void getAlbumByStyle(String style, AlbumGetCallback callback) {
        mAlbumRemoteDataSource.getAlbumByStyle(style, callback);
    }

    @Override
    public void getAlbumList(AlbumListGetCallback callback) {
        mAlbumRemoteDataSource.getAlbumList(callback);
    }
}
