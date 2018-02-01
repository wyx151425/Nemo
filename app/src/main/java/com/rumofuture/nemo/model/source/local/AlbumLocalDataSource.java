package com.rumofuture.nemo.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.source.AlbumDataSource;

import java.util.List;

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
    public void getAlbumByStyle(String style, NemoCallback<Album> callback) {

    }

    @Override
    public void getAlbumList(NemoCallback<List<Album>> callback) {

    }
}
