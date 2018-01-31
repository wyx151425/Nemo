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

/**
 * Created by WangZhenqi on 2017/4/27.
 */

public class NemoAlbumBookListAdapter extends RecyclerView.Adapter<NemoAlbumBookListAdapter.ItemViewHolder> {

    private List<Book> mBookList;
    private Context mContext;

    public NemoAlbumBookListAdapter(List<Book> bookList) {
        mBookList = bookList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_album_book_list, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.mBookInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoBookInfoActivity.actionStart(mContext, mBookList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Book book = mBookList.get(position);

        Glide.with(mContext).load(book.getCover().getUrl()).into(holder.mBookCoverView);
        holder.mBookNameView.setText(book.getName());
        holder.mBookIntroductionView.setText(book.getIntroduction());
        holder.mBookStyleView.setText(book.getStyle());
        holder.mBookCollectorCountView.setText(String.valueOf(book.getFavor()));
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mBookInfoContainer;
        ImageView mBookCoverView;
        TextView mBookNameView;
        TextView mBookIntroductionView;
        TextView mBookStyleView;
        TextView mBookCollectorCountView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mBookInfoContainer = (LinearLayout) itemView;
            mBookCoverView = (ImageView) itemView.findViewById(R.id.book_cover_view);
            mBookNameView = (TextView) itemView.findViewById(R.id.book_name_view);
            mBookIntroductionView = (TextView) itemView.findViewById(R.id.book_introduction_view);
            mBookStyleView = (TextView) itemView.findViewById(R.id.book_style_view);
            mBookCollectorCountView = (TextView) itemView.findViewById(R.id.book_favor_total_view);
        }
    }
}
