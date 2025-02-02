package com.rumofuture.nemo.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
import com.rumofuture.nemo.view.activity.MyBookPageListActivity;
import com.rumofuture.nemo.view.activity.MyBookShareActivity;
import com.rumofuture.nemo.view.fragment.MyBookInfoFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class MyBookInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_BOOK = 0;
    private static final int TYPE_REVIEW = 1;

    private Context mContext;
    private MyBookInfoFragment mFragment;
    private Book mBook;
    private List<Review> mReviewList;

    public MyBookInfoAdapter(MyBookInfoFragment fragment, Book book, List<Review> reviewList) {
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_book_info, parent, false);
            final BookViewHolder holder = new BookViewHolder(view);

            holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (1 == mBook.getType()) {
                        MyBookShareActivity.actionStart(mContext, mBook);
                    } else {
                        MyBookPageListActivity.actionStart(mContext, mBook);
                    }
                }
            });

            return holder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_book_review_list, parent, false);
            final ReviewViewHolder holder = new ReviewViewHolder(view);

            holder.mReviewInfoContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("删除评论")
                            .setMessage("您确定要删除这条评论吗？")
                            .setCancelable(true)
                            .setPositiveButton(R.string.prompt_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mFragment.actionDeleteReview(mReviewList.get(holder.getAdapterPosition() - 1));
                                }
                            })
                            .setNegativeButton(R.string.prompt_cancel, null)
                            .show();
                    return false;
                }
            });

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (0 == position) {
            BookViewHolder bookViewHolder = (BookViewHolder) holder;
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
        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookIntroductionView;
        TextView mBookStyleView;
        TextView mBookFavorTotalView;

        BookViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;
            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookFavorTotalView = (TextView) itemView.findViewById(R.id.book_favor_total_view);
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
