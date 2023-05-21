package com.fractal.challenge.controller;

import com.fractal.challenge.model.*;
import com.fractal.challenge.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    private ProductController productController;
    private Integer orderNumber = 0;

    public OrderController(ProductController productController) {
        this.productController = productController;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Order> index() {
        return orderRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    Order make(@RequestBody OrderRequest<Integer, String> orderRequest) {
        Order order = new Order(orderNumber, orderRequest.getPrice());
        boolean areThereStock = true;
        if (orderRequest.getSize() != 0) {
            for (iPair<Integer, String> itemCart : orderRequest.getCart()) {
                iPair<Boolean, Product> itemStatus = productController.verifyStock(itemCart.second, itemCart.first);
                if (!itemStatus.first) {
                    areThereStock = false;
                    break;
                }
                order.addProduct(itemStatus.second, itemCart.first);
            }
        }

        if (!areThereStock) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There arent Sock or The product doesn't exist!");
        }

        ++orderNumber;
        return orderRepository.save(order);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("{id}")
    Order update(@PathVariable String id, @RequestBody OrderRequest<Integer, String> newCart) {
        try {
            Order cartFound = orderRepository
                    .findById(id)
                    .orElseThrow(NoSuchElementException::new);

            List<iPair<Integer, Product>> newList = new ArrayList<>();
            boolean areThereStock = true;
            if (newCart.getSize() != 0) {
                for (iPair<Integer, String> itemCart : newCart.getCart()) {
                    iPair<Boolean, Product> itemStatus = productController.verifyStock(itemCart.second, itemCart.first);
                    if (!itemStatus.first) {
                        areThereStock = false;
                        break;
                    }
                    newList.add(new iPair<>(itemCart.first, itemStatus.second));
                }
            }

            if (!areThereStock) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There arent Sock or The product doesn't exist!");
            }

            cartFound.setPriceFinal(newCart.getPrice());
            cartFound.setProductList(newList);
            cartFound.setModifiedAt();
            return orderRepository.save(cartFound);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with ID: " + id, e);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/status/{id}")
    Order setStatus(@PathVariable String id, @RequestBody NewState<Character> newState) {

        /**
         * P(Progress), I(In Progress), C(Completed)
         * */

        try {
            Order cartFound = orderRepository
                    .findById(id)
                    .orElseThrow(NoSuchElementException::new);

            if (cartFound.getStatus() == 'C') {
                throw new ResponseStatusException(HttpStatus.OK, "Cart exist, but is Completed!");
            }

            cartFound.setStatus(newState.getNewState());
            return orderRepository.save(cartFound);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with ID: " + id, e);
        }
    }
}
