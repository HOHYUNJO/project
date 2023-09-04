package com.team2.project.Minigame.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class BoardVo {

    private int board_idx;
    private String board_cd;
    private String boardTitle;
    private String memberNick;
    private String boardText;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date CREATED_AT;
    private String CREATED_BY;
    private Date UPDATED_AT;
    private String UPDATED_BY;
    private String keyword;
    private int boardCount;

    public BoardVo() {
    }

    public BoardVo( String boardTitle, String memberNick, String boardText, int boardCount, Date CREATED_AT) {
        this.boardTitle = boardTitle;
        this.memberNick = memberNick;
        this.boardText = boardText;
        this.boardCount= boardCount;
        this.CREATED_AT = CREATED_AT;
    }

    public String getBoard_cd(){
        return board_cd;
    }
    public String getCREATED_BY(){
        return CREATED_BY;
    }
    public void setCREATED_BY(String CREATED_BY){
        this.CREATED_BY = CREATED_BY;
    }
    public Date getUPDATED_AT(){
        return UPDATED_AT;
    }
    public void setUPDATED_AT(Date UPDATED_AT){
        this.UPDATED_AT = UPDATED_AT;
    }
    public String getUPDATED_BY(){
        return UPDATED_BY;
    }
    public void setUPDATED_BY(String UPDATED_BY){
        this.UPDATED_BY = UPDATED_BY;
    }
    public String getKeyword(){
        return keyword;
    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
    public int getBoard_idx() {
        return board_idx;
    }

    public void setBoard_idx(int board_idx) {
        this.board_idx = board_idx;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public String getBoardText() {
        return boardText;
    }

    public void setBoardText(String boardText) {
        this.boardText = boardText;
    }

    public Date getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(Date CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public int getBoardCount() {
        return boardCount;
    }

    public void setBoardCount(int boardCount) {
        this.boardCount = boardCount;
    }
}

