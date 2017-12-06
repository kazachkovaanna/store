package org.makesense.store.repository;

import org.makesense.store.models.Role;
import org.makesense.store.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsersRepository extends MongoRepository<User, String> {
    public User findByName(String name);
    public List<User> findUsersByRolesIsContaining(Role role);
}
