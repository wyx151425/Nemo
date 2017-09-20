package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.NemoMainAlbumContract;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.source.AlbumDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class NemoMainAlbumPresenter implements NemoMainAlbumContract.Presenter, AlbumDataSource.AlbumListGetCallback {

    private NemoMainAlbumContract.View mView;
    private AlbumDataSource mAlbumRepository;

    public NemoMainAlbumPresenter(
            @NonNull NemoMainAlbumContract.View view,
            @NonNull AlbumDataSource albumRepository
    ) {
        mView = view;
        mAlbumRepository = albumRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAlbumList() {
        mAlbumRepository.getAlbumList(this);
    }

    @Override
    public void onAlbumListGetSuccess(List<Album> albumList) {
        if (mView.isActive()) {
            mView.showAlbumListGetSuccess(albumList);
        }
    }

    @Override
    public void onAlbumListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAlbumListGetFailed(e);
        }
    }
}
