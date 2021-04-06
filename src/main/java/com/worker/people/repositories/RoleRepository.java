package com.worker.people.repositories;

import com.worker.people.domain.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RoleRepository extends MongoRepository<Role, String> {

    @Query("{authority: '?0'}")
    Role findByAuthority(String authority);
    Role getByAuthority(String authority);

}
