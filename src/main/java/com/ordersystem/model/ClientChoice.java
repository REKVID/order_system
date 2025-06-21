package com.ordersystem.model;

import java.math.BigDecimal;

public class ClientChoice {
    private int id;
    private int documentId;
    private int productId;
    private Integer deliveryMethodId;
    private int quantity;

    private String productName;
    private BigDecimal productPrice;
    private String deliveryMethodName;
    private BigDecimal deliveryCost;
    private BigDecimal totalProductsPrice;
    private BigDecimal totalDeliveryCost;

    public ClientChoice() {
    }

    public ClientChoice(int id, int documentId, int productId, Integer deliveryMethodId, int quantity,
            String productName, BigDecimal productPrice, String deliveryMethodName, BigDecimal deliveryCost) {
        this.id = id;
        this.documentId = documentId;
        this.productId = productId;
        this.deliveryMethodId = deliveryMethodId;
        this.quantity = quantity;

        this.productName = productName;
        this.productPrice = productPrice;
        this.deliveryMethodName = deliveryMethodName;
        this.deliveryCost = deliveryCost;

        this.totalProductsPrice = productPrice.multiply(BigDecimal.valueOf(quantity));
        if (deliveryCost != null) {
            this.totalDeliveryCost = deliveryCost.multiply(BigDecimal.valueOf(quantity));
        } else {
            this.totalDeliveryCost = BigDecimal.ZERO;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Integer getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Integer deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public String getDeliveryMethodName() {
        return deliveryMethodName;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public BigDecimal getTotalProductsPrice() {
        return totalProductsPrice;
    }

    public BigDecimal getTotalDeliveryCost() {
        return totalDeliveryCost;
    }
}
