package com.ekdev.jdbc.entity;

import java.math.BigDecimal;

public class Orders {

    private Long id;
    private Long buyerId;
    private BigDecimal price;
    private Boolean isPaid;

    public Orders(Long id, Long buyerId, BigDecimal price, Boolean isPaid) {
        this.id = id;
        this.buyerId = buyerId;
        this.price = price;
        this.isPaid = isPaid;
    }

    public Orders() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", buyerId=" + buyerId +
                ", price=" + price +
                ", isPaid=" + isPaid +
                '}';
    }
}
