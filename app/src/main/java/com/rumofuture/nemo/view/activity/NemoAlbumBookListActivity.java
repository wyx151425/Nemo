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
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.presenter.NemoAlbumBookListPresenter;
import com.rumofuture.nemo.view.fragment.NemoAlbumBookListFragment;

public class NemoAlbumBookListActivity extends NemoActivity {

    private static final String EXTRA_ALBUM = "com.rumofuture.nemo.view.activity.NemoAlbumBookListActivity.album";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nemo_album_book_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Album album = (Album) getIntent().getSerializableExtra(EXTRA_ALBUM);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(album.getStyle());
        }

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));

        ImageView albumCoverView = (ImageView) findViewById(R.id.album_cover_view);
        albumCoverView.setImageResource(album.getImageId());

        NemoAlbumBookListFragment fragment =
                (NemoAlbumBookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (null == fragment) {
            fragment = NemoAlbumBookListFragment.newInstance(album);
            NemoActivityManager.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fragment_container);
        }

        NemoAlbumBookListPresenter presenter = new NemoAlbumBookListPresenter(
                fragment,
                DataSourceManager.provideBookRepository(NemoAlbumBookListActivity.this),
                DataSourceManager.provideAlbumRepository(NemoAlbumBookListActivity.this)
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

    public static void actionStart(Context context, Album album) {
        Intent intent = new Intent();
        intent.setClass(context, NemoAlbumBookListActivity.class);
        intent.putExtra(EXTRA_ALBUM, album);
        context.startActivity(intent);
    }
}
