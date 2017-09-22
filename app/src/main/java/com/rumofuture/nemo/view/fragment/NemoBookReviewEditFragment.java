package com.rumofuture.nemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.contract.NemoReviewEditContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Review;
import com.rumofuture.nemo.model.entity.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import static android.app.Activity.RESULT_OK;

public class NemoBookReviewEditFragment extends Fragment implements NemoReviewEditContract.View {

    public static final String EXTRA_REVIEW = "com.rumofuture.nemo.view.fragment.NemoBookReviewEditFragment.review";
    private static final String ARG_PARAM = "com.rumofuture.nemo.view.fragment.NemoBookReviewEditFragment.Book";

    private NemoReviewEditContract.Presenter mPresenter;

    private Book mBook;
    private Review mReview;

    public NemoBookReviewEditFragment() {

    }

    public static NemoBookReviewEditFragment newInstance(Book book) {
        Bundle args = new Bundle();
        NemoBookReviewEditFragment fragment = new NemoBookReviewEditFragment();
        args.putSerializable(ARG_PARAM, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getActivity()) {
            mBook = (Book) getArguments().getSerializable(ARG_PARAM);
        }
        mReview = new Review(mBook, BmobUser.getCurrentUser(User.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nemo_review_edit, container, false);

        final EditText reviewContentView = (EditText) view.findViewById(R.id.review_content_view);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reviewContentView.getText() == null || reviewContentView.getText().toString().equals("")) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("评论失败")
                            .setMessage("请编辑评论内容")
                            .setCancelable(true)
                            .setPositiveButton("确定", null)
                            .show();
                } else {
                    mReview.setContent(reviewContentView.getText().toString());
                    mPresenter.saveReview(mReview);
                }
            }
        });

        return view;
    }

    @Override
    public void setPresenter(NemoReviewEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean show) {

    }

    @Override
    public void showReviewSaveSuccess(Review review) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_REVIEW, review);
        getActivity().setResult(RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    public void showReviewSaveFailed(BmobException e) {
        Toast.makeText(getActivity(), "评论失败", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
