package com.ordersystem.controller;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.model.DeliveryMethod;

import javafx.scene.control.Alert;

public class DeliveryMethodController {

    private final DeliveryMethods deliveryMethodsContainer;
    private final AvailableDeliveryMethods availableDeliveryMethodsContainer;

    public DeliveryMethodController(DeliveryMethods deliveryMethodsContainer,
            AvailableDeliveryMethods availableDeliveryMethodsContainer) {
        this.deliveryMethodsContainer = deliveryMethodsContainer;
        this.availableDeliveryMethodsContainer = availableDeliveryMethodsContainer;
    }

    public void createDeliveryMethod(String name, String speedDays) {
        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Название способа доставки не может быть пустым.");
            return;
        }

        DeliveryMethod newMethod = new DeliveryMethod();
        newMethod.setName(name);
        newMethod.setSpeedDays(Integer.parseInt(speedDays));
        deliveryMethodsContainer.create(newMethod);
        showAlert(Alert.AlertType.INFORMATION, "Новый способ доставки создан.");
    }

    public void updateDeliveryMethod(String idText, String name, String speedDays) {
        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Для обновления необходимо указать ID.");
            return;
        }

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Название способа доставки не может быть пустым.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            DeliveryMethod methodToUpdate = deliveryMethodsContainer.findById(id);

            if (methodToUpdate != null) {
                methodToUpdate.setName(name);
                methodToUpdate.setSpeedDays(Integer.parseInt(speedDays));
                deliveryMethodsContainer.update(methodToUpdate);
                showAlert(Alert.AlertType.INFORMATION, "Способ доставки обновлен.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Способ доставки не найден.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID должен быть числом.");
        }
    }

    public void deleteDeliveryMethod(String idText) {
        if (idText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Пожалуйста, введите ID для удаления.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            DeliveryMethod methodToDelete = deliveryMethodsContainer.findById(id);

            if (methodToDelete != null) {
                boolean success = deliveryMethodsContainer.delete(methodToDelete);
                if (success) {
                    availableDeliveryMethodsContainer.loadAll();
                    showAlert(Alert.AlertType.INFORMATION, "Способ доставки удален.");
                } else {
                    showAlert(Alert.AlertType.ERROR,
                            "Не удалось удалить способ доставки так как он используется в сделке");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Способ доставки не найден.");
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