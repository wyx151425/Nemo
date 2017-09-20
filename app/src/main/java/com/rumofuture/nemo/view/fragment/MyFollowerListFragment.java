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
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.app.contract.MyFollowerListContract;
import com.rumofuture.nemo.model.source.UserDataSource;
import com.rumofuture.nemo.view.adapter.MyFollowerListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MyFollowerListFragment extends Fragment implements MyFollowerListContract.View {

    private MyFollowerListContract.Presenter mPresenter;

    private List<User> mFollowerList;
    private MyFollowerListAdapter mFollowerListAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mPageCode = 0;
    private boolean mQueryable = true;

    public MyFollowerListFragment() {

    }

    public static MyFollowerListFragment newInstance() {
        return new MyFollowerListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFollowerList = new ArrayList<>();
        mFollowerListAdapter = new MyFollowerListAdapter(mFollowerList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_follower_list, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPageCode = 0;
                mQueryable = true;
                mPresenter.getMyFollowerList(mPageCode);
            }
        });

        RecyclerView mFollowerListView = (RecyclerView) view.findViewById(R.id.follower_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFollowerListView.setLayoutManager(layoutManager);
        mFollowerListView.setAdapter(mFollowerListAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPageCode = 0;
        mQueryable = true;
        mPresenter.getMyFollowerList(mPageCode);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void setPresenter(MyFollowerListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMyFollowerListGetSuccess(List<User> followerList) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (followerList.size() < UserDataSource.PAGE_LIMIT) {
            mQueryable = false;
        }

        if (0 == followerList.size()) {
            mFollowerList.clear();
            mFollowerListAdapter.notifyDataSetChanged();
            Toast.makeText(getActivity(), "暂无粉丝", Toast.LENGTH_LONG).show();
        } else {
            mFollowerList.clear();
            for (User follower : followerList) {
                mFollowerList.add(follower);
            }
            mFollowerListAdapter.notifyDataSetChanged();
            mPageCode++;
        }
    }

    @Override
    public void showMyFollowerListGetFailed(BmobException e) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
