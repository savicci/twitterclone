package com.twitterclone.twitterclone.security.payload;

import java.time.LocalDateTime;

public class TweetResponse {
    private String text;
    private LocalDateTime createdAt;
    private String username;
    private String nick;

    public TweetResponse(String text, LocalDateTime createdAt, String username, String nick) {
        this.text = text;
        this.createdAt = createdAt;
        this.username = username;
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
