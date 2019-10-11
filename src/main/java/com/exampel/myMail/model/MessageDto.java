package com.exampel.myMail.model;

import java.util.Date;

public class MessageDto {
    private String content;
    private String goal;
    private boolean myMsg;
    private Date createMsgTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public boolean isMyMsg() {
        return myMsg;
    }

    public void setMyMsg(boolean myMsg) {
        this.myMsg = myMsg;
    }

    public Date getCreateMsgTime() {
        return createMsgTime;
    }

    public void setCreateMsgTime(Date createMsgTime) {
        this.createMsgTime = createMsgTime;
    }
}
