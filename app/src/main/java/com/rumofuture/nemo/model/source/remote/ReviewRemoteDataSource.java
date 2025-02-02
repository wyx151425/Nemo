package com.rumofuture.nemo.model.source.remote;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.schema.ReviewSchema;
import com.rumofuture.nemo.model.source.ReviewDataSource;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WangZhenqi on 2017/9/12.
 */

public class ReviewRemoteDataSource implements ReviewDataSource {

    private static final int PAGE_LIMIT = 32;

    public static final ReviewRemoteDataSource sInstance = new ReviewRemoteDataSource();

    public static ReviewRemoteDataSource getInstance() {
        return sInstance;
    }

    private ReviewRemoteDataSource() {

    }

    @Override
    public void saveReview(final Review review, final NemoCallback<Review> callback) {
        review.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (null == e) {
                    review.setObjectId(objectId);
                    callback.onSuccess(review);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteReview(final Review review, final NemoCallback<Review> callback) {
        review.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onSuccess(review);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getReviewListByBook(Book book, int pageIndex, final NemoCallback<List<Review>> callback) {
        BmobQuery<Review> query = new BmobQuery<>();
        query.addWhereEqualTo(ReviewSchema.Table.Cols.BOOK, book);
        query.include(ReviewSchema.Table.Cols.REVIEWER);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.findObjects(new FindListener<Review>() {
            @Override
            public void done(List<Review> reviewList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(reviewList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }
}
