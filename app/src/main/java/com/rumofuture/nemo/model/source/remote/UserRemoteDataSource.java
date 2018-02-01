package com.rumofuture.nemo.model.source.remote;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.model.entity.Follow;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.schema.FollowSchema;
import com.rumofuture.nemo.model.schema.UserSchema;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by WangZhenqi on 2017/4/16.
 */

public class UserRemoteDataSource implements UserDataSource {

    private static final UserRemoteDataSource sInstance = new UserRemoteDataSource();

    public static UserRemoteDataSource getInstance() {
        return sInstance;
    }

    private UserRemoteDataSource() {

    }

    @Override
    public void login(User user, final NemoCallback<User> callback) {
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void register(User user, final NemoCallback<User> callback) {
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (null == e) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void updateUserAvatar(final BmobFile newAvatar, final NemoCallback<BmobFile> callback) {
        final User currentUser = BmobUser.getCurrentUser(User.class);

        final User targetUser = new User();
        targetUser.setObjectId(currentUser.getObjectId());

        final BmobFile oldAvatar = currentUser.getAvatar();

        newAvatar.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    targetUser.setAvatar(newAvatar);
                    targetUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                callback.onSuccess(newAvatar);
                                currentUser.setAvatar(newAvatar);
                                if (null != oldAvatar) {
                                    oldAvatar.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {

                                        }
                                    });
                                }
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
    public void updateUserPortrait(final BmobFile newPortrait, final NemoCallback<BmobFile> callback) {
        final User currentUser = BmobUser.getCurrentUser(User.class);

        final User targetUser = new User();
        targetUser.setObjectId(currentUser.getObjectId());

        final BmobFile oldPortrait = currentUser.getPortrait();

        newPortrait.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    targetUser.setPortrait(newPortrait);
                    targetUser.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (null == e) {
                                callback.onSuccess(newPortrait);
                                currentUser.setPortrait(newPortrait);
                                if (null != oldPortrait) {
                                    oldPortrait.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {

                                        }
                                    });
                                }
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
    public void updateUserInfo(final User user, final NemoCallback<User> callback) {
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (null == e) {
                    callback.onSuccess(user);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getAuthorList(int pageIndex, final NemoCallback<List<User>> callback) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereGreaterThanOrEqualTo(UserSchema.Table.Cols.STATUS, 2);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> authorList, BmobException e) {
                if (null == e) {
                    callback.onSuccess(authorList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getFollowAuthorList(User follower, int pageIndex, final NemoCallback<List<User>> callback) {
        BmobQuery<Follow> query = new BmobQuery<>();
        query.addWhereEqualTo(FollowSchema.Table.Cols.FOLLOWER, follower);
        query.include(FollowSchema.Table.Cols.AUTHOR);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.order(FollowSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> followList, BmobException e) {
                if (null == e) {
                    List<User> authorList = new ArrayList<>();
                    for (Follow follow : followList) {
                        authorList.add(follow.getAuthor());
                    }
                    callback.onSuccess(authorList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getFollowerList(User author, int pageIndex, final NemoCallback<List<User>> callback) {
        BmobQuery<Follow> query = new BmobQuery<>();
        query.addWhereEqualTo(FollowSchema.Table.Cols.AUTHOR, BmobUser.getCurrentUser(User.class));
        query.include(FollowSchema.Table.Cols.FOLLOWER);
        query.setLimit(PAGE_LIMIT);
        query.setSkip(pageIndex * PAGE_LIMIT);
        query.order(FollowSchema.Table.Cols.CREATE_TIME);
        query.findObjects(new FindListener<Follow>() {
            @Override
            public void done(List<Follow> followList, BmobException e) {
                if (null == e) {
                    List<User> followerList = new ArrayList<>();
                    for (Follow follow : followList) {
                        followerList.add(follow.getFollower());
                    }
                    callback.onSuccess(followerList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getFollowAuthorTotal(User follower, final NemoCallback<Integer> callback) {
        BmobQuery<Follow> query = new BmobQuery<>();
        query.addWhereEqualTo(FollowSchema.Table.Cols.FOLLOWER, follower);
        query.count(Follow.class, new CountListener() {
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
    public void getFollowerTotal(User author, final NemoCallback<Integer> callback) {
        BmobQuery<Follow> query = new BmobQuery<>();
        query.addWhereEqualTo(FollowSchema.Table.Cols.AUTHOR, author);
        query.count(Follow.class, new CountListener() {
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
    public void searchAuthor(String keyword, final NemoCallback<List<User>> callback) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo(UserSchema.Table.Cols.NAME, keyword);
        query.addWhereGreaterThanOrEqualTo(UserSchema.Table.Cols.STATUS, 2);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> userList, BmobException e) {
                if (e == null) {
                    callback.onSuccess(userList);
                } else {
                    callback.onFailed(e.getMessage());
                }
            }
        });
    }
}
