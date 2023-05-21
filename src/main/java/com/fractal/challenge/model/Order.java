package com.fractal.challenge.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document
public class Order {
    @Id
    private String id;
    private int order;
    private Date modifiedAt = new Date();
    private static final Date createdAt = new Date();
    private List<iPair<Integer, Product>> products = new ArrayList<>();
    private Float priceFinal = 0.0f;
    private Character status = 'P';

    public Order(int order, Float priceFinal) {
        this.order = order;
        this.priceFinal = priceFinal;
    }

    public Character getStatus() { return status; }

    public void setStatus(Character status) { this.status = status; }
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<iPair<Integer, Product>> getProducts() {
        return products;
    }

    public void addProduct(Product product, Integer len) {
        products.add(new iPair<>(len, product));
    }

    public void removeProduct(Integer index) {
        products.remove(index);
    }

    public boolean updateByIndex(Integer index, Integer newLen) {
        if (newLen >= 0 && newLen < products.size()) {
            iPair<Integer, Product> itemFound = products.get(index);
            itemFound.setFirst(newLen);
            return  true;
        }
        return false;
    }

    public void setProductList(List<iPair<Integer, Product>> newProducList) {
        products = newProducList;
    }

    public Float getPriceFinal() {
        return priceFinal;
    }

    public void setPriceFinal(Float priceFinal) {
        this.priceFinal = priceFinal;
    }

    public void setModifiedAt() {
        modifiedAt = new Date();
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
