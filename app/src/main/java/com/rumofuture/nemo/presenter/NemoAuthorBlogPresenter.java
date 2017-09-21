package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.rumofuture.nemo.app.contract.NemoAuthorBlogContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Device;
import com.rumofuture.nemo.model.entity.Follow;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.schema.UserSchema;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.FollowDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.view.fragment.NemoAuthorBlogFragment;

import java.util.List;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.PushListener;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class NemoAuthorBlogPresenter implements NemoAuthorBlogContract.Presenter, UserDataSource.UserInfoUpdateCallback, BookDataSource.BookListGetCallback,
        FollowDataSource.FollowSaveCallback, FollowDataSource.FollowDeleteCallback, FollowDataSource.FollowGetCallback {

    private NemoAuthorBlogContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;
    private FollowDataSource mFollowRepository;

    public NemoAuthorBlogPresenter(
            @NonNull NemoAuthorBlogContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull BookDataSource bookRepository,
            @NonNull FollowDataSource followRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
        mFollowRepository = followRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthorBookList(User author, int pageCode) {
        mView.showProgressBar(true);
        mBookRepository.getBookListByAuthor(author, pageCode, false, this);
    }

    @Override
    public void followUser(Follow follow) {
        mFollowRepository.saveFollow(follow, this);
    }

    @Override
    public void unfollowUser(Follow follow) {
        mFollowRepository.deleteFollow(follow, this);
    }

    @Override
    public void getFollowRelation(Follow follow) {
        mFollowRepository.getFollow(follow, this);
    }

    @Override
    public void onBookListGetSuccess(List<Book> bookList) {
        if (mView.isActive()) {
            mView.showAuthorBookListGetSuccess(bookList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onBookListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showAuthorBookListGetFailed(e);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }

    @Override
    public void onFollowSaveSuccess(Follow follow) {
        if (mView.isActive()) {
            mView.showUserFollowSuccess(follow);
        }

        User follower = follow.getFollower();
        follower.increment(UserSchema.Table.Cols.FOLLOW_TOTAL);
        mUserRepository.updateUserInfo(follower, this);

        BmobPushManager pushManager = new BmobPushManager();
        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
        query.addWhereEqualTo("installationId", "C8CDE03327B6A0485A153C838ADD17A5");
        pushManager.setQuery(query);
        pushManager.pushMessage("", new PushListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    Toast.makeText(((NemoAuthorBlogFragment) mView).getActivity(), "推送成功", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onFollowSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showUserFollowFailed(e);
        }
    }

    @Override
    public void onFollowDeleteSuccess(Follow follow) {
        if (mView.isActive()) {
            mView.showUserUnfollowSuccess(follow);
        }

        User follower = BmobUser.getCurrentUser(User.class);
        follower.increment(UserSchema.Table.Cols.FOLLOW_TOTAL, -1);
        mUserRepository.updateUserInfo(follower, this);
    }

    @Override
    public void onFollowDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showUserUnfollowFailed(e);
        }
    }

    @Override
    public void onFollowGetSuccess(Follow follow) {
        if (mView.isActive()) {
            mView.showFollowGetSuccess(follow);
        }
    }

    @Override
    public void onFollowGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFollowGetFailed(e);
        }
    }
}
