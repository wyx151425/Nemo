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
import com.rumofuture.nemo.app.contract.MyFavoriteBookListContract;
import com.rumofuture.nemo.app.widget.OnListScrollListener;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.view.adapter.MyFavoriteBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyFavoriteBookListFragment extends Fragment implements MyFavoriteBookListContract.View {

    private MyFavoriteBookListContract.Presenter mPresenter;

    private List<Book> mBookList;
    private MyFavoriteBookListAdapter mBookListAdapter;

    private OnListScrollListener mScrollListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public MyFavoriteBookListFragment() {

    }

    public static MyFavoriteBookListFragment newInstance() {
        return new MyFavoriteBookListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBookList = new ArrayList<>();
        mBookListAdapter = new MyFavoriteBookListAdapter(mBookList);
        mScrollListener = new OnListScrollListener(BookDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getMyFavoriteBookList(pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_swipe_refresh, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getMyFavoriteBookList(0);
            }
        });

        RecyclerView bookListView = (RecyclerView) view.findViewById(R.id.recycler_view);
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
        // 重新赋值一遍，避免界面销毁时数据仍保存
        mScrollListener.init();
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.getMyFavoriteBookList(0);
    }

    @Override
    public void setPresenter(MyFavoriteBookListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyFavoriteBookListGetSuccess(List<Book> bookList) {
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
    public void showMyFavoriteBookListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), "获取失败" + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
