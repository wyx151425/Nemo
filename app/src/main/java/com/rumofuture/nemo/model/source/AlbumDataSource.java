package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public interface AlbumDataSource {

    int PAGE_LIMIT = 8;

    void getAlbumByStyle(String style, NemoCallback<Album> callback);
    void getAlbumList(NemoCallback<List<Album>> callback);
}
