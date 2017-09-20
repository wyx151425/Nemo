package com.rumofuture.nemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.view.activity.NemoAlbumBookListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class NemoMainAlbumListAdapter extends RecyclerView.Adapter<NemoMainAlbumListAdapter.ItemViewHolder> {

    private List<Album> mAlbumList;
    private Context mContext;

    public NemoMainAlbumListAdapter() {
        mAlbumList = new ArrayList<>();
        mAlbumList.add(new Album(R.drawable.classicism, "古典", "Classical Style"));
        mAlbumList.add(new Album(R.drawable.purefresh, "清新", "Pure & Fresh Style"));
        mAlbumList.add(new Album(R.drawable.burningblood, "热血", "Burning Blood Style"));
        mAlbumList.add(new Album(R.drawable.artistic, "文艺", "Artistic Style"));
        mAlbumList.add(new Album(R.drawable.lovely, "宠萌", "Lovely Style"));
        mAlbumList.add(new Album(R.drawable.humor, "幽默", "Humor Style"));
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_nemo_main_album_list, parent, false);
        final ItemViewHolder holder = new ItemViewHolder(view);
        holder.mAlbumGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NemoAlbumBookListActivity.actionStart(
                        mContext,
                        mAlbumList.get(holder.getAdapterPosition()).getStyle(),
                        mAlbumList.get(holder.getAdapterPosition()).getImageId()
                );
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mAlbumCoverView.setImageResource(mAlbumList.get(position).getImageId());
        holder.mAlbumStyleView.setText(mAlbumList.get(position).getStyle());
        holder.mAlbumNoteView.setText(mAlbumList.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mAlbumCoverView;
        TextView mAlbumNoteView;
        TextView mAlbumStyleView;
        Button mAlbumGoButton;

        ItemViewHolder(View itemView) {
            super(itemView);

            mAlbumCoverView = (ImageView) itemView.findViewById(R.id.album_cover_view);
            mAlbumStyleView = (TextView) itemView.findViewById(R.id.album_style_view);
            mAlbumNoteView = (TextView) itemView.findViewById(R.id.album_note_view);
            mAlbumGoButton = (Button) itemView.findViewById(R.id.album_go_button);
        }
    }
}
