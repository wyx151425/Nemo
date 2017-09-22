package com.rumofuture.nemo.presenter;

import com.rumofuture.nemo.app.contract.NemoReviewEditContract;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.source.ReviewDataSource;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class NemoReviewEditPresenter implements NemoReviewEditContract.Presenter, ReviewDataSource.ReviewSaveCallback {

    private NemoReviewEditContract.View mView;
    private ReviewDataSource mReviewRepository;

    public NemoReviewEditPresenter(
            NemoReviewEditContract.View view,
            ReviewDataSource reviewRepository
    ) {
        mView = view;
        mReviewRepository = reviewRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void saveReview(Review review) {
        mReviewRepository.saveReview(review, this);
    }

    @Override
    public void onReviewSaveSuccess(Review review) {
        if (mView.isActive()) {
            mView.showReviewSaveSuccess(review);
        }
    }

    @Override
    public void onReviewSaveFailed(BmobException e) {
        if (mView.isActive()) {
            mView.showReviewSaveFailed(e);
        }
    }
}
