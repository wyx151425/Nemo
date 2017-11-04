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
import com.rumofuture.nemo.presenter.MyBookListPresenter;
import com.rumofuture.nemo.view.fragment.MyBookListFragment;

public class MyBookListActivity extends NemoActivity {

    private MyBookListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_book_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mFragment =
                (MyBookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (mFragment == null) {
            mFragment = MyBookListFragment.newInstance();
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(),
                    mFragment, R.id.fragment_container);
        }

        // 创建Presenter
        MyBookListPresenter presenter = new MyBookListPresenter(
                mFragment,
                DataSourceManager.provideUserRepository(MyBookListActivity.this),
                DataSourceManager.provideBookRepository(MyBookListActivity.this)
        );

        mFragment.setPresenter(presenter);
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
        intent.setClass(context, MyBookListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFragment.onActivityResult(requestCode, resultCode, data);
    }
}
