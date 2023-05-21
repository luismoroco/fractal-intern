package com.fractal.challenge.repository;

import com.fractal.challenge.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    /**
     * Methods available in Mongodb Repository
     * */

    Product findByName(String name);
}
