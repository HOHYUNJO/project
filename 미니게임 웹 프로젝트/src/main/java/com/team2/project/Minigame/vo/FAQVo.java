package com.team2.project.Minigame.vo;

import java.sql.Date;

public class FAQVo {
    private int FAQ_idx;
    private int FAQ_Count;

    public FAQVo(int FAQ_idx, int FAQ_Count) {
        this.FAQ_idx = FAQ_idx;
        this.FAQ_Count = FAQ_Count;
    }
    public FAQVo() {}

    public int getFAQ_idx() {
        return FAQ_idx;
    }

    public void setFAQ_idx(int FAQ_idx) {
        this.FAQ_idx = FAQ_idx;
    }

    public int getFAQ_Count() {
        return FAQ_Count;
    }

    public void setFAQ_Count(int FAQ_Count) {
        this.FAQ_Count = FAQ_Count;
    }



}
