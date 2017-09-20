package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.model.entity.Album;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public interface AlbumDataSource {

    void updateAlbum(Album album, AlbumUpdateCallback callback);
    void getAlbumByStyle(String style, AlbumGetCallback callback);

    interface AlbumUpdateCallback {
        void onAlbumUpdateSuccess(Album album);
        void onAlbumUpdateFailed(BmobException e);
    }

    interface AlbumGetCallback {
        void onAlbumGetSuccess(Album album);
        void onAlbumGetFailed(BmobException e);
    }
}
