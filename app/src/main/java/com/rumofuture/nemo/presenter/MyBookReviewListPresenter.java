package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.contract.MyBookReviewListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.source.ReviewDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class MyBookReviewListPresenter implements MyBookReviewListContract.Presenter, ReviewDataSource.ReviewDeleteCallback, ReviewDataSource.ReviewListGetCallback {

    private MyBookReviewListContract.View mView;
    private ReviewDataSource mReviewRepository;

    public MyBookReviewListPresenter(
            @NonNull MyBookReviewListContract.View view,
            @NonNull ReviewDataSource reviewRepository
    ) {
        mView = view;
        mReviewRepository = reviewRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void deleteReview(Review review) {
        mView.showProgressBar(true);
        mReviewRepository.deleteReview(review, this);
    }

    @Override
    public void getBookReviewList(Book book, int pageCode) {
        mView.showProgressBar(true);
        mReviewRepository.getReviewListByBook(book, pageCode, this);
    }

    @Override
    public void onReviewDeleteSuccess(Review review) {
        if (mView.isActive()) {
            mView.showReviewDeleteSuccess(review);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onReviewDeleteFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showReviewDeleteFailed(e);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onReviewListGetSuccess(List<Review> reviewList) {
        if (mView.isActive()) {
            mView.showReviewListGetSuccess(reviewList);
            mView.showProgressBar(false);
        }
    }

    @Override
    public void onReviewListGetFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showReviewListGetFailed(e);
            mView.showProgressBar(false);
        }
    }
}
