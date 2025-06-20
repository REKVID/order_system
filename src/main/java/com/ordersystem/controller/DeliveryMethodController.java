package com.ordersystem.controller;

import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.model.DeliveryMethod;
import com.ordersystem.view.EmployeeMainView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class DeliveryMethodController {

    private final EmployeeMainView view;
    private final DeliveryMethods deliveryMethodsContainer;

    public DeliveryMethodController(EmployeeMainView view, DeliveryMethods deliveryMethodsContainer) {
        this.view = view;
        this.deliveryMethodsContainer = deliveryMethodsContainer;
        view.deliveryMethodsTableView.setItems(deliveryMethodsContainer.getDeliveryMethods());
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.addDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createDeliveryMethod();
            }
        });
        view.saveDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateDeliveryMethod();
            }
        });
        view.deleteDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteDeliveryMethod();
            }
        });
    }

    private void clearDeliveryMethodForm() {
        view.deliveryMethodIdField.clear();
        view.deliveryMethodNameField.clear();
    }

    private void createDeliveryMethod() {
        if (!view.deliveryMethodIdField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "ID должен быть пустым для нового способа доставки.");
            return;
        }
        String name = view.deliveryMethodNameField.getText();
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Название способа доставки не может быть пустым.");
            return;
        }

        DeliveryMethod newMethod = new DeliveryMethod();
        newMethod.setName(name);
        deliveryMethodsContainer.create(newMethod);
        showAlert(Alert.AlertType.INFORMATION, "Новый способ доставки создан.");
        clearDeliveryMethodForm();
    }

    private void updateDeliveryMethod() {
        String idText = view.deliveryMethodIdField.getText();
        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Для обновления необходимо указать ID.");
            return;
        }

        String name = view.deliveryMethodNameField.getText();
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Название способа доставки не может быть пустым.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            DeliveryMethod methodToUpdate = null;
            for (DeliveryMethod m : deliveryMethodsContainer.getDeliveryMethods()) {
                if (m.getId() == id) {
                    methodToUpdate = m;
                    break;
                }
            }

            if (methodToUpdate != null) {
                methodToUpdate.setName(name);
                deliveryMethodsContainer.update(methodToUpdate);
                showAlert(Alert.AlertType.INFORMATION, "Способ доставки обновлен.");
                clearDeliveryMethodForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Способ доставки с ID " + id + " не найден.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID должен быть числом.");
        }
    }

    private void deleteDeliveryMethod() {
        String idText = view.deliveryMethodIdField.getText();
        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Пожалуйста, введите ID для удаления.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            DeliveryMethod methodToDelete = null;
            for (DeliveryMethod m : deliveryMethodsContainer.getDeliveryMethods()) {
                if (m.getId() == id) {
                    methodToDelete = m;
                    break;
                }
            }

            if (methodToDelete != null) {
                deliveryMethodsContainer.delete(methodToDelete);
                showAlert(Alert.AlertType.INFORMATION, "Способ доставки удален.");
                clearDeliveryMethodForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Способ доставки с ID " + id + " не найден.");
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