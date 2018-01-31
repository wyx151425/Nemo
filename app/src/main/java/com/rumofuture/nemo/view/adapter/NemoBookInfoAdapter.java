package com.rumofuture.nemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.view.activity.NemoAuthorBlogActivity;
import com.rumofuture.nemo.view.activity.NemoBookPageListActivity;
import com.rumofuture.nemo.view.activity.NemoBookShareActivity;
import com.rumofuture.nemo.view.fragment.NemoBookInfoFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class NemoBookInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BOOK = 0;
    private static final int TYPE_REVIEW = 1;

    private Context mContext;
    private NemoBookInfoFragment mFragment;
    private Book mBook;
    private List<Review> mReviewList;

    public NemoBookInfoAdapter(NemoBookInfoFragment fragment, Book book, List<Review> reviewList) {
        mFragment = fragment;
        mBook = book;
        mReviewList = reviewList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        if (TYPE_BOOK == viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_book_info, parent, false);
            final NemoBookInfoAdapter.BookViewHolder holder = new NemoBookInfoAdapter.BookViewHolder(view);

            holder.mAuthorInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NemoAuthorBlogActivity.actionStart(mContext, mBook.getAuthor());
                }
            });
            holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (1 == mBook.getType()) {
                        NemoBookShareActivity.actionStart(mContext, mBook);
                    } else {
                        NemoBookPageListActivity.actionStart(mContext, mBook);
                    }
                }
            });
            holder.mReviewActionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragment.actionEditReview();
                }
            });

            return holder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_book_review_list, parent, false);
            return new ReviewViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (0 == position) {
            BookViewHolder bookViewHolder = (BookViewHolder) holder;

            Glide.with(mContext).load(mBook.getAuthor().getAvatar().getUrl()).into(bookViewHolder.mAuthorAvatarView);
            bookViewHolder.mAuthorNameView.setText(mBook.getAuthor().getName());

            Glide.with(mContext).load(mBook.getCover().getUrl()).into(bookViewHolder.mBookCoverView);
            bookViewHolder.mBookNameView.setText(mBook.getName());
            bookViewHolder.mBookIntroductionView.setText(mBook.getIntroduction());
            bookViewHolder.mBookStyleView.setText(mBook.getStyle());
            bookViewHolder.mBookFavorTotalView.setText(String.valueOf(mBook.getFavor()));
        } else {
            Review review = mReviewList.get(position - 1);
            ReviewViewHolder reviewViewHolder = (ReviewViewHolder) holder;
            if (null != review.getReviewer().getAvatar()) {
                Glide.with(mContext).load(review.getReviewer().getAvatar().getUrl()).into(reviewViewHolder.mReviewerAvatarView);
            }
            reviewViewHolder.mReviewerNameView.setText(review.getReviewer().getName());
            reviewViewHolder.mReviewContentView.setText(review.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mReviewList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_BOOK;
        } else {
            return TYPE_REVIEW;
        }
    }

    private static class BookViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mBookInfoContainer;

        LinearLayout mAuthorInfoContainer;
        CircleImageView mAuthorAvatarView;
        TextView mAuthorNameView;

        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookIntroductionView;
        TextView mBookStyleView;
        TextView mBookFavorTotalView;

        TextView mReviewActionView;

        BookViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;

            mAuthorInfoContainer = (LinearLayout) itemView.findViewById(R.id.author_info_container);
            mAuthorAvatarView = (CircleImageView) itemView.findViewById(R.id.author_avatar_view);
            mAuthorNameView = (TextView) itemView.findViewById(R.id.author_name_view);

            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookFavorTotalView = (TextView) itemView.findViewById(R.id.book_favor_total_view);

            mReviewActionView = (TextView) itemView.findViewById(R.id.review_action_view);
        }
    }

    private static class ReviewViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mReviewInfoContainer;
        CircleImageView mReviewerAvatarView;
        TextView mReviewerNameView;
        TextView mReviewContentView;

        ReviewViewHolder(View itemView) {
            super(itemView);

            mReviewInfoContainer = (LinearLayout) itemView;
            mReviewerAvatarView = (CircleImageView) itemView.findViewById(R.id.reviewer_avatar_view);
            mReviewerNameView = (TextView) itemView.findViewById(R.id.reviewer_name_view);
            mReviewContentView = (TextView) itemView.findViewById(R.id.review_content_view);
        }
    }
}
