package com.exampel.myMail.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_sender_id")
    private User userSender;
    @ManyToOne
    @JoinColumn(name = "user_recip_id")
    private User userRecip;

    @Column(name = "create_msg_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgTime;

    public Message() {}

    public User getUserRecip() {return userRecip;}

    public void setUserRecip(User userRecip) {this.userRecip = userRecip;}

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }
}
