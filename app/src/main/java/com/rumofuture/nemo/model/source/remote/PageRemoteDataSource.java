package com.rumofuture.nemo.model.source.remote;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.Page;
import com.rumofuture.nemo.model.schema.PageSchema;
import com.rumofuture.nemo.model.source.PageDataSource;

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
 * Created by WangZhenqi on 2017/4/15.
 */

public class PageRemoteDataSource implements PageDataSource {

    private static final int PAGE_LIMIT = 64;

    private static final PageRemoteDataSource sInstance = new PageRemoteDataSource();

    public static PageRemoteDataSource getInstance() {
        return sInstance;
    }

    private PageRemoteDataSource() {

    }

    @Override
    public void savePage(final Page page, final NemoCallback<Page> callback) {
        final BmobFile image = page.getImage();
        image.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    page.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (null == e) {
                                page.setObjectId(objectId);
                                callback.onSuccess(page);
                            } else {
                                callback.onFailed(e.getMessage());
                                image.delete(new UpdateListener() {
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
    public void deletePage(final Page page, final NemoCallback<Page> callback) {
        final BmobFile image = page.getImage();
        page.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onSuccess(page);
                    image.delete(new UpdateListener() {
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
    public void updatePage(final Page page, final BmobFile newImage, final NemoCallback<Page> callback) {
        final BmobFile oldImage = page.getImage();
        newImage.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    page.setImage(newImage);
                    page.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                callback.onSuccess(page);
                                oldImage.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {

                                    }
                                });
                            } else {
                                callback.onFailed(e.getMessage());
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
    public void getPageListByBook(Book book, int pageIndex, final NemoCallback<List<Page>> callBack) {
        BmobQuery<Page> query = new BmobQuery<>();
        query.addWhereEqualTo(PageSchema.Table.Cols.BOOK, book);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.order(PageSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Page>() {
            @Override
            public void done(List<Page> pageList, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(pageList);
                } else {
                    callBack.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getPageTotalNumber(Book book, final NemoCallback<Integer> callback) {
        BmobQuery<Page> query = new BmobQuery<>();
        query.addWhereEqualTo(PageSchema.Table.Cols.BOOK, book);
        query.count(Page.class, new CountListener() {
            @Override
            public void done(Integer totalNumber, BmobException e) {
                if (null == e) {
                    callback.onSuccess(totalNumber);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }
}
