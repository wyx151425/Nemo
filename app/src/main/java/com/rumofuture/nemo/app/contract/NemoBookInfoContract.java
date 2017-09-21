package com.rumofuture.nemo.app.contract;

import com.rumofuture.nemo.app.NemoPresenter;
import com.rumofuture.nemo.app.NemoView;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Favorite;
import com.rumofuture.nemo.model.entity.Review;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public interface NemoBookInfoContract {

    interface View extends NemoView<NemoBookInfoContract.Presenter> {
        void showBookFavoriteSuccess(Favorite favorite);
        void showBookFavoriteFailed(BmobException e);

        void showFavoriteRemoveSuccess();
        void showFavoriteRemoveFailed(BmobException e);

        void showFavoriteGetSuccess(Favorite favorite);
        void showFavoriteGetFailed(BmobException e);

        void showBookFavorTotalUpdateSuccess(Book book);

        void showReviewListGetSuccess(List<Review> reviewList);
        void showReviewListGetFailed(BmobException e);

        boolean isActive();
    }

    interface Presenter extends NemoPresenter {
        void favoriteBook(Favorite favorite);
        void removeBookFromMyFavorite(Favorite favorite);
        void getFavoriteRelation(Favorite favorite);
        void getBookReviewList(Book book, int pageCode);
    }
}
