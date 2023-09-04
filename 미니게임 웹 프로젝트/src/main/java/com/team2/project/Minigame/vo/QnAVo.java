package com.team2.project.Minigame.vo;

import java.sql.Date;

public class QnAVo {
    private Integer QNA_idx;

    private String QNA_Title;

    private String QNA_Text;

    private Date CREATED_AT;

    private Integer QNACount;

    private String memberNick;

    public QnAVo() {
    }

    public QnAVo( String QNA_Title, String memberNick, String QNA_Text, int QNACount) {
        this.QNA_Title = QNA_Title;
        this.memberNick = memberNick;
        this.QNA_Text = QNA_Text;
        this.QNACount= QNACount;
    }

    public String getMemberNick() {
        return memberNick;
    }

    public void setMemberNick(String memberNick) {
        this.memberNick = memberNick;
    }

    public int getQNA_idx() {
        return QNA_idx;
    }

    public void setQNA_idx(int QNA_idx) {
        this.QNA_idx = QNA_idx;
    }

    public String getQNA_Title() {
        return QNA_Title;
    }

    public void setQNA_Title(String QNA_Title) {
        this.QNA_Title = QNA_Title;
    }

    public String getQNA_Text() {
        return QNA_Text;
    }

    public void setQNA_Text(String QNA_Text) {
        this.QNA_Text = QNA_Text;
    }

    public Date getCREATED_AT() {
        return CREATED_AT;
    }

    public void setCREATED_AT(Date CREATED_AT) {
        this.CREATED_AT = CREATED_AT;
    }

    public int getQNACount() {
        return QNACount;
    }

    public void setQNACount(int QNACount) {
        this.QNACount = QNACount;
    }
}
