package com.rumofuture.nemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.Page;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class NemoBookPageListAdapter extends RecyclerView.Adapter<NemoBookPageListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Page> mPageList;

    public NemoBookPageListAdapter(List<Page> pageList) {
        mPageList = pageList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null)
            mContext = parent.getContext();

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_book_page_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Glide.with(mContext).load(mPageList.get(position).getImage().getUrl()).into(holder.mPageImageView);
    }

    @Override
    public int getItemCount() {
        return mPageList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mPageImageView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mPageImageView = (ImageView) itemView.findViewById(R.id.page_image);
        }
    }
}
