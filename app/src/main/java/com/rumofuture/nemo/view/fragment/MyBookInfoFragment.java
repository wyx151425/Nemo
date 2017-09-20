package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.MyBookReviewListContract;
import com.rumofuture.nemo.app.widget.OnListScrollListener;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.source.ReviewDataSource;
import com.rumofuture.nemo.view.adapter.MyBookReviewListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class MyBookInfoFragment extends Fragment implements MyBookReviewListContract.View {

    private static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.MyBookInfoFragment.book";

    private MyBookReviewListContract.Presenter mPresenter;

    private Book mBook;
    private List<Review> mReviewList;
    private MyBookReviewListAdapter mReviewListAdapter;
    private OnListScrollListener mScrollListener;

    public MyBookInfoFragment() {

    }

    public static MyBookInfoFragment newInstance(Book book) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        MyBookInfoFragment fragment = new MyBookInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
        mReviewList = new ArrayList<>();
        mReviewListAdapter = new MyBookReviewListAdapter(this, mBook, mReviewList);
        mScrollListener = new OnListScrollListener(ReviewDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getBookReviewList(mBook, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        RecyclerView reviewListView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        reviewListView.setLayoutManager(layoutManager);
        reviewListView.setAdapter(mReviewListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        reviewListView.addOnScrollListener(mScrollListener);

        mPresenter.getBookReviewList(mBook, 0);

        return view;
    }

    @Override
    public void setPresenter(MyBookReviewListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showReviewDeleteSuccess(Review review) {
        mReviewList.remove(review);
        mReviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviewDeleteFailed(BmobException e) {
        Toast.makeText(getActivity(), "删除评论失败" + e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showReviewListGetSuccess(List<Review> reviewList) {
        for (Review review : reviewList) {
            mReviewList.add(review);
        }
        mReviewListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showReviewListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), "获取评论失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void actionDeleteReview(Review review) {
        mPresenter.deleteReview(review);
    }
}
