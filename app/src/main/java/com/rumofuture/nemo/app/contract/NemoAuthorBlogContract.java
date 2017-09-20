package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Follow;
import com.rumofuture.nemo.model.entity.User;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public interface NemoAuthorBlogContract {

    interface View extends NemoView<NemoAuthorBlogContract.Presenter> {
        void showProgressBar(boolean show);

        void showAuthorBookListGetSuccess(List<Book> bookList);
        void showAuthorBookListGetFailed(BmobException e);

        void showUserFollowSuccess(Follow follow);
        void showUserFollowFailed(BmobException e);

        void showUserUnfollowSuccess(Follow follow);
        void showUserUnfollowFailed(BmobException e);

        void showFollowGetSuccess(Follow follow);
        void showFollowGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void getAuthorBookList(User author, int pageCode);

        void followUser(Follow follow);
        void unfollowUser(Follow follow);
        void getFollowRelation(Follow follow);
    }
}
