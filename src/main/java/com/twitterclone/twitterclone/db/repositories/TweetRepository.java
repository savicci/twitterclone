package com.twitterclone.twitterclone.db.repositories;

import com.twitterclone.twitterclone.db.models.Tweet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TweetRepository extends CrudRepository<Tweet, Long> {
    Tweet findById(long id);

    List<Tweet> findAllByIdIn(List<Long> userIds);
}
