package com.fractal.challenge.model;

import java.util.List;

public class OrderRequest<T, R> {
    private List<iPair<T, R>> cart;
    private Float price;

    public Integer getSize() {
        return cart.size();
    }
    public List<iPair<T, R>> getCart() {
        return cart;
    }

    public void setCart(List<iPair<T, R>> cart) {
        this.cart = cart;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}