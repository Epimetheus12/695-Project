package com.worker.people.repositories;

import com.worker.people.domain.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
//    void finOne(User findOne);
}
