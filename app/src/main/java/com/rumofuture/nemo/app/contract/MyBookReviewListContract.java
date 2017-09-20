package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public interface MyBookReviewListContract {

    interface View extends NemoView<MyBookReviewListContract.Presenter> {
        void showProgressBar(boolean show);

        void showReviewDeleteSuccess(Review review);
        void showReviewDeleteFailed(BmobException e);

        void showReviewListGetSuccess(List<Review> reviewList);
        void showReviewListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void deleteReview(Review review);
        void getBookReviewList(Book book, int pageCode);
    }
}
