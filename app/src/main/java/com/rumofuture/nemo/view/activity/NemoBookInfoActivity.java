package com.rumofuture.nemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.presenter.NemoBookInfoPresenter;
import com.rumofuture.nemo.app.manager.NemoActivityManager;
import com.rumofuture.nemo.app.manager.DataSourceManager;
import com.rumofuture.nemo.view.fragment.NemoBookInfoFragment;

public class NemoBookInfoActivity extends NemoActivity {

    private static final String EXTRA_BOOK_OBJECT = "com.rumofuture.nemo.view.activity.NemoBookInfoActivity.BookObject";

    private Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_book_info);

        mBook = (Book) getIntent().getSerializableExtra(EXTRA_BOOK_OBJECT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(mBook.getName());
        }

        NemoBookInfoFragment fragment =
                (NemoBookInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = NemoBookInfoFragment.newInstance(mBook);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        NemoBookInfoPresenter presenter = new NemoBookInfoPresenter(
                fragment,
                DataSourceManager.provideUserRepository(NemoBookInfoActivity.this),
                DataSourceManager.provideBookRepository(NemoBookInfoActivity.this),
                DataSourceManager.provideReviewRepository(NemoBookInfoActivity.this),
                DataSourceManager.provideFavoriteRepository(NemoBookInfoActivity.this)
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
        intent.setClass(context, NemoBookInfoActivity.class);
        intent.putExtra(EXTRA_BOOK_OBJECT, book);
        context.startActivity(intent);
    }
}
