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
 * Created by WangZhenqi on 2017/9/19.
 */

public class NemoBookSearchListAdapter extends RecyclerView.Adapter<NemoBookSearchListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Book> mBookList;

    public NemoBookSearchListAdapter(List<Book> bookList) {
        mBookList = bookList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_book_list, parent, false);
        final NemoBookSearchListAdapter.ItemViewHolder holder = new NemoBookSearchListAdapter.ItemViewHolder(view);

        holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoBookInfoActivity.actionStart(mContext, mBookList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(NemoBookSearchListAdapter.ItemViewHolder holder, int position) {
        Book book = mBookList.get(position);

        Glide.with(mContext).load(book.getAuthor().getAvatar().getUrl()).into(holder.mAuthorAvatarView);
        holder.mAuthorNameView.setText(book.getAuthor().getName());

        Glide.with(mContext).load(book.getCover().getUrl()).into(holder.mBookCoverView);
        holder.mBookNameView.setText(book.getName());
        holder.mBookStyleView.setText(book.getStyle());
        holder.mBookIntroductionView.setText(book.getIntroduction());
        holder.mBookFavorTotalView.setText(String.valueOf(book.getFavor()));
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
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
