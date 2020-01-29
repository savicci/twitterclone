package com.twitterclone.twitterclone.db.repositories;

import com.twitterclone.twitterclone.db.models.Follow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowRepository extends CrudRepository<Follow, Long> {
    List<Follow> findAllByFollowerId(long followerId);
}
