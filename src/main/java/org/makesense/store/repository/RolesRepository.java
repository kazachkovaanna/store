package org.makesense.store.repository;

import org.makesense.store.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolesRepository  extends MongoRepository <Role, String>{
    public Role findByRole(String role);
}
