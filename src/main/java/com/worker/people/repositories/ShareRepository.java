package com.worker.people.repositories;

import com.worker.people.domain.entities.Share;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareRepository extends MongoRepository<Share,String> {
    //find by Author id
    List<Share> findAllByAuthorId(String AuthorId);

    Optional<Share> findByAuthorId(String authorId);

    List<Share> findAllByAuthorIdOrderByTimeDesc(String authorID);

    List<Share> findAllByAuthorIdInOrderByTimeDesc(List<String> authorIDs);
}
