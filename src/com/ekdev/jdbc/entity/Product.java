package com.ekdev.jdbc.entity;

import java.math.BigDecimal;

public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Double rating;

    public Product(Long id, String name, BigDecimal price, String description, Double rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}
