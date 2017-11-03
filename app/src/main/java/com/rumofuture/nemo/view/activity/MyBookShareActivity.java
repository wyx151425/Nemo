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
import com.rumofuture.nemo.presenter.MyBookSharePresenter;
import com.rumofuture.nemo.view.fragment.MyBookShareFragment;

/**
 * Created by WangZhenqi on 2017/11/3.
 */

public class MyBookShareActivity extends NemoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MyBookShareFragment fragment =
                (MyBookShareFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = MyBookShareFragment.newInstance();
            NemoActivityManager.addFragmentToActivity(
                    getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        MyBookSharePresenter presenter = new MyBookSharePresenter(
                fragment,
                DataSourceManager.provideUserRepository(MyBookShareActivity.this),
                DataSourceManager.provideBookRepository(MyBookShareActivity.this)
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

    public static void actionStart(Context context, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, MyBookShareActivity.class);
        ((NemoActivity) context).startActivityForResult(intent, requestCode);
    }
}
