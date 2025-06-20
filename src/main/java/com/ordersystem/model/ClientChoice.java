package com.ordersystem.model;

public class ClientChoice {
    private int id;
    private int documentId;
    private int productId;
    private Integer deliveryMethodId;
    private int quantity;

    public ClientChoice() {
    }

    public ClientChoice(int id, int documentId, int productId, Integer deliveryMethodId, int quantity) {
        this.id = id;
        this.documentId = documentId;
        this.productId = productId;
        this.deliveryMethodId = deliveryMethodId;
        this.quantity = quantity;
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
}
