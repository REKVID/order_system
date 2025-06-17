package com.ordersystem.controller;

import com.ordersystem.dao.ClientDAO;
import com.ordersystem.dao.UserDAO;
import com.ordersystem.model.Client;
import com.ordersystem.model.User;
import com.ordersystem.view.LoginView;

import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class LoginController {
    private LoginView view;

    public LoginController() {
        view = new LoginView();
        view.loginClientButton.setOnAction(event -> handleLoginClientButton());
        view.registerButton.setOnAction(event -> handleRegisterButton());
        view.loginAdminButton.setOnAction(event -> handleLoginAdminButton());
    }

    public Parent getView() {
        return view.getView();
    }

    private void handleLoginClientButton() {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();
        System.out.println("Попытка входа клиента: " + username + "/" + password);
        UserDAO userDAO = new UserDAO();
        if (userDAO.authenticateUser(username, password)) {

            // TODO меню клиента

        } else {
            showAlert(Alert.AlertType.ERROR, "Неверное имя пользователя или пароль.");
        }
    }

    private void handleRegisterButton() {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();
        System.out.println("Нажата кнопка регистрации для пользователя: " + username);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Имя пользователя и пароль не могут быть пустыми.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        ClientDAO clientDAO = new ClientDAO();
        int clientRoleId = 1;

        Client newClient = new Client();

        try {
            clientDAO.create(newClient);
            Integer newClientId = newClient.getId();

            User newUser = new User(0, username, password, clientRoleId, newClientId);
            userDAO.create(newUser);

            showAlert(Alert.AlertType.INFORMATION, "Пользователь успешно зарегистрирован!");
            view.usernameField.clear();
            view.passwordField.clear();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка регистрации");
            e.printStackTrace();
        }
    }

    private void handleLoginAdminButton() {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();
        System.out.println("Попытка входа администратора: " + username + "/" + password);

        // TODO меню администратора
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
