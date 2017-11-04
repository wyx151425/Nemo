package com.rumofuture.nemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.app.manager.DataSourceManager;
import com.rumofuture.nemo.app.manager.NemoActivityManager;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.presenter.NemoBookInfoPresenter;
import com.rumofuture.nemo.view.fragment.NemoAuthorBookInfoFragment;

/**
 * Created by WangZhenqi on 2017/9/20.
 */

public class NemoAuthorBookInfoActivity extends NemoActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.NemoAuthorBookInfoActivity.BookObject";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_book_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(book.getName());
        }

        NemoAuthorBookInfoFragment fragment =
                (NemoAuthorBookInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = NemoAuthorBookInfoFragment.newInstance(book);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        NemoBookInfoPresenter presenter = new NemoBookInfoPresenter(
                fragment,
                DataSourceManager.provideUserRepository(NemoAuthorBookInfoActivity.this),
                DataSourceManager.provideBookRepository(NemoAuthorBookInfoActivity.this),
                DataSourceManager.provideReviewRepository(NemoAuthorBookInfoActivity.this),
                DataSourceManager.provideFavoriteRepository(NemoAuthorBookInfoActivity.this)
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

    public static void actionStart(Context context, Book book) {
        Intent intent = new Intent();
        intent.setClass(context, NemoAuthorBookInfoActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }
}
