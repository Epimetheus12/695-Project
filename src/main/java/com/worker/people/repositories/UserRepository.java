package com.worker.people.repositories;

import com.worker.people.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends MongoRepository<User, String> {
    //    void finOne(User findOne);
    @Query("{email: ?0}")
    Optional<User> findByEmail(String email);
    @Query("{username: ?0}")
    Optional<User> findByUsername(String username);


}
