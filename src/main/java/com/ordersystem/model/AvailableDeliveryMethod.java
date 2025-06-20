package com.ordersystem.model;

import java.math.BigDecimal;

public class AvailableDeliveryMethod {
    private int productId;
    private int deliveryMethodId;
    private BigDecimal deliveryCost;

    public AvailableDeliveryMethod() {
    }

    public AvailableDeliveryMethod(int productId, int deliveryMethodId, BigDecimal deliveryCost) {
        this.productId = productId;
        this.deliveryMethodId = deliveryMethodId;
        this.deliveryCost = deliveryCost;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(int deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }
}
