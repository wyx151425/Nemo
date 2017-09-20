package com.rumofuture.nemo.model.schema;

/**
 * Created by WangZhenqi on 2017/9/11.
 */

public class AlbumSchema {

    public static final class Table {
        public static final String NAME = "Album";

        public static final class Cols {
            public static final String OBJECT_ID = "objectId";
            public static final String CREATE_TIME = "createAt";
            public static final String UPDATE_TIME = "updateAt";

            public static final String IMAGE_ID = "imageId";
            public static final String STYLE = "style";
            public static final String NOTE = "note";
            public static final String BOOK_TOTAL = "bookTotal";
        }
    }
}
