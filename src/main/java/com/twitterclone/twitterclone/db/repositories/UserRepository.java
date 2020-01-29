package com.twitterclone.twitterclone.db.repositories;

import com.twitterclone.twitterclone.db.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    User findById(long id);
}
