package com.rumofuture.nemo.model.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by WangZhenqi on 2017/4/18.
 */

public class Review extends BmobObject {

    private Book book;
    private User reviewer;
    private String content;

    private Integer status;

    public Review() {

    }

    public Review(Book book, User reviewer) {
        this.book = book;
        this.reviewer = reviewer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
