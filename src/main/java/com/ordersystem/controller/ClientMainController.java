package com.ordersystem.controller;

import java.util.ArrayList;
import java.util.List;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.containers.Products;
import com.ordersystem.dao.AvailableDeliveryMethodDAO;
import com.ordersystem.dao.ClientChoiceDAO;
import com.ordersystem.dao.ClientDAO;
import com.ordersystem.dao.DeliveryMethodDAO;
import com.ordersystem.dao.DocumentDAO;
import com.ordersystem.dao.UserDAO;
import com.ordersystem.model.AvailableDeliveryMethod;
import com.ordersystem.model.Client;
import com.ordersystem.model.ClientChoice;
import com.ordersystem.model.DeliveryMethod;
import com.ordersystem.model.Document;
import com.ordersystem.model.Product;
import com.ordersystem.model.User;
import com.ordersystem.view.ClientMainView;
import com.ordersystem.view.LoginView;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ClientMainController {
    private final ClientMainView view;
    private final Stage stage;
    private final Integer clientId;
    private final DocumentDAO documentDAO;
    private final ClientDAO clientDAO;
    private final DeliveryMethodDAO deliveryMethodDAO;
    private final AvailableDeliveryMethodDAO availableDeliveryMethodDAO;
    private final UserDAO userDAO;
    private final List<ClientChoice> DealTempContainer;

    public ClientMainController(Stage stage, Integer clientId) {
        this.stage = stage;
        this.clientId = clientId;
        this.documentDAO = new DocumentDAO();
        this.clientDAO = new ClientDAO();
        this.deliveryMethodDAO = new DeliveryMethodDAO();
        this.availableDeliveryMethodDAO = new AvailableDeliveryMethodDAO();
        this.userDAO = new UserDAO();
        this.view = new ClientMainView(this);
        this.DealTempContainer = new ArrayList<>();
        view.catalogTableView.setItems(Products.getInstance().getProductsList());
    }

    public void handleShowProfile() {
        view.showProfile();
        User user = userDAO.findByClientId(clientId);
        Client client = clientDAO.findById(clientId);
        view.fillProfileData(user, client);
    }

    public void handleShowCatalog() {
        view.showCatalog();
    }

    public void handleMakeDeal() {
        makeDeal();
    }

    public void handleLogout() {
        LoginController loginController = new LoginController(this.stage);
        LoginView loginView = new LoginView(loginController);
        this.stage.getScene().setRoot(loginView.getView());
        this.stage.setTitle("Система заказов - Вход");
    }

    public void handleAddToDeal(String productName, String quantityText, String deliveryMethodName) {
        try {
            int quantity = Integer.parseInt(quantityText);
            if (productName.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Название товара не может быть пустым.");
                return;
            }

            if (quantity <= 0) {
                showAlert(Alert.AlertType.ERROR, "Количество должно быть положительным числом.");
                return;
            }

            Product selectedProduct = null;
            for (Product p : Products.getInstance().getProductsList()) {
                if (p.getName().equalsIgnoreCase(productName)) {
                    selectedProduct = p;
                    break;
                }
            }
            if (selectedProduct == null) {
                showAlert(Alert.AlertType.ERROR, "Товар с названием '" + productName + "' не найден.");
                return;
            }

            DeliveryMethod selectedDeliveryMethod = null;
            for (DeliveryMethod dm : DeliveryMethods.getInstance().getDeliveryMethods()) {
                if (dm.getName().equalsIgnoreCase(deliveryMethodName)) {
                    selectedDeliveryMethod = dm;
                    break;
                }
            }
            if (selectedDeliveryMethod == null) {
                showAlert(Alert.AlertType.ERROR, "Метод доставки не найден.");
                return;
            }

            boolean isMethodAvailable = false;
            for (AvailableDeliveryMethod adm : AvailableDeliveryMethods.getInstance()
                    .getAvailableDeliveryMethodsList()) {
                if (adm.getProductId() == selectedProduct.getId()
                        && adm.getDeliveryMethodId() == selectedDeliveryMethod.getId()) {
                    isMethodAvailable = true;
                    break;
                }
            }

            if (!isMethodAvailable) {
                showAlert(Alert.AlertType.ERROR, "Метод доставки не доступен для товара ");
                return;
            }

            for (ClientChoice existingChoice : DealTempContainer) {
                if (existingChoice.getProductId() == selectedProduct.getId()
                        && existingChoice.getDeliveryMethodId() == selectedDeliveryMethod.getId()) {
                    existingChoice.setQuantity(existingChoice.getQuantity() + quantity);
                    showAlert(Alert.AlertType.ERROR, "дублирование обработано");
                    return;
                }
            }

            ClientChoice choice = new ClientChoice();
            choice.setProductId(selectedProduct.getId());
            choice.setQuantity(quantity);
            choice.setDeliveryMethodId(selectedDeliveryMethod.getId());

            DealTempContainer.add(choice);
            showAlert(Alert.AlertType.INFORMATION, "Товар добавлен в сделку.");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Количество должно быть числом.");
        }
    }

    private void makeDeal() {
        Client client = clientDAO.findById(clientId);
        if (client.getName() == null || client.getName().trim().isEmpty() ||
                client.getPhone() == null || client.getPhone().trim().isEmpty() ||
                client.getAddress() == null || client.getAddress().trim().isEmpty() ||
                client.getContactPerson() == null || client.getContactPerson().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Заполните все поля профиля перед оформлением заказа.");
            return;
        }

        if (DealTempContainer.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Сделка пуста. Добавьте товары для оформления сделки.");
            return;
        }

        Document newDocument = new Document();
        newDocument.setClientId(clientId);
        newDocument.setDate(new java.sql.Date(System.currentTimeMillis()));
        int documentId = documentDAO.create(newDocument);

        if (documentId != -1) {
            ClientChoiceDAO clientChoiceDAO = new ClientChoiceDAO();
            for (ClientChoice choice : DealTempContainer) {
                choice.setDocumentId(documentId);
                clientChoiceDAO.create(choice);
            }
            showAlert(Alert.AlertType.INFORMATION, "Сделка успешно оформлена!");
            DealTempContainer.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Не удалось создать сделку.");
        }
    }

    public void handleSaveProfile(String newLogin, String newPassword, String name, String phone, String address,
            String contactPerson) {
        Client client = clientDAO.findById(clientId);
        client.setName(name);
        client.setPhone(phone);
        client.setAddress(address);
        client.setContactPerson(contactPerson);
        clientDAO.update(client);

        User user = userDAO.findByClientId(clientId);
        if (user != null) {
            boolean userIsModified = false;

            if (newLogin != null && !newLogin.isEmpty() && !newLogin.equals(user.getUsername())) {
                user.setUsername(newLogin);
                userIsModified = true;
            }

            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(newPassword);
                userIsModified = true;
            }

            if (userIsModified) {
                userDAO.update(user);
            }
        }

        showAlert(Alert.AlertType.INFORMATION, "Данные профиля успешно обновлены.");
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Parent getView() {
        return view.getView();
    }
}
