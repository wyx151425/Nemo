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
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.app.contract.MyFavoriteBookListContract;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.view.adapter.MyFavoriteBookListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyFavoriteBookListFragment extends Fragment implements MyFavoriteBookListContract.View {

    private MyFavoriteBookListContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Book> mBookList;
    private MyFavoriteBookListAdapter mBookListAdapter;

    private int mPageCode = 0;
    private boolean mQueryable = true;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_favorite_book_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageCode = 0;
                mQueryable = true;
                mPresenter.getMyFavoriteBookList(mPageCode);
            }
        });

        RecyclerView myCollectedBookListView = (RecyclerView) view.findViewById(R.id.my_collected_book_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myCollectedBookListView.setLayoutManager(layoutManager);
        myCollectedBookListView.setAdapter(mBookListAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 重新赋值一遍，避免界面销毁时数据仍保存
        mPageCode = 0;
        mQueryable = true;
        mPresenter.getMyFavoriteBookList(mPageCode);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setPresenter(MyFavoriteBookListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyFavoriteBookListGetSuccess(List<Book> bookList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (BookDataSource.PAGE_LIMIT > bookList.size()) {
            mQueryable = false;
        }

        if (0 == bookList.size()) {
            mBookList.clear();
            mBookListAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "暂无收藏", Toast.LENGTH_LONG).show();
        } else {
            mBookList.clear();
            for (Book book : bookList) {
                mBookList.add(book);
            }
            mBookListAdapter.notifyDataSetChanged();
            mPageCode++;
        }
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
