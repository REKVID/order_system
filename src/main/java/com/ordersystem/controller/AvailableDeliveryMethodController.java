package com.ordersystem.controller;

import java.math.BigDecimal;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.model.AvailableDeliveryMethod;
import com.ordersystem.view.EmployeeMainView;

import javafx.scene.control.Alert;

public class AvailableDeliveryMethodController {

    private final EmployeeMainView view;
    private final AvailableDeliveryMethods availableDeliveryMethodsContainer;

    public AvailableDeliveryMethodController(EmployeeMainView view,
            AvailableDeliveryMethods availableDeliveryMethodsContainer) {
        this.view = view;
        this.availableDeliveryMethodsContainer = availableDeliveryMethodsContainer;
        view.availableDeliveryMethodsTableView
                .setItems(availableDeliveryMethodsContainer.getAvailableDeliveryMethodsList());
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.addAvailableDeliveryMethodButton.setOnAction(e -> createAvailableDeliveryMethod());
        view.saveAvailableDeliveryMethodButton.setOnAction(e -> updateAvailableDeliveryMethod());
        view.deleteAvailableDeliveryMethodButton.setOnAction(e -> deleteAvailableDeliveryMethod());
    }

    private void clearForm() {
        view.formProductIdField.clear();
        view.availableDeliveryMethodIdField.clear();
        view.availableDeliveryCostField.clear();
    }

    private void createAvailableDeliveryMethod() {
        try {
            int productId = Integer.parseInt(view.formProductIdField.getText());
            int deliveryMethodId = Integer.parseInt(view.availableDeliveryMethodIdField.getText());
            BigDecimal deliveryCost = new BigDecimal(view.availableDeliveryCostField.getText());

            AvailableDeliveryMethod newMethod = new AvailableDeliveryMethod(productId, deliveryMethodId, deliveryCost);
            availableDeliveryMethodsContainer.create(newMethod);

            showAlert(Alert.AlertType.INFORMATION, "Новый доступный способ доставки успешно создан.");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID и стоимость должны быть числами.");
        }
    }

    private void updateAvailableDeliveryMethod() {
        try {
            int productId = Integer.parseInt(view.formProductIdField.getText());
            int deliveryMethodId = Integer.parseInt(view.availableDeliveryMethodIdField.getText());
            BigDecimal deliveryCost = new BigDecimal(view.availableDeliveryCostField.getText());

            AvailableDeliveryMethod methodToUpdate = new AvailableDeliveryMethod(productId, deliveryMethodId,
                    deliveryCost);
            availableDeliveryMethodsContainer.update(methodToUpdate);

            showAlert(Alert.AlertType.INFORMATION, "Способ доставки обновлен.");
            clearForm();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID и стоимость должны быть числами.");
        }
    }

    private void deleteAvailableDeliveryMethod() {
        try {
            int productId = Integer.parseInt(view.formProductIdField.getText());
            int deliveryMethodId = Integer.parseInt(view.availableDeliveryMethodIdField.getText());

            AvailableDeliveryMethod methodToDelete = new AvailableDeliveryMethod(productId, deliveryMethodId, null);
            availableDeliveryMethodsContainer.delete(methodToDelete);

            showAlert(Alert.AlertType.INFORMATION, "Способ доставки удален.");
            clearForm();
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