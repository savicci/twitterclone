package com.twitterclone.twitterclone.db.repositories;

import com.twitterclone.twitterclone.db.models.Tweet;
import org.springframework.data.repository.CrudRepository;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
    Tweet findById(long id);
}
