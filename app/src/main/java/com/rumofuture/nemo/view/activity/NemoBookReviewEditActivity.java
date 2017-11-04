package com.rumofuture.nemo.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.app.manager.DataSourceManager;
import com.rumofuture.nemo.app.manager.NemoActivityManager;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.presenter.NemoReviewEditPresenter;
import com.rumofuture.nemo.view.fragment.NemoBookReviewEditFragment;

public class NemoBookReviewEditActivity extends NemoActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.NemoBookReviewEditActivity.book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_review_edit);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (null != getSupportActionBar()) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(book.getName());
        }

        NemoBookReviewEditFragment fragment =
                (NemoBookReviewEditFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (null == fragment) {
            fragment = NemoBookReviewEditFragment.newInstance(book);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        NemoReviewEditPresenter presenter = new NemoReviewEditPresenter(
                fragment,
                DataSourceManager.provideReviewRepository(NemoBookReviewEditActivity.this)
        );
        fragment.setPresenter(presenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actionStart(Fragment fragment, Book book, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(fragment.getActivity(), NemoBookReviewEditActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        fragment.startActivityForResult(intent, requestCode);
    }
}
