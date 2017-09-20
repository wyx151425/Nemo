package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/18.
 */

public interface NemoAuthorSearchContract {

    interface View extends NemoView<NemoAuthorSearchContract.Presenter> {
        void showProgressBar(boolean show);

        void showAuthorSearchSuccess(List<User> authorList);
        void showAuthorSearchFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void searchAuthor(String keyword);
    }
}
