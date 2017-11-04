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
import com.rumofuture.nemo.presenter.MyFollowerListPresenter;
import com.rumofuture.nemo.view.fragment.MyFollowerListFragment;

public class MyFollowerListActivity extends NemoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        MyFollowerListFragment fragment =
                (MyFollowerListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (null == fragment) {
            fragment = MyFollowerListFragment.newInstance();
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        MyFollowerListPresenter presenter = new MyFollowerListPresenter(
                fragment,
                DataSourceManager.provideUserRepository(MyFollowerListActivity.this)
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

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MyFollowerListActivity.class);
        context.startActivity(intent);
    }
}
