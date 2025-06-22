package com.ordersystem.controller;

import java.math.BigDecimal;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.Products;
import com.ordersystem.dao.AvailableDeliveryMethodDAO;
import com.ordersystem.model.Product;

import javafx.scene.control.Alert;

public class ProductController {

    private final Products productsContainer;
    private final AvailableDeliveryMethods availableDeliveryMethodsContainer;
    private final AvailableDeliveryMethodDAO availableDeliveryMethodDAO;

    public ProductController(Products productsContainer,
            AvailableDeliveryMethods availableDeliveryMethodsContainer) {
        this.productsContainer = productsContainer;
        this.availableDeliveryMethodsContainer = availableDeliveryMethodsContainer;
        this.availableDeliveryMethodDAO = new AvailableDeliveryMethodDAO();
    }

    public void createProduct(String name, String priceText, String description, boolean isDeliveryAvailable) {
        try {
            BigDecimal price = new BigDecimal(priceText);

            if (name.isEmpty() || price.compareTo(BigDecimal.ZERO) <= 0 || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка: наличие пустых полей или некорректная цена.");
                return;
            }

            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setDescription(description);
            newProduct.setDeliveryAvailable(isDeliveryAvailable);
            productsContainer.create(newProduct);

            showAlert(Alert.AlertType.INFORMATION, "Новый товар успешно создан.");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Цена должна быть числом. Например: 123.45");
        }
    }

    public void updateProduct(String idText, String name, String priceText, String description,
            boolean isDeliveryAvailable) {
        try {
            if (idText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Для обновления товара необходимо указать его ID.");
                return;
            }
            int id = Integer.parseInt(idText);
            BigDecimal price = new BigDecimal(priceText);

            if (name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Название товара не может быть пустым.");
                return;
            }

            Product productToUpdate = productsContainer.findById(id);

            if (productToUpdate != null) {
                boolean oldDeliveryStatus = productToUpdate.isDeliveryAvailable();

                productToUpdate.setName(name);
                productToUpdate.setPrice(price);
                productToUpdate.setDescription(description);
                productToUpdate.setDeliveryAvailable(isDeliveryAvailable);
                productsContainer.update(productToUpdate);

                if (oldDeliveryStatus && !isDeliveryAvailable) {
                    try {
                        availableDeliveryMethodDAO.deleteByProductId(id);
                    } catch (Exception e) {
                        showAlert(Alert.AlertType.ERROR, "Не удалось удалить доступные методы доставки так как они используются в сделке");
                    }
                    availableDeliveryMethodsContainer.loadAll();
                }

                showAlert(Alert.AlertType.INFORMATION, "Товар успешно обновлен.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Товар с ID  не найден.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID и Цена должны быть числами. Например: 123.45");
        }
    }

    public void deleteProduct(String idText) {
        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Введите ID товара для удаления");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            Product productToDelete = productsContainer.findById(id);

            if (productToDelete != null) {
                boolean success = productsContainer.delete(productToDelete);
                if (success) {
                    availableDeliveryMethodsContainer.loadAll();
                    showAlert(Alert.AlertType.INFORMATION, "Товар успешно удален.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Не удалось удалить товар так как он используется в сделке");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Товар не найден.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID должен быть числом.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}