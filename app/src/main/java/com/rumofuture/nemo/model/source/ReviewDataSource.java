package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface ReviewDataSource {

    int PAGE_LIMIT = 32;

    void saveReview(Review review, NemoCallback<Review> callback);
    void deleteReview(Review review, NemoCallback<Review> callback);
    void getReviewListByBook(Book book, int pageIndex, NemoCallback<List<Review>> callback);
}
