package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.app.contract.NemoBookSearchContract;
import com.rumofuture.nemo.view.adapter.NemoBookSearchListAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class NemoBookSearchFragment extends Fragment implements NemoBookSearchContract.View {

    private NemoBookSearchContract.Presenter mPresenter;
    private NemoProgressBarFragment mProgressBar;

    private List<Book> mBookList;
    private NemoBookSearchListAdapter mBookListAdapter;

    public NemoBookSearchFragment() {

    }

    public static NemoBookSearchFragment newInstance() {
        return new NemoBookSearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mBookList = new ArrayList<>();
        mBookListAdapter = new NemoBookSearchListAdapter(mBookList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mProgressBar = NemoProgressBarFragment.newInstance(getResources().getString(R.string.prompt_loading));

        View view = inflater.inflate(R.layout.fragment_nemo_recycler_view, container, false);

        RecyclerView bookListView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookListView.setLayoutManager(layoutManager);
        bookListView.setAdapter(mBookListAdapter);

        return view;
    }

    @Override
    public void setPresenter(NemoBookSearchContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            mProgressBar.show(getFragmentManager(), null);
        } else {
            mProgressBar.dismiss();
        }
    }

    @Override
    public void showBookSearchSuccess(List<Book> bookList) {
        mBookList.clear();
        for (Book book : bookList) {
            mBookList.add(book);
        }
        mBookListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBookSearchFailed(BmobException e) {
        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_nemo_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                mPresenter.searchBook(keyword);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                return false;
            }
        });
    }
}
