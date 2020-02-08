package com.twitterclone.twitterclone.controller;

import com.twitterclone.twitterclone.security.CurrentUser;
import com.twitterclone.twitterclone.security.UserPrincipal;
import com.twitterclone.twitterclone.security.payload.UserSummary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }
}
