package com.rumofuture.nemo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.rumofuture.nemo.R;
import com.rumofuture.nemo.app.NemoActivity;
import com.rumofuture.nemo.app.manager.DataSourceManager;
import com.rumofuture.nemo.app.manager.NemoActivityManager;
import com.rumofuture.nemo.presenter.NemoAlbumBookListPresenter;
import com.rumofuture.nemo.view.fragment.NemoAlbumBookListFragment;

public class NemoAlbumBookListActivity extends NemoActivity {

    private static final String EXTRA_STYLE = "com.rumofuture.nemo.view.activity.NemoAlbumBookListActivity.style";
    private static final String EXTRA_IMAGE_ID = "com.rumofuture.nemo.view.activity.NemoAlbumBookListActivity.imageId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_album_book_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String style = intent.getStringExtra(EXTRA_STYLE);
        Integer imageId = intent.getIntExtra(EXTRA_IMAGE_ID, 0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(style);
        }

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));

        ImageView albumCoverView = (ImageView) findViewById(R.id.album_cover_view);
        albumCoverView.setImageResource(imageId);

        NemoAlbumBookListFragment fragment =
                (NemoAlbumBookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = NemoAlbumBookListFragment.newInstance(style);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        NemoAlbumBookListPresenter presenter = new NemoAlbumBookListPresenter(
                fragment,
                DataSourceManager.provideBookRepository(NemoAlbumBookListActivity.this)
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

    public static void actionStart(Context context, String style, Integer imageId) {
        Intent intent = new Intent();
        intent.setClass(context, NemoAlbumBookListActivity.class);
        intent.putExtra(EXTRA_STYLE, style);
        intent.putExtra(EXTRA_IMAGE_ID, imageId);
        context.startActivity(intent);
    }
}
