package com.rumofuture.nemo.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.model.entity.Book;

public class NemoBookShareFragment extends Fragment {

    public static final String ARG_BOOK = "com.rumofuture.nemo.view.fragment.NemoBookShareFragment.url";
    private Book mBook;
    private WebView mWebView;

    public NemoBookShareFragment() {

    }

    public static NemoBookShareFragment newInstance(Book book) {
        Bundle args = new Bundle();
        NemoBookShareFragment fragment = new NemoBookShareFragment();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mBook = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_book_share, container, false);
        mWebView = (WebView) view.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new NemoWebViewClient());
        mWebView.setSaveEnabled(false);
        mWebView.loadUrl(mBook.getUrl());
        return view;
    }

    private static class NemoWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
