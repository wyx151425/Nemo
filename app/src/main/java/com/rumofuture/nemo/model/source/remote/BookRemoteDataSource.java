package com.rumofuture.nemo.model.source.remote;

import android.support.annotation.Nullable;

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
import cn.bmob.v3.datatype.BmobSmsState;
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
    public void saveBook(final Book book, final BookSaveCallback callback) {
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
                                callback.onBookSaveSuccess(book);
                            } else {
                                callback.onBookSaveFailed(e);
                                cover.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    callback.onBookSaveFailed(e);
                }
            }
        });
    }

    @Override
    public void deleteBook(final Book book, final BookDeleteCallback callback) {
        final BmobFile cover = book.getCover();
        book.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onBookDeleteSuccess(book);
                    cover.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                } else {
                    callback.onBookDeleteFailed(e);
                }
            }
        });
    }

    @Override
    public void updateBook(final Book book, @Nullable final BmobFile newCover, final BookUpdateCallback callback) {
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
                        callback.onBookUpdateFailed(e);
                    }
                }
            });
        }

        book.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onBookUpdateSuccess(book);
                } else {
                    callback.onBookUpdateFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListWithAuthor(int pageCode, final BookListGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(BookSchema.Table.Cols.SHOW, true);
        query.include(BookSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListByAuthor(User author, int pageCode, boolean self, final BookListGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.AUTHOR, author);
        if (!self) {
            query.addWhereEqualTo(BookSchema.Table.Cols.APPROVE, true);
            query.addWhereEqualTo(BookSchema.Table.Cols.SHOW, true);
        }
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getBookListByStyle(String style, int pageCode, final BookListGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.STYLE, style);
        query.addWhereEqualTo(BookSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(BookSchema.Table.Cols.SHOW, true);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getFavoriteBookList(User favor, int pageCode, final BookListGetCallback callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favor);
        query.include(FavoriteSchema.Table.Cols.BOOK);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageCode * PAGE_LIMIT);
        query.findObjects(new FindListener<Favorite>() {
            @Override
            public void done(List<Favorite> favoriteList, BmobException e) {
                if (null == e) {
                    List<Book> bookList = new ArrayList<>();
                    for (Favorite favorite : favoriteList) {
                        bookList.add(favorite.getBook());
                    }
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getAuthorBookTotal(User author, final TotalGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.AUTHOR, author);
        query.count(Book.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getAlbumBookTotal(Album album, final TotalGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.STYLE, album.getStyle());
        query.count(Book.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }

    @Override
    public void getFavoriteBookTotal(User favor, final TotalGetCallback callback) {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo(FavoriteSchema.Table.Cols.FAVOR, favor);
        query.count(Favorite.class, new CountListener() {
            @Override
            public void done(Integer total, BmobException e) {
                if (null == e) {
                    callback.onTotalGetSuccess(total);
                } else {
                    callback.onTotalGetFailed(e);
                }
            }
        });
    }

    @Override
    public void searchBook(String keyword, final BookListGetCallback callback) {
        BmobQuery<Book> query = new BmobQuery<>();
        query.addWhereEqualTo(BookSchema.Table.Cols.NAME, keyword);
        query.addWhereEqualTo(BookSchema.Table.Cols.APPROVE, true);
        query.addWhereEqualTo(BookSchema.Table.Cols.SHOW, true);
        query.include(BookSchema.Table.Cols.AUTHOR);
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (null == e) {
                    callback.onBookListGetSuccess(bookList);
                } else {
                    callback.onBookListGetFailed(e);
                }
            }
        });
    }
}
