package com.ordersystem.controller;

import java.math.BigDecimal;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.Products;
import com.ordersystem.model.AvailableDeliveryMethod;
import com.ordersystem.model.Product;

import javafx.scene.control.Alert;

public class AvailableDeliveryMethodController {

    private final AvailableDeliveryMethods availableDeliveryMethodsContainer;
    private final Products productsContainer;

    public AvailableDeliveryMethodController(AvailableDeliveryMethods availableDeliveryMethodsContainer,
            Products productsContainer) {
        this.availableDeliveryMethodsContainer = availableDeliveryMethodsContainer;
        this.productsContainer = productsContainer;
    }

    public void createAvailableDeliveryMethod(String productIdText, String deliveryMethodIdText,
            String deliveryCostText) {
        try {
            int productId = Integer.parseInt(productIdText);
            int deliveryMethodId = Integer.parseInt(deliveryMethodIdText);
            BigDecimal deliveryCost = new BigDecimal(deliveryCostText);

            Product product = productsContainer.findById(productId);

            if (product == null) {
                showAlert(Alert.AlertType.ERROR, "Продукт не найден.");
                return;
            }

            if (!product.isDeliveryAvailable()) {
                showAlert(Alert.AlertType.ERROR, "Для этого продукта доставка не запрещена.");
                return;
            }

            AvailableDeliveryMethod newMethod = new AvailableDeliveryMethod(productId, deliveryMethodId, deliveryCost);
            availableDeliveryMethodsContainer.create(newMethod);

            showAlert(Alert.AlertType.INFORMATION, "Новый доступный способ доставки успешно создан.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID и стоимость должны быть числами.");
        }
    }

    public void updateAvailableDeliveryMethod(String productIdText, String deliveryMethodIdText,
            String deliveryCostText) {
        try {
            int productId = Integer.parseInt(productIdText);
            int deliveryMethodId = Integer.parseInt(deliveryMethodIdText);
            BigDecimal deliveryCost = new BigDecimal(deliveryCostText);

            AvailableDeliveryMethod methodToUpdate = new AvailableDeliveryMethod(productId, deliveryMethodId,
                    deliveryCost);
            availableDeliveryMethodsContainer.update(methodToUpdate);

            showAlert(Alert.AlertType.INFORMATION, "Способ доставки обновлен.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID и стоимость должны быть числами.");
        }
    }

    public void deleteAvailableDeliveryMethod(String productIdText, String deliveryMethodIdText) {
        try {
            int productId = Integer.parseInt(productIdText);
            int deliveryMethodId = Integer.parseInt(deliveryMethodIdText);

            AvailableDeliveryMethod methodToDelete = new AvailableDeliveryMethod(productId, deliveryMethodId, null);
            boolean success = availableDeliveryMethodsContainer.delete(methodToDelete);
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Способ доставки удален.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Не удалось удалить способ доставки так как он используется в сделке");
            }

            showAlert(Alert.AlertType.INFORMATION, "Способ доставки удален.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID должны быть числами.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}