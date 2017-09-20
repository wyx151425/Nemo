package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.widget.OnListScrollListener;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Follow;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.app.contract.NemoAuthorBlogContract;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.view.adapter.NemoAuthorBlogListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class NemoAuthorBlogFragment extends Fragment implements NemoAuthorBlogContract.View {

    private static final String ARG_AUTHOR = "com.rumofuture.nemo.view.fragment.NemoAuthorBlogFragment.author";

    private NemoAuthorBlogContract.Presenter mPresenter;

    private User mTargetAuthor;

    private Follow mFollow;

    private boolean isOnline = false;
    private boolean isFollow = false;

    private List<Book> mBookList;
    private NemoAuthorBlogListAdapter mBookListAdapter;

    private FloatingActionButton mFab;

    private OnListScrollListener mScrollListener;

    public NemoAuthorBlogFragment() {
    }

    public static NemoAuthorBlogFragment newInstance(User author) {
        Bundle args = new Bundle();
        NemoAuthorBlogFragment fragment = new NemoAuthorBlogFragment();
        args.putSerializable(ARG_AUTHOR, author);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mTargetAuthor = (User) getArguments().getSerializable(ARG_AUTHOR);
        }

        if (null != BmobUser.getCurrentUser(User.class)) {
            mFollow = new Follow(mTargetAuthor, BmobUser.getCurrentUser(User.class));
            isOnline = true;
        } else {
            isOnline = false;
        }

        mBookList = new ArrayList<>();
        mBookListAdapter = new NemoAuthorBlogListAdapter(mTargetAuthor, mBookList);
        mScrollListener = new OnListScrollListener(BookDataSource.PAGE_LIMIT) {
            @Override
            public void onLoadMore(int pageCode) {
                mPresenter.getAuthorBookList(mTargetAuthor, pageCode);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_author_blog, container, false);

        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFab.setClickable(false);
                if (isOnline) {
                    if (isFollow) {
                        mPresenter.unfollowUser(mFollow);
                    } else {
                        mPresenter.followUser(mFollow);
                    }
                } else {
                    Toast.makeText(getActivity(), "登录Nemo即可关注此作者", Toast.LENGTH_LONG).show();
                }
            }
        });

        RecyclerView bookListView = (RecyclerView) view.findViewById(R.id.book_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        mScrollListener.setLayoutManager(layoutManager);
        bookListView.addOnScrollListener(mScrollListener);

        if (isOnline && mTargetAuthor.getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
            mFab.setImageResource(R.mipmap.ic_favorite_red_fab);
            mFab.setClickable(false);
        } else if (isOnline) {
            mPresenter.getFollowRelation(mFollow);
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getAuthorBookList(mTargetAuthor, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        mBookList.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isOnline = false;
        isFollow = false;
    }

    @Override
    public void setPresenter(NemoAuthorBlogContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showAuthorBookListGetSuccess(List<Book> bookList) {
        for (Book book : bookList) {
            mBookList.add(book);
        }
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAuthorBookListGetFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserFollowSuccess(Follow follow) {
        mFollow = follow;
        isFollow = true;
        mFab.setImageResource(R.mipmap.ic_favorite_red_fab);
        mFab.setClickable(true);
        Toast.makeText(getActivity(), "关注成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserFollowFailed(BmobException e) {
        mFab.setClickable(true);
        Toast.makeText(getActivity(), "关注失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserUnfollowSuccess(Follow follow) {
        follow.setObjectId(null);
        mFollow = follow;
        isFollow = false;
        mFab.setImageResource(R.mipmap.ic_favorite_silver_fab);
        mFab.setClickable(true);
        Toast.makeText(getActivity(), "取消关注", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserUnfollowFailed(BmobException e) {
        mFab.setClickable(true);
        Toast.makeText(getActivity(), "取消关注失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFollowGetSuccess(Follow follow) {
        mFollow.setObjectId(follow.getObjectId());
        isFollow = true;
        mFab.setImageResource(R.mipmap.ic_favorite_red_fab);
        mFab.setClickable(true);
    }

    @Override
    public void showFollowGetFailed(BmobException e) {
        mFab.setClickable(true);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
