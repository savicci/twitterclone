package com.twitterclone.twitterclone.security.payload;

public class UserSummary {
    private long id;
    private String username;
    private String nick;

    public UserSummary(long id, String username, String nick) {
        this.id = id;
        this.username = username;
        this.nick = nick;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
