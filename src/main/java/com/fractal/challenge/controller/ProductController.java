package com.fractal.challenge.controller;

import com.fractal.challenge.model.Product;
import com.fractal.challenge.model.iPair;
import com.fractal.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Product> index() {
        return productRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Product make(@RequestBody Product product) {
        Product itemFound = productRepository.findByName(product.getName());
        if (itemFound != null) {
            throw new ResponseStatusException(HttpStatus.OK, "Item exist!");
        }

        return productRepository.save(product);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    Product update(@PathVariable String id, @RequestBody Product product) {
        try {
            Product itemFound = productRepository
                    .findById(id)
                    .orElseThrow(NoSuchElementException::new);

            itemFound.setName(product.getName());
            itemFound.setStock(product.getStock());
            itemFound.setPrice(product.getPrice());

            return productRepository.save(itemFound);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with ID: " + id, e);
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        try {
            Product itemFound = productRepository
                    .findById(id)
                    .orElseThrow(NoSuchElementException::new);

            productRepository.delete(itemFound);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with ID: " + id, e);
        }
    }

    public iPair<Boolean, Product> verifyStock(String id, Integer LenProductRequired) {
        try {
            Product itemFound = productRepository
                    .findById(id)
                    .orElseThrow(NoSuchElementException::new);

            return new iPair<>(LenProductRequired <= itemFound.getStock(), itemFound);
        } catch (NoSuchElementException e) {
            return new iPair<>(false, new Product("", 0.0f, 0));
        }
    }
}
