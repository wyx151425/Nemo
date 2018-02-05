package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyBookReviewListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.source.ReviewDataSource;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class MyBookReviewListPresenter implements MyBookReviewListContract.Presenter {

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
        mReviewRepository.deleteReview(review, new NemoCallback<Review>() {
            @Override
            public void onSuccess(Review data) {
                if (mView.isActive()) {
                    mView.showReviewDeleteSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showReviewDeleteFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }

    @Override
    public void getBookReviewList(Book book, int pageCode) {
        mView.showProgressBar(true);
        mReviewRepository.getReviewListByBook(book, pageCode, new NemoCallback<List<Review>>() {
            @Override
            public void onSuccess(List<Review> data) {
                if (mView.isActive()) {
                    mView.showReviewListGetSuccess(data);
                    mView.showProgressBar(false);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showReviewListGetFailed(message);
                    mView.showProgressBar(false);
                }
            }
        });
    }
}
