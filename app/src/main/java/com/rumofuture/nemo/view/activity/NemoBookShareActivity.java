package com.rumofuture.nemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.app.manager.NemoActivityManager;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.view.fragment.NemoBookShareFragment;

/**
 * Created by WangZhenqi on 2017/10/13.
 */

public class NemoBookShareActivity extends NemoActivity {

    public static final String EXTRA_BOOK = "com.rumofuture.nemo.view.activity.NemoBookShareActivity.url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NemoBookShareFragment fragment =
                (NemoBookShareFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = NemoBookShareFragment.newInstance((Book) getIntent().getSerializableExtra(EXTRA_BOOK));
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }
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
        intent.setClass(context, NemoBookShareActivity.class);
        intent.putExtra(EXTRA_BOOK, book);
        context.startActivity(intent);
    }
}
