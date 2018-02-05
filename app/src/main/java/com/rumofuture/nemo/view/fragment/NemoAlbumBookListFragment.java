package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.NemoAlbumBookListContract;
import com.rumofuture.nemo.app.widget.OnListScrollListener;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.view.adapter.NemoAlbumBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoAlbumBookListFragment extends Fragment implements NemoAlbumBookListContract.View {

    private static final String ARG_ALBUM = "com.rumofuture.nemo.view.fragment.NemoAlbumBookListFragment.album";

    private NemoAlbumBookListContract.Presenter mPresenter;

    private Album mAlbum;
    private List<Book> mBookList;
    private NemoAlbumBookListAdapter mBookListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public NemoAlbumBookListFragment() {

    }

    public static NemoAlbumBookListFragment newInstance(Album album) {
        Bundle args = new Bundle();
        NemoAlbumBookListFragment fragment = new NemoAlbumBookListFragment();
        args.putSerializable(ARG_ALBUM, album);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mAlbum = (Album) getArguments().getSerializable(ARG_ALBUM);
        }
        mBookList = new ArrayList<>();
        mBookListAdapter = new NemoAlbumBookListAdapter(mBookList);
        mScrollListener = new OnListScrollListener(BookDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getAlbumBookList(mAlbum.getStyle(), pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_swipe_refresh, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.init();
                mPresenter.getAlbumBookList(mAlbum.getStyle(), 0);
            }
        });

        RecyclerView bookListView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        bookListView.addOnScrollListener(mScrollListener);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getAlbumBookList(mAlbum.getStyle(), 0);
    }

    @Override
    public void setPresenter(NemoAlbumBookListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showAlbumBooksGetSuccess(List<Book> bookList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mBookList.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mBookList.addAll(bookList);
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumBooksGetFailed(String message) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
