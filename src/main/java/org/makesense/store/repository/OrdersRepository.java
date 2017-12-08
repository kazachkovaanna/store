package org.makesense.store.repository;

import org.makesense.store.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdersRepository extends MongoRepository<Order, String> {

    public Order findByUserEmail(String email);
    public List<Order> findAllByUserEmail(String email);
}
