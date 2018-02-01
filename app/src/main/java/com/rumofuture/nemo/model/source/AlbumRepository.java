package com.rumofuture.nemo.model.source;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;

import java.util.List;

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
    public void getAlbumByStyle(String style, NemoCallback<Album> callback) {
        mAlbumRemoteDataSource.getAlbumByStyle(style, callback);
    }

    @Override
    public void getAlbumList(NemoCallback<List<Album>> callback) {
        mAlbumRemoteDataSource.getAlbumList(callback);
    }
}
