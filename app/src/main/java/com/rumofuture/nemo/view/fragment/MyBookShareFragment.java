package com.rumofuture.nemo.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumofuture.nemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookShareFragment extends Fragment {


    public MyBookShareFragment() {
        // Required empty public constructor
    }

    public static MyBookShareFragment newInstance() {
        return new MyBookShareFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_book_share, container, false);
    }
}
