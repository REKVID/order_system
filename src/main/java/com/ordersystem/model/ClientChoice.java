package com.ordersystem.model;

public class ClientChoice {
    private int id;
    private int documentId;
    private int productId;
    private Integer deliveryMethodsId;
    private int quantity;

    public ClientChoice() {
    }

    public ClientChoice(int id, int documentId, int productId, Integer deliveryMethodsId, int quantity) {
        this.id = id;
        this.documentId = documentId;
        this.productId = productId;
        this.deliveryMethodsId = deliveryMethodsId;
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

    public Integer getDeliveryMethodsId() {
        return deliveryMethodsId;
    }

    public void setDeliveryMethodsId(Integer deliveryMethodsId) {
        this.deliveryMethodsId = deliveryMethodsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ClientChoice{" +
                "id=" + id +
                ", documentId=" + documentId +
                ", productId=" + productId +
                ", deliveryMethodsId=" + deliveryMethodsId +
                ", quantity=" + quantity +
                '}';
    }
}
