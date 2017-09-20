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
import com.rumofuture.nemo.presenter.MyBookReviewListPresenter;
import com.rumofuture.nemo.view.fragment.MyBookInfoFragment;

/**
 * Created by WangZhenqi on 2017/9/19.
 */

public class MyBookInfoActivity extends NemoActivity {

    private static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.MyBookInfoActivity.book";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Book book = (Book) getIntent().getSerializableExtra(EXTRA_BOOK);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(book.getName());
        }

        MyBookInfoFragment fragment = (MyBookInfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = MyBookInfoFragment.newInstance(book);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        MyBookReviewListPresenter presenter = new MyBookReviewListPresenter(
                fragment,
                DataSourceManager.provideReviewRepository(MyBookInfoActivity.this)
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
        intent.setClass(context, MyBookInfoActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }
}
