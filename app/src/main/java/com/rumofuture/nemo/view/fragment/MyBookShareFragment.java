package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.MyBookShareContract;
import com.rumofuture.nemo.model.entity.Book;

import cn.bmob.v3.exception.BmobException;

public class MyBookShareFragment extends Fragment implements MyBookShareContract.View {

    private MyBookShareContract.Presenter mPresenter;

    public MyBookShareFragment() {

    }

    public static MyBookShareFragment newInstance() {
        return new MyBookShareFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_book_share, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        return view;
    }

    @Override
    public void setPresenter(MyBookShareContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showBookShareSuccess(Book book) {

    }

    @Override
    public void showBookShareFailed(BmobException e) {
        Toast.makeText(getActivity(), R.string.prompt_save_failed, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
