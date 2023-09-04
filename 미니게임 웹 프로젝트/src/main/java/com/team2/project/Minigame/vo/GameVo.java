package com.team2.project.Minigame.vo;

import java.util.Date;

public class GameVo {
    private int INDEX_NO;
    private String gameID;
    private String gameTitle;
    private String gameMaker;
    private String imageFileName; // 이미지 파일 이름 추가
    private double rating;
    private String gameDetail;
    private Date CREATE_AT;

    private int gameCount;

    public void setRating(Double rating) {
        if (rating == null) {
            this.rating = 0.0;
        } else {
            this.rating = rating;
        }
    }


    public int getINDEX_NO() {
        return INDEX_NO;
    }

    public void setINDEX_NO(int INDEX_NO) {
        this.INDEX_NO = INDEX_NO;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameMaker() {
        return gameMaker;
    }

    public void setGameMaker(String gameMaker) {
        this.gameMaker = gameMaker;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getGameDetail() {
        return gameDetail;
    }

    public void setGameDetail(String gameDetail) {
        this.gameDetail = gameDetail;
    }

    public Date getCREATE_AT() {
        return CREATE_AT;
    }

    public void setCREATE_AT(Date CREATE_AT) {
        this.CREATE_AT = CREATE_AT;
    }


    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}