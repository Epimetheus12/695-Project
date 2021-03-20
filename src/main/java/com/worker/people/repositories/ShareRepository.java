package com.worker.people.repositories;

import com.worker.people.domain.entities.Share;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ShareRepository extends MongoRepository<Share,String> {
    //find by Author id
    List<Share> findByAuthorId(String AuthorId);
}
