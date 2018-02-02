package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Album;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public interface NemoMainAlbumContract {

    interface View extends NemoView<NemoMainAlbumContract.Presenter> {
        void showAlbumListGetSuccess(List<Album> albumList);
        void showAlbumListGetFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAlbumList();
    }
}
