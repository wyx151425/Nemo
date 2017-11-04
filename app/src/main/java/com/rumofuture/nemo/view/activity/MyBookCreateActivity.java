package com.rumofuture.nemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.app.manager.DataSourceManager;
import com.rumofuture.nemo.presenter.MyBookCreatePresenter;
import com.rumofuture.nemo.view.fragment.MyBookCreateFragment;

public class MyBookCreateActivity extends NemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_create);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MyBookCreateFragment fragment =
                (MyBookCreateFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = MyBookCreateFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }

        MyBookCreatePresenter presenter = new MyBookCreatePresenter(
                fragment,
                DataSourceManager.provideUserRepository(MyBookCreateActivity.this),
                DataSourceManager.provideBookRepository(MyBookCreateActivity.this)
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
        intent.setClass(context, MyBookCreateActivity.class);
        ((NemoActivity) context).startActivityForResult(intent, requestCode);
    }
}
