package com.twitterclone.twitterclone.security.payload;

import java.time.LocalDateTime;

public class TweetResponse {
    private String text;
    private LocalDateTime createdAt;
    private String owner;

    public TweetResponse(String text, LocalDateTime createdAt, String owner) {
        this.text = text;
        this.createdAt = createdAt;
        this.owner = owner;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
