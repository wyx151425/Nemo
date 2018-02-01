package com.rumofuture.nemo.model.source.remote;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.schema.AlbumSchema;
import com.rumofuture.nemo.model.source.AlbumDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class AlbumRemoteDataSource implements AlbumDataSource {

    private static final AlbumRemoteDataSource sInstance = new AlbumRemoteDataSource();

    public static AlbumRemoteDataSource getInstance() {
        return sInstance;
    }

    private AlbumRemoteDataSource() {

    }

    @Override
    public void getAlbumByStyle(String style, final NemoCallback<Album> callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.addWhereEqualTo(AlbumSchema.Table.Cols.STYLE, style);
        query.findObjects(new FindListener<Album>() {
            @Override
            public void done(List<Album> albumList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(albumList.get(0));
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getAlbumList(final NemoCallback<List<Album>> callback) {
        BmobQuery<Album> query = new BmobQuery<>();
        query.setLimit(PAGE_LIMIT);
        query.order(AlbumSchema.Table.Cols.NUMBER);
        query.findObjects(new FindListener<Album>() {
            @Override
            public void done(List<Album> albumList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(albumList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }
}
