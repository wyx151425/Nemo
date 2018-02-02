package com.rumofuture.nemo.presenter;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.NemoReviewEditContract;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.source.ReviewDataSource;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class NemoReviewEditPresenter implements NemoReviewEditContract.Presenter {

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
        mReviewRepository.saveReview(review, new NemoCallback<Review>() {
            @Override
            public void onSuccess(Review data) {
                if (mView.isActive()) {
                    mView.showReviewSaveSuccess(data);
                }
            }

            @Override
            public void onFailed(String message) {
                if (mView.isActive()) {
                    mView.showReviewSaveFailed(message);
                }
            }
        });
    }
}
