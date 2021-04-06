package com.worker.people.repositories;

import com.worker.people.domain.entities.Share;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRepository extends MongoRepository<Share,String> {
    //find by Author id
    List<Share> findByAuthorId(String AuthorId);
}
