package com.rumofuture.nemo.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class Favorite extends BmobObject {

    private Book book;
    private User favor;

    private Integer status;

    public Favorite() {

    }

    public Favorite(Book book, User favor) {
        this.book = book;
        this.favor = favor;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getFavor() {
        return favor;
    }

    public void setFavor(User favor) {
        this.favor = favor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
