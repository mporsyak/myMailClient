package com.exampel.myMail.model;


public class NewMessageDto {
    private String content;
    private String userSender;
    private String userRecip;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserSender() {
        return userSender;
    }

    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }

    public String getUserRecip() {
        return userRecip;
    }

    public void setUserRecip(String userRecip) {
        this.userRecip = userRecip;
    }
}
