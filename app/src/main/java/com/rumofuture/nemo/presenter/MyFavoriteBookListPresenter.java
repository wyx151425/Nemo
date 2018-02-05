package com.rumofuture.nemo.presenter;

import android.support.annotation.NonNull;

import com.rumofuture.nemo.app.NemoCallback;
import com.rumofuture.nemo.app.contract.MyFavoriteBookListContract;
import com.rumofuture.nemo.model.entity.Book;
import com.rumofuture.nemo.model.entity.User;
import com.rumofuture.nemo.model.source.BookDataSource;
import com.rumofuture.nemo.model.source.UserDataSource;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by WangZhenqi on 2017/4/24.
 */

public class MyFavoriteBookListPresenter implements MyFavoriteBookListContract.Presenter {

    private MyFavoriteBookListContract.View mView;
    private UserDataSource mUserRepository;
    private BookDataSource mBookRepository;

    public MyFavoriteBookListPresenter(
            @NonNull MyFavoriteBookListContract.View view,
            @NonNull UserDataSource userRepository,
            @NonNull BookDataSource bookRepository
    ) {
        mView = view;
        mUserRepository = userRepository;
        mBookRepository = bookRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMyFavoriteBookList(int pageCode) {
        mBookRepository.getFavoriteBookList(
                BmobUser.getCurrentUser(User.class), pageCode, new NemoCallback<List<Book>>() {
                    @Override
                    public void onSuccess(List<Book> data) {
                        if (mView.isActive()) {
                            mView.showMyFavoriteBookListGetSuccess(data);
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        if (mView.isActive()) {
                            mView.showMyFavoriteBookListGetFailed(message);
                        }
                    }
                }
        );
        mBookRepository.getFavoriteBookTotalNumber(
                BmobUser.getCurrentUser(User.class), new NemoCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {
                        User currentUser = BmobUser.getCurrentUser(User.class);
                        if (!Objects.equals(currentUser.getFavorite(), data)) {
                            currentUser.setFavorite(data);
                            mUserRepository.updateUserInfo(currentUser, new NemoCallback<User>() {
                                @Override
                                public void onSuccess(User data) {

                                }

                                @Override
                                public void onFailed(String message) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailed(String message) {

                    }
                }
        );
    }
}
