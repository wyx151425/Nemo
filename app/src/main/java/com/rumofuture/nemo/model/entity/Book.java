package com.rumofuture.nemo.model.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by WangZhenqi on 2016/12/24.
 */

public class Book extends BmobObject {
    /**
     * 所属漫画作者
     */
    private User author;
    /**
     * 漫画册名称
     */
    private String name;
    /**
     * 漫画册所属风格
     */
    private String style;
    /**
     * 漫画册简介
     */
    private String introduction;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 漫画册封面路径
     */
    private BmobFile cover;
    /**
     * 漫画册漫画分页数
     */
    private Integer page;
    /**
     * 收藏此漫画的用户数
     */
    private Integer favor;
    /**
     * 漫画册审核状态（0禁止展示/1新创建待审核/2更新后待审核/3审核通过可展示）
     */
    private Integer status;
    /**
     * 漫画册类型（0本地/1网页链接）
     */
    private Integer type;
    /**
     * 漫画册是否公开
     */
    private Boolean publish;
    /**
     * 漫画册是否是自己原创
     */
    private Boolean copyright;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BmobFile getCover() {
        return cover;
    }

    public void setCover(BmobFile cover) {
        this.cover = cover;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getFavor() {
        return favor;
    }

    public void setFavor(Integer favor) {
        this.favor = favor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }
}
