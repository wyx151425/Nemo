package com.rumofuture.nemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.view.activity.NemoAuthorBlogActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class NemoAuthorSearchListAdapter extends RecyclerView.Adapter<NemoAuthorSearchListAdapter.ItemViewHolder> {

    private Context mContext;
    private List<User> mAuthorList;

    public NemoAuthorSearchListAdapter(List<User> authorList) {
        mAuthorList = authorList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mContext) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_author_list, parent, false);
        final NemoAuthorSearchListAdapter.ItemViewHolder holder = new NemoAuthorSearchListAdapter.ItemViewHolder(view);

        holder.mAuthorInfoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoAuthorBlogActivity.actionStart(mContext, mAuthorList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        User author = mAuthorList.get(position);
        Glide.with(mContext).load(author.getAvatar().getUrl()).into(holder.mAuthorAvatarView);
        holder.mAuthorNameView.setText(author.getName());
        holder.mAuthorMottoView.setText(author.getMotto());
        holder.mAuthorBookTotalView.setText(String.valueOf(author.getBookTotal()));
        holder.mAuthorFollowerTotalView.setText(String.valueOf(author.getFollowerTotal()));
    }

    @Override
    public int getItemCount() {
        return mAuthorList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mAuthorInfoContainer;
        CircleImageView mAuthorAvatarView;
        TextView mAuthorNameView;
        TextView mAuthorMottoView;
        TextView mAuthorBookTotalView;
        TextView mAuthorFollowerTotalView;

        ItemViewHolder(View itemView) {
            super(itemView);

            mAuthorInfoContainer = (LinearLayout) itemView;
            mAuthorAvatarView = (CircleImageView) itemView.findViewById(R.id.author_avatar_view);
            mAuthorNameView = (TextView) itemView.findViewById(R.id.author_name_view);
            mAuthorMottoView = (TextView) itemView.findViewById(R.id.author_motto_view);
            mAuthorBookTotalView = (TextView) itemView.findViewById(R.id.author_book_total_view);
            mAuthorFollowerTotalView = (TextView) itemView.findViewById(R.id.author_follower_total_view);
        }
    }
}
