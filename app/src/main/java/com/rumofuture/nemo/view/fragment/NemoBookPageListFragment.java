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
import com.rumofuture.nemo.app.contract.NemoPageListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;
import com.rumofuture.nemo.model.source.PageDataSource;
import com.rumofuture.nemo.view.adapter.NemoBookPageListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoBookPageListFragment extends Fragment implements NemoPageListContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.NemoBookPageListFragment.book";

    private NemoPageListContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Book mBook;

    private List<Page> mPageList;
    private NemoBookPageListAdapter mPageListAdapter;

    private int mPageCode = 0;
    private boolean mQueryable = true;

    public NemoBookPageListFragment() {

    }

    public static NemoBookPageListFragment newInstance(Book book) {
        Bundle args = new Bundle();
        NemoBookPageListFragment fragment = new NemoBookPageListFragment();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_page_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageCode = 0;
                mQueryable = true;
                mPresenter.getBookPageList(mBook, mPageCode);
            }
        });

        RecyclerView pageListView = (RecyclerView) view.findViewById(R.id.page_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        pageListView.setLayoutManager(layoutManager);

        mPageList = new ArrayList<>();
        mPageListAdapter = new NemoBookPageListAdapter(mPageList);
        pageListView.setAdapter(mPageListAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPageCode = 0;
        mQueryable = true;
        mPresenter.getBookPageList(mBook, mPageCode);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setPresenter(NemoPageListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPageListGetSuccess(List<Page> pageList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (pageList.size() < PageDataSource.PAGE_LIMIT) {
            mQueryable = false;
        }

        if (0 == pageList.size()) {
            mPageList.clear();
            mPageListAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "暂无漫画", Toast.LENGTH_LONG).show();
        } else {
            mPageList.clear();
            for (Page page : pageList) {
                mPageList.add(page);
            }
            mPageListAdapter.notifyDataSetChanged();
            mPageCode++;
        }
    }

    @Override
    public void showPageListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), R.string.prompt_load_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
