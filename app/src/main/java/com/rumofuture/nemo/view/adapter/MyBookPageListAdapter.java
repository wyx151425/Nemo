package com.rumofuture.nemo.view.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.Page;
import com.rumofuture.nemo.view.fragment.MyBookPageListFragment;

import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/15.
 */

public class MyBookPageListAdapter extends RecyclerView.Adapter<MyBookPageListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<Page> mPageList;
    private MyBookPageListFragment mFragment;

    public MyBookPageListAdapter(List<Page> pageList, MyBookPageListFragment fragment) {
        mPageList = pageList;
        mFragment = fragment;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_page_list, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);

        holder.mPageImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                dialog.setTitle("选项");
                dialog.setMessage("请选择您要进行的操作");
                dialog.setCancelable(true);
                dialog.setNegativeButton(R.string.prompt_update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFragment.actionUpdatePage(mPageList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                    }
                });
                dialog.setPositiveButton(R.string.prompt_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFragment.actionDeletePage(mPageList.get(holder.getAdapterPosition()));
                    }
                });
                dialog.show();
                return false;
            }
        });

        return holder;
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
            mPageImageView = (ImageView) itemView;
        }
    }
}
