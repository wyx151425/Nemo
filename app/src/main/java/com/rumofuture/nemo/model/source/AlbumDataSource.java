package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.model.entity.Album;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public interface AlbumDataSource {

    int PAGE_LIMIT = 8;

    void updateAlbum(Album album, AlbumUpdateCallback callback);
    void getAlbumByStyle(String style, AlbumGetCallback callback);
    void getAlbumList(AlbumListGetCallback callback);

    interface AlbumUpdateCallback {
        void onAlbumUpdateSuccess(Album album);
        void onAlbumUpdateFailed(BmobException e);
    }

    interface AlbumGetCallback {
        void onAlbumGetSuccess(Album album);
        void onAlbumGetFailed(BmobException e);
    }

    interface AlbumListGetCallback {
        void onAlbumListGetSuccess(List<Album> albumList);
        void onAlbumListGetFailed(BmobException e);
    }
}
