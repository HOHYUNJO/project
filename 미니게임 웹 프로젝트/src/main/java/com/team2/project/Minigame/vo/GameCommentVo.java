package com.team2.project.Minigame.vo;

public class GameCommentVo {    // 테이블 이름 comments

    private int commentID;

    private int INDEX_NO;

    private String commentText;

    private String commentNick;

    private double rating;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public GameCommentVo(int commentID, String commentText, int INDEX_NO, String commentNick, double rating) {
        this.commentText = commentText;
        this.commentNick = commentNick;
        this.INDEX_NO = INDEX_NO;
        this.commentID = commentID;
        this.rating = rating;
    }

    public GameCommentVo() {

    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public int getINDEX_NO() {
        return INDEX_NO;
    }

    public void setINDEX_NO(int INDEX_NO) {
        this.INDEX_NO = INDEX_NO;
    }

    public String getCommentNick() {
        return commentNick;
    }

    public void setCommentNick(String commentNick) {
        this.commentNick = commentNick;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }


}
