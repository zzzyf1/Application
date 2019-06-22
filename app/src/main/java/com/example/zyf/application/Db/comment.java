package com.example.zyf.application.Db;

import java.sql.Date;

public class comment {
    private String commentator;//评论人
    private int comment_id;//评论ID
    private String comment;//评论内容
    private Date date;//评论发表日期


    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentator() {
        return commentator;
    }

    public void setCommentator(String commentator) {
        this.commentator = commentator;
    }
}
