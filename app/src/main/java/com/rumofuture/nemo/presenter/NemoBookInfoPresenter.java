package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

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

public class NemoBookInfoPresenter implements NemoBookInfoContract.Presenter, UserDataSource.UserInfoUpdateCallback, BookDataSource.BookUpdateCallback,
        FavoriteDataSource.FavoriteSaveCallback, FavoriteDataSource.FavoriteDeleteCallback, FavoriteDataSource.FavoriteGetCallback, ReviewDataSource.ReviewListGetCallback {

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
        mFavoriteRepository.saveFavorite(favorite, this);
    }

    @Override
    public void removeBookFromMyFavorite(Favorite favorite) {
        mFavoriteRepository.deleteFavorite(favorite, this);
    }

    @Override
    public void getFavoriteRelation(Favorite favorite) {
        mFavoriteRepository.getFavorite(favorite, this);
    }

    @Override
    public void getBookReviewList(Book book, int pageCode) {
        mReviewRepository.getReviewListByBook(book, pageCode, this);
    }

    @Override
    public void onFavoriteSaveSuccess(Favorite favorite) {
        if (mView.isActive()) {
            mView.showBookFavoriteSuccess(favorite);
        }

        User collector = favorite.getFavor();
        collector.increment(UserSchema.Table.Cols.FAVORITE);
        mUserRepository.updateUserInfo(collector, this);

        Book book = favorite.getBook();
        book.increment(BookSchema.Table.Cols.FAVOR_TOTAL);
        mBookRepository.updateBook(book, null, this);
    }

    @Override
    public void onFavoriteSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showBookFavoriteFailed(e);
        }
    }

    @Override
    public void onFavoriteDeleteSuccess(Favorite favorite) {
        if (mView.isActive()) {
            mView.showFavoriteRemoveSuccess();
        }

        User collector = favorite.getFavor();
        collector.increment(UserSchema.Table.Cols.FAVORITE, -1);
        mUserRepository.updateUserInfo(collector, this);

        Book book = favorite.getBook();
        book.increment(BookSchema.Table.Cols.FAVOR_TOTAL, -1);
        mBookRepository.updateBook(book, null, this);
    }

    @Override
    public void onFavoriteDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFavoriteRemoveFailed(e);
        }
    }

    @Override
    public void onFavoriteGetSuccess(Favorite favorite) {
        if (mView.isActive()) {
            mView.showFavoriteGetSuccess(favorite);
        }
    }

    @Override
    public void onFavoriteGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showFavoriteGetFailed(e);
        }
    }

    @Override
    public void onUserInfoUpdateSuccess() {

    }

    @Override
    public void onUserInfoUpdateFailed(BmobException e) {

    }

    @Override
    public void onBookUpdateSuccess(Book book) {
        if (mView.isActive()) {
            mView.showBookFavorTotalUpdateSuccess(book);
        }
    }

    @Override
    public void onBookUpdateFailed(BmobException e) {

    }

    @Override
    public void onReviewListGetSuccess(List<Review> reviewList) {
        if (mView.isActive()) {
            mView.showReviewListGetSuccess(reviewList);
        }
    }

    @Override
    public void onReviewListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showReviewListGetFailed(e);
        }
    }
}
