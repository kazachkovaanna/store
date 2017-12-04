package org.makesense.store.repository;

import org.makesense.store.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
    public User findByName(String name);
}
