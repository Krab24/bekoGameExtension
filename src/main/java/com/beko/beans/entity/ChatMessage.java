package com.beko.beans.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by ankovalenko on 5/20/2015.
 */
@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity{
    private static final long serialVersionUID = 7081335641847820893L;

    private String chatMessage;
    private User user;

    public ChatMessage(){
        setId(UUID.randomUUID().toString());
    }

    @Column(name = "message", nullable = false)
    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void lazyInit() {

    }
}
