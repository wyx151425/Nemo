package com.rumofuture.nemo.model.source;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Favorite;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public interface FavoriteDataSource {
    void saveFavorite(Favorite favorite, NemoCallback<Favorite> callback);
    void deleteFavorite(Favorite favorite, NemoCallback<Favorite> callback);
    void getFavorite(Favorite favorite, NemoCallback<Favorite> callback);
}
