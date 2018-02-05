package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoAuthorBlogContract;
import com.rumofuture.nemo.model.entity.Book;
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
import cn.bmob.v3.listener.PushListener;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class NemoAuthorBlogPresenter implements NemoAuthorBlogContract.Presenter {

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
        mBookRepository.getBookListByAuthor(author, pageCode, false, new NemoCallback<List<Book>>() {
            @Override
            public void onSuccess(List<Book> data) {
                if (mView.isActive()) {
                    mView.showAuthorBookListGetSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showAuthorBookListGetFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }

    @Override
    public void followUser(Follow follow) {
        mFollowRepository.saveFollow(follow, new NemoCallback<Follow>() {
            @Override
            public void onSuccess(Follow data) {
                if (mView.isActive()) {
                    mView.showUserFollowSuccess(data);
                }

                User follower = data.getFollower();
                follower.increment(UserSchema.Table.Cols.FOLLOW);
                mUserRepository.updateUserInfo(follower, new NemoCallback<User>() {
                    @Override
                    public void onSuccess(User data) {

                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });

//        BmobPushManager pushManager = new BmobPushManager();
//        BmobQuery<BmobInstallation> query = BmobInstallation.getQuery();
//        query.addWhereEqualTo("installationId", "C8CDE03327B6A0485A153C838ADD17A5");
//        pushManager.setQuery(query);
//        pushManager.pushMessage("", new PushListener() {
//            @Override
//            public void done(BmobException e) {
//
//            }
//        });
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showUserFollowFailed(message);
                }
            }
        });
    }

    @Override
    public void unfollowUser(Follow follow) {
        mFollowRepository.deleteFollow(follow, new NemoCallback<Follow>() {
            @Override
            public void onSuccess(Follow data) {
                if (mView.isActive()) {
                    mView.showUserUnfollowSuccess(data);
                }

                User follower = BmobUser.getCurrentUser(User.class);
                follower.increment(UserSchema.Table.Cols.FOLLOW, -1);
                mUserRepository.updateUserInfo(follower, new NemoCallback<User>() {
                    @Override
                    public void onSuccess(User data) {

                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showUserUnfollowFailed(message);
                }
            }
        });
    }

    @Override
    public void getFollowRelation(Follow follow) {
        mFollowRepository.getFollow(follow, new NemoCallback<Follow>() {
            @Override
            public void onSuccess(Follow data) {
                if (mView.isActive()) {
                    mView.showFollowGetSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showFollowGetFailed(message);
                }
            }
        });
    }
}
