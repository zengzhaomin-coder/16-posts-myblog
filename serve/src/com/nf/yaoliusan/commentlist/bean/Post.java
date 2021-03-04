package com.nf.yaoliusan.commentlist.bean;

import java.util.Date;

// 博客列表
public class Post {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String cover;
    private Date created;
    private int likes;

    // 无参构造
    public Post() {
    }

    // 带参函数
    public Post(String title, String content, String author, String cover) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.cover = cover;
    }

    // get / set 访问器
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    // toString 方法
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", cover='" + cover + '\'' +
                ", created=" + created +
                ", likes=" + likes +
                '}';
    }
}
