package com.twitterclone.twitterclone;

import com.twitterclone.twitterclone.db.models.User;
import com.twitterclone.twitterclone.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TwittercloneApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(TwittercloneApplication.class, args);
    }

    @RequestMapping("/users")
    public User getUserById(@RequestParam(required = true) long id) {
        return userRepository.findById(id);
    }

    @Bean
    public CommandLineRunner insertData(UserRepository repository) {
        return args -> {
            User user = new User();
            user.setId(1);
            user.setNick("nickynick");
            user.setEmailAddress("email@address.com");

            repository.save(user);
        };
    }
}
