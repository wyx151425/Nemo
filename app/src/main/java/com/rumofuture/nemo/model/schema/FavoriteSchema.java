package com.rumofuture.nemo.model.schema;

/**
 * Created by WangZhenqi on 2017/4/23.
 */

public class FavoriteSchema {

    public static final class Table {
        public static final String NAME = "Favorite";

        public static final class Cols {
            public static final String OBJECT_ID = "objectId";
            public static final String CREATE_TIME = "createAt";
            public static final String UPDATE_TIME = "updateAt";

            public static final String BOOK = "book";
            public static final String FAVOR = "favor";
        }
    }
}
