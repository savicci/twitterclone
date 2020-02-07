package com.twitterclone.twitterclone.controller;

import com.twitterclone.twitterclone.db.models.Follow;
import com.twitterclone.twitterclone.db.models.Tweet;
import com.twitterclone.twitterclone.db.models.User;
import com.twitterclone.twitterclone.db.repositories.FollowRepository;
import com.twitterclone.twitterclone.db.repositories.TweetRepository;
import com.twitterclone.twitterclone.db.repositories.UserRepository;
import com.twitterclone.twitterclone.security.exception.BadRequestException;
import com.twitterclone.twitterclone.security.payload.ApiResponse;
import com.twitterclone.twitterclone.security.payload.DashboardTweetRequest;
import com.twitterclone.twitterclone.security.payload.TweetAddRequest;
import com.twitterclone.twitterclone.security.payload.TweetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private FollowRepository followRepository;

    @Autowired
    public TweetController(TweetRepository tweetRepository, UserRepository userRepository, FollowRepository followRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    @RequestMapping("/add")
    private ResponseEntity<?> addTweet(@RequestBody TweetAddRequest tweetRequest) {
        try {
            User user = userRepository.findByUsername(tweetRequest.getUserName()).orElseThrow(IllegalArgumentException::new);

            Tweet tweet = new Tweet(tweetRequest.getText(), LocalDateTime.now(), user.getId());

            tweetRepository.save(tweet);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new BadRequestException("Could not add tweet", e.getCause()),
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(new ApiResponse(true, "Tweet added succesfully"));
    }

    @RequestMapping("/dashboard")
    private ResponseEntity<?> getAllTweets(@RequestBody DashboardTweetRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow(IllegalArgumentException::new);
            List<Follow> following = followRepository.findAllByFollowerId(user.getId());

            Map<Long, Tweet> tweets = tweetRepository.findAllByIdIn(following.stream().map(Follow::getFollowingId).collect(Collectors.toList()))
                    .stream()
                    .collect(Collectors.toMap(Tweet::getId, Function.identity()));

            return ResponseEntity.ok(tweets.keySet().stream()
                    .map(key -> new TweetResponse(tweets.get(key).getText(), tweets.get(key).getCreatedAt(), userRepository.findById(key).get().getNick()))
                    .collect(Collectors.toList()));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new BadRequestException("Coult not find user with given username"), HttpStatus.BAD_REQUEST);
        }
    }
}
