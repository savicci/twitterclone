package com.twitterclone.twitterclone.security.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TweetAddRequest {
    @NotBlank
    @Size(min = 10, max = 280)
    private String text;

    @NotBlank
    @Size(max = 30)
    private String userName;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
