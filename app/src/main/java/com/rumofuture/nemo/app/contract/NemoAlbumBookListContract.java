package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public interface NemoAlbumBookListContract {

    interface View extends NemoView<NemoAlbumBookListContract.Presenter> {
        void showAlbumBooksGetSuccess(List<Book> books);
        void showAlbumBooksGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAlbumBookList(String style, int pageCode);
    }
}
