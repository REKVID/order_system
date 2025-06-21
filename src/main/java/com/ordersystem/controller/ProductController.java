package com.ordersystem.controller;

import java.math.BigDecimal;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.Products;
import com.ordersystem.dao.AvailableDeliveryMethodDAO;
import com.ordersystem.model.Product;
import com.ordersystem.view.EmployeeMainView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class ProductController {

    private final EmployeeMainView view;
    private final Products productsContainer;
    private final AvailableDeliveryMethods availableDeliveryMethodsContainer;

    public ProductController(EmployeeMainView view, Products productsContainer,
            AvailableDeliveryMethods availableDeliveryMethodsContainer) {
        this.view = view;
        this.productsContainer = productsContainer;
        this.availableDeliveryMethodsContainer = availableDeliveryMethodsContainer;
        view.productsTableView.setItems(productsContainer.getProductsList());
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.addProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createProduct();
            }
        });
        view.saveProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateProduct();
            }
        });
        view.deleteProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteProduct();
            }
        });
    }

    private void clearProductForm() {
        view.productIdField.clear();
        view.productNameField.clear();
        view.productPriceField.clear();
        view.productDescriptionArea.clear();
        view.productDeliveryAvailableCheckBox.setSelected(false);
    }

    private void createProduct() {
        try {
            if (!view.productIdField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "ID должно быть пустым");
                return;
            }

            String name = view.productNameField.getText();
            BigDecimal price = new BigDecimal(view.productPriceField.getText());
            String description = view.productDescriptionArea.getText();
            boolean isDeliveryAvailable = view.productDeliveryAvailableCheckBox.isSelected();

            if (name.isEmpty() || price.compareTo(BigDecimal.ZERO) <= 0 || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка: наличие пустых полей");
                return;
            }

            Product newProduct = new Product();
            newProduct.setName(name);
            newProduct.setPrice(price);
            newProduct.setDescription(description);
            newProduct.setDeliveryAvailable(isDeliveryAvailable);
            productsContainer.create(newProduct);

            showAlert(Alert.AlertType.INFORMATION, "Новый товар успешно создан.");
            clearProductForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Цена должна быть числом. Например: 123.45");
        }
    }

    private void updateProduct() {
        try {
            String idText = view.productIdField.getText();
            if (idText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Для обновления товара необходимо указать его ID.");
                return;
            }
            int id = Integer.parseInt(idText);

            String name = view.productNameField.getText();
            BigDecimal price = new BigDecimal(view.productPriceField.getText());
            String description = view.productDescriptionArea.getText();
            boolean isDeliveryAvailable = view.productDeliveryAvailableCheckBox.isSelected();

            if (name.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Название товара не может быть пустым.");
                return;
            }

            Product productToUpdate = null;
            for (Product p : productsContainer.getProductsList()) {
                if (p.getId() == id) {
                    productToUpdate = p;
                    break;
                }
            }

            if (productToUpdate != null) {
                boolean oldDeliveryStatus = productToUpdate.isDeliveryAvailable();

                productToUpdate.setName(name);
                productToUpdate.setPrice(price);
                productToUpdate.setDescription(description);
                productToUpdate.setDeliveryAvailable(isDeliveryAvailable);
                productsContainer.update(productToUpdate);

                if (oldDeliveryStatus && !isDeliveryAvailable) {
                    new AvailableDeliveryMethodDAO().deleteByProductId(id);
                    availableDeliveryMethodsContainer.loadAll();
                }

                showAlert(Alert.AlertType.INFORMATION, "Товар успешно обновлен.");
                clearProductForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Товар с ID " + id + " не найден.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID и Цена должны быть числами. Например: 123.45");
        }
    }

    private void deleteProduct() {
        String idText = view.productIdField.getText();
        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Введите ID товара для удаления");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            Product productToDelete = null;
            for (Product p : productsContainer.getProductsList()) {
                if (p.getId() == id) {
                    productToDelete = p;
                    break;
                }
            }

            if (productToDelete != null) {

                productsContainer.delete(productToDelete);
                availableDeliveryMethodsContainer.loadAll();
                showAlert(Alert.AlertType.INFORMATION, "Товар успешно удален.");
                clearProductForm();

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