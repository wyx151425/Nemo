package com.rumofuture.nemo.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2017/1/29.
 */

/**
 * @author 王振琦
 */
public class Page extends BmobObject {
    /**
     * 所属漫画册
     */
    private Book book;
    /**
     * 图像
     */
    private BmobFile image;
    /**
     * 状态(0已删除/1正常显示)
     */
    private Integer status;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
