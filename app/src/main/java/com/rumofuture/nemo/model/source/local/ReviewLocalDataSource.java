package com.rumofuture.nemo.model.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.source.ReviewDataSource;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class ReviewLocalDataSource implements ReviewDataSource {

    private static ReviewLocalDataSource sInstance;

    private final Context mContext;

    public static ReviewLocalDataSource getInstance(@NonNull Context context) {
        if (null == sInstance) {
            sInstance = new ReviewLocalDataSource(context);
        }
        return sInstance;
    }

    private ReviewLocalDataSource(@NonNull Context context) {
        mContext = context;
    }

    @Override
    public void saveReview(Review review, NemoCallback<Review> callback) {

    }

    @Override
    public void deleteReview(Review review, NemoCallback<Review> callback) {

    }

    @Override
    public void getReviewListByBook(Book book, int pageIndex, NemoCallback<List<Review>> callback) {

    }
}
