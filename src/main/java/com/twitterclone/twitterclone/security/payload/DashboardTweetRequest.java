package com.twitterclone.twitterclone.security.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DashboardTweetRequest {
    @NotBlank
    @Size(max = 20)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
