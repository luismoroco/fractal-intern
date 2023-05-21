package com.fractal.challenge.repository;

import com.fractal.challenge.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
    /**
     * Methods available in Mongodb Repository
     * */
}
