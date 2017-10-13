package com.rumofuture.nemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.app.manager.NemoActivityManager;
import com.rumofuture.nemo.view.fragment.NemoBookShareFragment;

/**
 * Created by WangZhenqi on 2017/10/13.
 */

public class NemoBookShareActivity extends NemoActivity {

    public static final String EXTRA_URL = "com.rumofuture.nemo.view.activity.NemoBookShareActivity.url";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        NemoBookShareFragment fragment =
                (NemoBookShareFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = NemoBookShareFragment.newInstance(getIntent().getStringExtra(EXTRA_URL));
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }
    }

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, NemoBookShareActivity.class);
        intent.putExtra(EXTRA_URL, url);
        context.startActivity(intent);
    }
}
