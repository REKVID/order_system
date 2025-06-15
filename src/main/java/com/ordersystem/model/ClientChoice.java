package com.ordersystem.model;

import java.math.BigDecimal;

public class ClientChoice {
    private int id;
    private int documentId;
    private int productId;
    private Integer deliveryMethodsId;
    private int quantity;
    private BigDecimal finalDeliveryCost;

    public ClientChoice() {
    }

    public ClientChoice(int id, int documentId, int productId, Integer deliveryMethodsId, int quantity,
            BigDecimal finalDeliveryCost) {
        this.id = id;
        this.documentId = documentId;
        this.productId = productId;
        this.deliveryMethodsId = deliveryMethodsId;
        this.quantity = quantity;
        this.finalDeliveryCost = finalDeliveryCost;
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

    public BigDecimal getFinalDeliveryCost() {
        return finalDeliveryCost;
    }

    public void setFinalDeliveryCost(BigDecimal finalDeliveryCost) {
        this.finalDeliveryCost = finalDeliveryCost;
    }

    @Override
    public String toString() {
        return "ClientChoice{" +
                "id=" + id +
                ", documentId=" + documentId +
                ", productId=" + productId +
                ", deliveryMethodsId=" + deliveryMethodsId +
                ", quantity=" + quantity +
                ", finalDeliveryCost=" + finalDeliveryCost +
                '}';
    }
}
