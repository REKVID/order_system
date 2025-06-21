package com.ordersystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ordersystem.App;
import com.ordersystem.containers.ClientChoices;
import com.ordersystem.containers.Products;
import com.ordersystem.dao.AvailableDeliveryMethodDAO;
import com.ordersystem.dao.ClientDAO;
import com.ordersystem.dao.DeliveryMethodDAO;
import com.ordersystem.dao.DocumentDAO;
import com.ordersystem.dao.UserDAO;
import com.ordersystem.model.AvailableDeliveryMethod;
import com.ordersystem.model.Client;
import com.ordersystem.model.ClientChoice;
import com.ordersystem.model.Document;
import com.ordersystem.model.Product;
import com.ordersystem.model.User;
import com.ordersystem.view.ClientMainView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class ClientMainController {
    private final ClientMainView view;
    private final Integer clientId;
    private final DocumentDAO documentDAO;
    private final ClientDAO clientDAO;
    private final DeliveryMethodDAO deliveryMethodDAO;
    private final AvailableDeliveryMethodDAO availableDeliveryMethodDAO;
    private final UserDAO userDAO;
    private final List<ClientChoice> shoppingCart;

    public ClientMainController(Integer clientId) {
        this.clientId = clientId;
        this.documentDAO = new DocumentDAO();
        this.clientDAO = new ClientDAO();
        this.deliveryMethodDAO = new DeliveryMethodDAO();
        this.availableDeliveryMethodDAO = new AvailableDeliveryMethodDAO();
        this.userDAO = new UserDAO();
        this.view = new ClientMainView();
        this.shoppingCart = new ArrayList<>();

        setupEventHandlers();
        view.catalogTableView.setItems(Products.getInstance().getProductsList());
    }

    private void setupEventHandlers() {
        view.profileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.showProfile();
                loadProfileData();
            }
        });

        view.catalogButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.showCatalog();
            }
        });

        view.saveProfileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveProfileData();
            }
        });

        view.placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                placeOrder();
            }
        });

        view.logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                App.loadLoginWindow();
            }
        });

        view.addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String idText = view.productIdField.getText();

                try {
                    int id = Integer.parseInt(idText);
                    Product selectedProduct = null;
                    for (Product p : Products.getInstance().getProductsList()) {
                        if (p.getId() == id) {
                            selectedProduct = p;
                            break;
                        }
                    }

                    if (selectedProduct != null) {
                        showAddToCartDialog(selectedProduct);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Товар не найден.");
                    }
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "ID товара должен быть числом.");
                }
            }
        });
    }

    private void showAddToCartDialog(Product product) {
        Dialog<ClientChoice> dialog = new Dialog<>();
        dialog.setHeaderText("Выбор количества и способа доставки");

        ButtonType saveButtonType = new ButtonType("Сохранить", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField quantityField = new TextField("1");
        quantityField.setPromptText("Количество");

        List<AvailableDeliveryMethod> availableMethods = availableDeliveryMethodDAO.findByProductId(product.getId());

        ComboBox<AvailableDeliveryMethod> deliveryComboBox = new ComboBox<>(
                FXCollections.observableArrayList(availableMethods));
        if (!availableMethods.isEmpty()) {
            deliveryComboBox.getSelectionModel().selectFirst();
        }

        grid.add(new Label("Количество:"), 0, 0);
        grid.add(quantityField, 1, 0);
        grid.add(new Label("Способ доставки:"), 0, 1);
        grid.add(deliveryComboBox, 1, 1);

        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(availableMethods.isEmpty());

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, ClientChoice>() {
            @Override
            public ClientChoice call(ButtonType dialogButton) {
                if (dialogButton == saveButtonType) {
                    return createClientChoiceFromDialog(
                            quantityField.getText(),
                            deliveryComboBox.getValue(),
                            product);
                }
                return null;
            }
        });

        Optional<ClientChoice> result = dialog.showAndWait();

        if (result.isPresent()) {
            ClientChoice choice = result.get();
            shoppingCart.add(choice);
            showAlert(Alert.AlertType.INFORMATION, "Товар '" + product.getName() + "' добавлен в корзину.");
        }
    }

    private ClientChoice createClientChoiceFromDialog(String quantityText, AvailableDeliveryMethod selectedDelivery,Product product) {
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert(Alert.AlertType.ERROR, "Количество должно быть положительным числом");
                return null;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Некорректное количество. Введите целое число");
            return null;
        }

        if (selectedDelivery == null) {
            showAlert(Alert.AlertType.ERROR, "Необходимо выбрать способ доставки.");
            return null;
        }

        ClientChoice choice = new ClientChoice();
        choice.setProductId(product.getId());
        choice.setQuantity(quantity);
        choice.setDeliveryMethodId(selectedDelivery.getDeliveryMethodId());
        return choice;
    }

    private void placeOrder() {
        Client client = clientDAO.findById(clientId);
        if (client.getName() == null || client.getName().trim().isEmpty() ||
                client.getPhone() == null || client.getPhone().trim().isEmpty() ||
                client.getAddress() == null || client.getAddress().trim().isEmpty() ||
                client.getContactPerson() == null || client.getContactPerson().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Заполните все поля профиля перед оформлением заказа.");
            return;
        }

        if (shoppingCart.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Ваша корзина пуста. Добавьте товары для оформления заказа.");
            return;
        }

        Document newDocument = new Document();
        newDocument.setClientId(clientId);
        newDocument.setDate(new java.sql.Date(System.currentTimeMillis()));
        int documentId = documentDAO.create(newDocument);

        if (documentId != -1) {
            ClientChoices clientChoices = new ClientChoices(documentId);
            for (ClientChoice choice : shoppingCart) {
                choice.setDocumentId(documentId);
                clientChoices.create(choice);
            }
            showAlert(Alert.AlertType.INFORMATION, "Ваш заказ успешно оформлен!");
            shoppingCart.clear(); // Очищаем корзину после успешного заказа
        } else {
            showAlert(Alert.AlertType.ERROR, "Не удалось создать документ заказа.");
        }
    }

    private void loadProfileData() {
        Client client = clientDAO.findById(clientId);
        User user = userDAO.findByClientId(clientId);

        view.loginField.setText(user.getUsername());
        view.passwordField.setText(user.getPassword());

        view.nameField.setText(client.getName());
        view.phoneField.setText(client.getPhone());
        view.addressField.setText(client.getAddress());
        view.contactPersonField.setText(client.getContactPerson());
    }

    private void saveProfileData() {
        Client client = clientDAO.findById(clientId);
        client.setName(view.nameField.getText());
        client.setPhone(view.phoneField.getText());
        client.setAddress(view.addressField.getText());
        client.setContactPerson(view.contactPersonField.getText());
        clientDAO.update(client);

        User user = userDAO.findByClientId(clientId);
        if (user != null) {
            boolean userIsModified = false;
            String newLogin = view.loginField.getText();
            String newPassword = view.passwordField.getText();

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
