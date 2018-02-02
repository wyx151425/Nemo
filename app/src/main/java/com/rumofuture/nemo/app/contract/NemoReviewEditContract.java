package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Review;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public interface NemoReviewEditContract {

    interface View extends NemoView<NemoReviewEditContract.Presenter> {
        void showProgressBar(boolean show);

        void showReviewSaveSuccess(Review review);
        void showReviewSaveFailed(String message);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void saveReview(Review review);
    }
}
