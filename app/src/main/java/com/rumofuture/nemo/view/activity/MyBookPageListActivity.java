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
import com.rumofuture.nemo.presenter.MyBookPageListPresenter;
import com.rumofuture.nemo.view.fragment.MyBookPageListFragment;

public class MyBookPageListActivity extends NemoActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.wzq.nemo.view.activity.MyBookPageListActivity.book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_page_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(book.getName());
        }

        MyBookPageListFragment fragment = (MyBookPageListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = MyBookPageListFragment.newInstance(book);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(),
                    fragment, R.id.fragment_container);
        }

        // Create the presenter
        MyBookPageListPresenter presenter = new MyBookPageListPresenter(
                fragment,
                DataSourceManager.provideBookRepository(MyBookPageListActivity.this),
                DataSourceManager.providePageRepository(MyBookPageListActivity.this)
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
        intent.setClass(context, MyBookPageListActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }
}
