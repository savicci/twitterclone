package com.twitterclone.twitterclone;

import com.twitterclone.twitterclone.db.models.Follow;
import com.twitterclone.twitterclone.db.models.Tweet;
import com.twitterclone.twitterclone.db.models.User;
import com.twitterclone.twitterclone.db.repositories.FollowRepository;
import com.twitterclone.twitterclone.db.repositories.TweetRepository;
import com.twitterclone.twitterclone.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class TwittercloneApplication {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private FollowRepository followRepository;
    private TweetRepository tweetRepository;

    @Autowired
    public TwittercloneApplication(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                   FollowRepository followRepository, TweetRepository tweetRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.followRepository = followRepository;
        this.tweetRepository = tweetRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwittercloneApplication.class, args);
    }


    @Bean
    CommandLineRunner createData() {
        return args -> {
            User user1 = new User("newUserNick", "newUser", "newuser@email.com",
                    passwordEncoder.encode("password"), 0, 2);
            User user2 = new User("newUserNick2", "newUser2", "newuser2@email.com",
                    passwordEncoder.encode("password2"), 1, 1);
            User user3 = new User("newUserNick3", "newUser3", "newuser3@email.com",
                    passwordEncoder.encode("password3"), 2, 0);

            user1.setCreatedAt(LocalDateTime.now());
            user2.setCreatedAt(LocalDateTime.now().minusDays(2));
            user3.setCreatedAt(LocalDateTime.now().minusDays(4));

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            followRepository.save(new Follow(1, 2));
            followRepository.save(new Follow(1, 3));
            followRepository.save(new Follow(2, 3));

            tweetRepository.save(new Tweet("Tweet 1 by user1", LocalDateTime.now().minusHours(6), 1));
            tweetRepository.save(new Tweet("Tweet 1 by user2", LocalDateTime.now().minusHours(4), 2));
            tweetRepository.save(new Tweet("Tweet 1 by user3", LocalDateTime.now().minusHours(2), 3));
            tweetRepository.save(new Tweet("Tweet 2 by user3", LocalDateTime.now(), 3));
        };
    }
}
