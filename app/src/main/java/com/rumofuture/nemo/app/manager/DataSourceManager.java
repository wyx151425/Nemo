package com.rumofuture.nemo.app.manager;

import android.content.Context;

import com.rumofuture.nemo.model.source.AlbumRepository;
import com.rumofuture.nemo.model.source.BookRepository;
import com.rumofuture.nemo.model.source.FavoriteRepository;
import com.rumofuture.nemo.model.source.FollowRepository;
import com.rumofuture.nemo.model.source.PageRepository;
import com.rumofuture.nemo.model.source.ReviewRepository;
import com.rumofuture.nemo.model.source.UserRepository;
import com.rumofuture.nemo.model.source.local.AlbumLocalDataSource;
import com.rumofuture.nemo.model.source.local.BookLocalDataSource;
import com.rumofuture.nemo.model.source.local.FavoriteLocalDataSource;
import com.rumofuture.nemo.model.source.local.FollowLocalDataSource;
import com.rumofuture.nemo.model.source.local.PageLocalDataSource;
import com.rumofuture.nemo.model.source.local.ReviewLocalDataSource;
import com.rumofuture.nemo.model.source.local.UserLocalDataSource;
import com.rumofuture.nemo.model.source.remote.AlbumRemoteDataSource;
import com.rumofuture.nemo.model.source.remote.BookRemoteDataSource;
import com.rumofuture.nemo.model.source.remote.FavoriteRemoteDataSource;
import com.rumofuture.nemo.model.source.remote.FollowRemoteDataSource;
import com.rumofuture.nemo.model.source.remote.PageRemoteDataSource;
import com.rumofuture.nemo.model.source.remote.ReviewRemoteDataSource;
import com.rumofuture.nemo.model.source.remote.UserRemoteDataSource;

/**
 * Created by WangZhenqi on 2017/1/20.
 */

public class DataSourceManager {

    public static UserRepository provideUserRepository(Context context) {
        return UserRepository.getInstance(
                UserLocalDataSource.getInstance(context),
                UserRemoteDataSource.getInstance()
        );
    }

    public static AlbumRepository provideAlbumRepository(Context context) {
        return AlbumRepository.getInstance(
                AlbumLocalDataSource.getInstance(context),
                AlbumRemoteDataSource.getInstance()
        );
    }

    public static BookRepository provideBookRepository(Context context) {
        return BookRepository.getInstance(
                BookLocalDataSource.getInstance(context),
                BookRemoteDataSource.getInstance()
        );
    }

    public static FavoriteRepository provideFavoriteRepository(Context context) {
        return FavoriteRepository.getInstance(
                FavoriteLocalDataSource.getInstance(context),
                FavoriteRemoteDataSource.getInstance()
        );
    }

    public static FollowRepository provideFollowRepository(Context context) {
        return FollowRepository.getInstance(
                FollowLocalDataSource.getInstance(context),
                FollowRemoteDataSource.getInstance()
        );
    }

    public static PageRepository providePageRepository(Context context) {
        return PageRepository.getInstance(
                PageLocalDataSource.getInstance(context),
                PageRemoteDataSource.getInstance()
        );
    }

    public static ReviewRepository provideReviewRepository(Context context) {
        return ReviewRepository.getInstance(
                ReviewLocalDataSource.getInstance(context),
                ReviewRemoteDataSource.getInstance()
        );
    }
}
