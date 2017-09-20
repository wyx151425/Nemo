package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.view.adapter.NemoMainAlbumListAdapter;

public class NemoMainAlbumFragment extends Fragment {

    public NemoMainAlbumFragment() {

    }

    public static NemoMainAlbumFragment newInstance() {
        return new NemoMainAlbumFragment();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_album, container, false);

        RecyclerView albumListView = (RecyclerView) view.findViewById(R.id.album_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumListView.setLayoutManager(layoutManager);

        NemoMainAlbumListAdapter adapter = new NemoMainAlbumListAdapter();
        albumListView.setAdapter(adapter);

        return view;
    }
}
