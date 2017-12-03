package org.makesense.store.repository;

import org.makesense.store.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductsRepository extends MongoRepository<Product, String> {

    public List<Product> findAllByNameContains(String s);
    public Product findByName(String name);
}
