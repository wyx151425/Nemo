package com.rumofuture.nemo.model.source.remote;

import android.support.annotation.Nullable;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Album;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Favorite;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.schema.BookSchema;
import com.rumofuture.nemo.model.schema.FavoriteSchema;
import com.rumofuture.nemo.model.source.BookDataSource;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by WangZhenqi on 2017/4/13.
 */

public class BookRemoteDataSource implements BookDataSource {

    private static final BookRemoteDataSource sInstance = new BookRemoteDataSource();

    public static BookRemoteDataSource getInstance() {
        return sInstance;
    }

    private BookRemoteDataSource() {

    }

    @Override
    public void saveBook(final Book book, final NemoCallback<Book> callback) {
        final BmobFile cover = book.getCover();
        // 用于上传封面图片的方法
        cover.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    // 图片上传成功后，封面对象自动获取Bmob云中地址
                    // 将图片赋值给漫画册对象并进行上传即可
                    book.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (null == e) {
                                book.setObjectId(objectId);
                                callback.onSuccess(book);
                            } else {
                                callback.onFailed(e.getMessage());
                                cover.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void deleteBook(final Book book, final NemoCallback<Book> callback) {
        final BmobFile cover = book.getCover();
        book.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onSuccess(book);
                    cover.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void updateBook(final Book book, @Nullable final BmobFile newCover, final NemoCallback<Book> callback) {
        if (null != newCover) {
            newCover.upload(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (null == e) {
                        BmobFile oldCover = book.getCover();
                        oldCover.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {

                            }
                        });
                        book.setCover(newCover);
                    } else {
                        callback.onFailed(e.getMessage());
                    }
                }
            });
        }

        book.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onSuccess(book);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getBookListWithAuthor(int pageIndex, final NemoCallback<List<Book>> callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.STATUS, 3);
        query.addWhereEqualTo(BookSchema.Table.Cols.PUBLISH, true);
        query.include(BookSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(bookList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getBookListByAuthor(User author, int pageIndex, boolean own, final NemoCallback<List<Book>> callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.AUTHOR, author);
        if (!own) {
            query.addWhereEqualTo(BookSchema.Table.Cols.STATUS, 3);
            query.addWhereEqualTo(BookSchema.Table.Cols.PUBLISH, true);
        }
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(bookList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getBookListByStyle(String style, int pageIndex, final NemoCallback<List<Book>> callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.STYLE, style);
        query.addWhereEqualTo(BookSchema.Table.Cols.STATUS, 3);
        query.addWhereEqualTo(BookSchema.Table.Cols.PUBLISH, true);
        query.include(BookSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(bookList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getFavoriteBookList(User favor, int pageIndex, final NemoCallback<List<Book>> callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favor);
        query.include(FavoriteSchema.Table.Cols.BOOK + "." + BookSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.order(FavoriteSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Favorite>() {
            @Override
            public void done(List<Favorite> favoriteList, BmobException e) {
                if (null == e) {
                    List<Book> bookList = new ArrayList<>();
                    for (Favorite favorite : favoriteList) {
                        bookList.add(favorite.getBook());
                    }
                    callback.onSuccess(bookList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getAuthorBookTotalNumber(User author, boolean own, final NemoCallback<Integer> callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.AUTHOR, author);
        if (!own) {
            query.addWhereEqualTo(BookSchema.Table.Cols.STATUS, 3);
            query.addWhereEqualTo(BookSchema.Table.Cols.PUBLISH, true);
        }
        query.count(Book.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onSuccess(total);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getAlbumBookTotalNumber(Album album, final NemoCallback<Integer> callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.STYLE, album.getStyle());
        query.addWhereEqualTo(BookSchema.Table.Cols.STATUS, 3);
        query.addWhereEqualTo(BookSchema.Table.Cols.PUBLISH, true);
        query.count(Book.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onSuccess(total);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getFavoriteBookTotalNumber(User favor, final NemoCallback<Integer> callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favor);
        query.count(Favorite.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onSuccess(total);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void searchBook(String keyword, final NemoCallback<List<Book>> callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.NAME, keyword);
        query.addWhereEqualTo(BookSchema.Table.Cols.STATUS, 3);
        query.addWhereEqualTo(BookSchema.Table.Cols.PUBLISH, true);
        query.include(BookSchema.Table.Cols.AUTHOR);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(bookList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }
}
