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
import com.rumofuture.nemo.app.widget.OnListScrollListener;
import com.rumofuture.nemo.app.contract.NemoAlbumBookListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.view.adapter.NemoAlbumBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoAlbumBookListFragment extends Fragment implements NemoAlbumBookListContract.View {

    private static final String ARG_STYLE = "com.rumofuture.nemo.view.fragment.NemoAlbumBookListFragment.style";

    private NemoAlbumBookListContract.Presenter mPresenter;

    private String mStyle;
    private List<Book> mBookList;
    private NemoAlbumBookListAdapter mBookListAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private OnListScrollListener mScrollListener;

    public NemoAlbumBookListFragment() {

    }

    public static NemoAlbumBookListFragment newInstance(String style) {
        Bundle args = new Bundle();
        NemoAlbumBookListFragment fragment = new NemoAlbumBookListFragment();
        args.putSerializable(ARG_STYLE, style);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mStyle = (String) getArguments().getSerializable(ARG_STYLE);
        }
        mBookList = new ArrayList<>();
        mBookListAdapter = new NemoAlbumBookListAdapter(mBookList);
        mScrollListener = new OnListScrollListener(BookDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getAlbumBookList(mStyle, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_album_book_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mScrollListener.init();
                mPresenter.getAlbumBookList(mStyle, 0);
            }
        });

        RecyclerView bookListView = (RecyclerView) view.findViewById(R.id.book_list_view);
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
        mSwipeRefreshLayout.setRefreshing(true);
        mScrollListener.init();
        mPresenter.getAlbumBookList(mStyle, 0);
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
        for (Book book : bookList) {
            mBookList.add(book);
        }
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAlbumBooksGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
