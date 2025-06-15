package com.ordersystem.model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private BigDecimal price;
    private String description;
    private boolean isDeliveryAvailable;

    public Product() {
    }

    public Product(int id, String name, BigDecimal price, String description, boolean isDeliveryAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isDeliveryAvailable = isDeliveryAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public boolean isDeliveryAvailable() {
        return isDeliveryAvailable;
    }

    public void setDeliveryAvailable(boolean deliveryAvailable) {
        isDeliveryAvailable = deliveryAvailable;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name +
                ", price=" + price +
                ", description='" + description +
                ", isDeliveryAvailable=" + isDeliveryAvailable +
                '}';
    }
}
