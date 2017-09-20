package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface ReviewDataSource {

    int PAGE_LIMIT = 32;

    void saveReview(Review review, ReviewSaveCallback callback);
    void deleteReview(Review review, ReviewDeleteCallback callback);
    void getReviewListByBook(Book book, int pageCode, ReviewListGetCallback callback);

    interface ReviewSaveCallback {
        void onReviewSaveSuccess(Review review);
        void onReviewSaveFailed(BmobException e);
    }

    interface ReviewDeleteCallback {
        void onReviewDeleteSuccess(Review review);
        void onReviewDeleteFailed(BmobException e);
    }

    interface ReviewListGetCallback {
        void onReviewListGetSuccess(List<Review> reviewList);
        void onReviewListGetFailed(BmobException e);
    }
}
