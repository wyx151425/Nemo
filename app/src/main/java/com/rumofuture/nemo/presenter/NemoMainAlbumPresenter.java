package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoMainAlbumContract;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.source.AlbumDataSource;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class NemoMainAlbumPresenter implements NemoMainAlbumContract.Presenter {

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
        mAlbumRepository.getAlbumList(new NemoCallback<List<Album>>() {
            @Override
            public void onSuccess(List<Album> data) {
                if (mView.isActive()) {
                    mView.showAlbumListGetSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showAlbumListGetFailed(message);
                }
            }
        });
    }
}
