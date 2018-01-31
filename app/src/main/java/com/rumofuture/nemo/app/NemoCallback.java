package com.rumofuture.nemo.app;

/**
 * Created by WangZhenqi on 2018/1/30.
 */

public interface NemoCallback<T> {
    void onSuccess(T data);
    void onFailed(String message);
}
