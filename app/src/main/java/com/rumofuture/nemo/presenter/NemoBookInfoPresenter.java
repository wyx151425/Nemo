package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoBookInfoContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Favorite;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.schema.BookSchema;
import com.rumofuture.nemo.model.schema.UserSchema;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.FavoriteDataSource;
import com.rumofuture.nemo.model.source.ReviewDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class NemoBookInfoPresenter implements NemoBookInfoContract.Presenter {

    private NemoBookInfoContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;
    private ReviewDataSource mReviewRepository;
    private FavoriteDataSource mFavoriteRepository;

    public NemoBookInfoPresenter(
            @NonNull NemoBookInfoContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull BookDataSource bookRepository,
            @NonNull ReviewDataSource reviewRepository,
            @NonNull FavoriteDataSource favoriteRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
        mReviewRepository = reviewRepository;
        mFavoriteRepository = favoriteRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void favoriteBook(Favorite favorite) {
        mFavoriteRepository.saveFavorite(favorite, new NemoCallback<Favorite>() {
            @Override
            public void onSuccess(Favorite data) {
                if (mView.isActive()) {
                    mView.showBookFavoriteSuccess(data);
                }

                User collector = data.getFavor();
                collector.increment(UserSchema.Table.Cols.FAVORITE);
                mUserRepository.updateUserInfo(collector, new NemoCallback<User>() {
                    @Override
                    public void onSuccess(User data) {

                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });

                Book book = data.getBook();
                book.increment(BookSchema.Table.Cols.FAVOR_TOTAL);
                mBookRepository.updateBook(book, null, new NemoCallback<Book>() {
                    @Override
                    public void onSuccess(Book data) {

                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showBookFavoriteFailed(message);
                }
            }
        });
    }

    @Override
    public void removeBookFromMyFavorite(Favorite favorite) {
        mFavoriteRepository.deleteFavorite(favorite, new NemoCallback<Favorite>() {
            @Override
            public void onSuccess(Favorite data) {
                if (mView.isActive()) {
                    mView.showFavoriteRemoveSuccess();
                }

                User collector = data.getFavor();
                collector.increment(UserSchema.Table.Cols.FAVORITE, -1);
                mUserRepository.updateUserInfo(collector, new NemoCallback<User>() {
                    @Override
                    public void onSuccess(User data) {

                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });

                Book book = data.getBook();
                book.increment(BookSchema.Table.Cols.FAVOR_TOTAL, -1);
                mBookRepository.updateBook(book, null, new NemoCallback<Book>() {
                    @Override
                    public void onSuccess(Book data) {
                        if (mView.isActive()) {
                            mView.showBookFavorTotalUpdateSuccess(data);
                        }
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                });
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showFavoriteRemoveFailed(message);
                }
            }
        });
    }

    @Override
    public void getFavoriteRelation(Favorite favorite) {
        mFavoriteRepository.getFavorite(favorite, new NemoCallback<Favorite>() {
            @Override
            public void onSuccess(Favorite data) {
                if (mView.isActive()) {
                    mView.showFavoriteGetSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showFavoriteGetFailed(message);
                }
            }
        });
    }

    @Override
    public void getBookReviewList(Book book, int pageIndex) {
        mReviewRepository.getReviewListByBook(book, pageIndex, new NemoCallback<List<Review>>() {
            @Override
            public void onSuccess(List<Review> data) {
                if (mView.isActive()) {
                    mView.showReviewListGetSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showReviewListGetFailed(message);
                }
            }
        });
    }
}
