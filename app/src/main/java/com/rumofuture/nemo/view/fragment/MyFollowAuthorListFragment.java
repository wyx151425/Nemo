package com.rumofuture.nemo.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.MyFollowAuthorListContract;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.view.adapter.MyFollowAuthorListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFollowAuthorListFragment extends Fragment implements MyFollowAuthorListContract.View {

    private MyFollowAuthorListContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<User> mAuthorList;
    private MyFollowAuthorListAdapter mAuthorListAdapter;

    private int mPageCode = 0;
    private boolean mQueryable = true;

    public MyFollowAuthorListFragment() {
    }

    public static MyFollowAuthorListFragment newInstance() {
        return new MyFollowAuthorListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthorList = new ArrayList<>();
        mAuthorListAdapter = new MyFollowAuthorListAdapter(mAuthorList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_follow_author_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageCode = 0;
                mQueryable = true;
                mPresenter.getMyFollowAuthorList(mPageCode);
            }
        });

        RecyclerView myFollowedUserListView = (RecyclerView) view.findViewById(R.id.my_followed_user_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myFollowedUserListView.setLayoutManager(layoutManager);
        myFollowedUserListView.setAdapter(mAuthorListAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPageCode = 0;
        mQueryable = true;
        mPresenter.getMyFollowAuthorList(mPageCode);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setPresenter(MyFollowAuthorListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showFollowUserListGetSuccess(List<User> authorList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (UserDataSource.PAGE_LIMIT > authorList.size()) {
            mQueryable = false;
        }

        if (0 == authorList.size()) {
            mAuthorList.clear();
            mAuthorListAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "暂无关注", Toast.LENGTH_LONG).show();
        } else {
            mAuthorList.clear();
            for (User author : authorList) {
                mAuthorList.add(author);
            }
            mAuthorListAdapter.notifyDataSetChanged();
            mPageCode++;
        }
    }

    @Override
    public void showFollowUserListGetFailed(BmobException e) {
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
