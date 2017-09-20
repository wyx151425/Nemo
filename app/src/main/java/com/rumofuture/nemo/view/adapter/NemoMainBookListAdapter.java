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
import com.rumofuture.nemo.view.activity.NemoBookInfoActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class NemoMainBookListAdapter extends RecyclerView.Adapter<NemoMainBookListAdapter.ItemViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private List<Book> mBookList;

    public NemoMainBookListAdapter(List<Book> bookList) {
        mBookList = bookList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.header_nemo_main_book_list, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_book_list, parent, false);
            final ItemViewHolder holder = new ItemViewHolder(view);

            holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NemoBookInfoActivity.actionStart(mContext, mBookList.get(holder.getAdapterPosition() - 1));
                }
            });

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (0 != position) {
            Book book = mBookList.get(position - 1);

            Glide.with(mContext).load(book.getAuthor().getAvatar().getUrl()).into(holder.mAuthorAvatarView);
            holder.mAuthorNameView.setText(book.getAuthor().getName());

            Glide.with(mContext).load(book.getCover().getUrl()).into(holder.mBookCoverView);
            holder.mBookNameView.setText(book.getName());
            holder.mBookStyleView.setText(book.getStyle());
            holder.mBookIntroductionView.setText(book.getIntroduction());
            holder.mBookFavorTotalView.setText(String.valueOf(book.getFavorTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return mBookList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;

        CircleImageView mAuthorAvatarView;
        TextView mAuthorNameView;

        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookStyleView;
        TextView mBookIntroductionView;
        TextView mBookFavorTotalView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView.findViewById(R.id.book_info_container);

            mAuthorAvatarView = (CircleImageView) itemView.findViewById(R.id.author_avatar_view);
            mAuthorNameView = (TextView) itemView.findViewById(R.id.author_name_view);

            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookFavorTotalView = (TextView) itemView.findViewById(R.id.book_favor_total_view);
        }
    }
}
