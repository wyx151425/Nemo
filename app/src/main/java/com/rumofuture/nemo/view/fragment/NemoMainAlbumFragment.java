package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.NemoMainAlbumContract;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.source.AlbumDataSource;
import com.rumofuture.nemo.view.adapter.NemoMainAlbumListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoMainAlbumFragment extends Fragment implements NemoMainAlbumContract.View {

    private NemoMainAlbumContract.Presenter mPresenter;

    private List<Album> mAlbumList;
    private NemoMainAlbumListAdapter mAlbumListAdapter;

    public NemoMainAlbumFragment() {

    }

    public static NemoMainAlbumFragment newInstance() {
        return new NemoMainAlbumFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumList = new ArrayList<>();
        for (int index = 0; index < AlbumDataSource.PAGE_LIMIT; index++) {
            Album album = new Album();
            album.setBookTotal(0);
            mAlbumList.add(album);
        }
        mAlbumListAdapter = new NemoMainAlbumListAdapter(mAlbumList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_main_album, container, false);

        RecyclerView albumListView = (RecyclerView) view.findViewById(R.id.album_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        albumListView.setLayoutManager(layoutManager);
        albumListView.setAdapter(mAlbumListAdapter);

        mPresenter.getAlbumList();

        return view;
    }

    @Override
    public void setPresenter(NemoMainAlbumContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAlbumListGetSuccess(List<Album> albumList) {
        mAlbumList.clear();
        for (Album album : albumList) {
            mAlbumList.add(album);
        }
        mAlbumListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumListGetFailed(BmobException e) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
