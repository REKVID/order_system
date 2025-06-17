package com.ordersystem.controller;

import com.ordersystem.App;
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
        view.loginButton.setOnAction(event -> handleLoginButton());
        view.registerButton.setOnAction(event -> handleRegisterButton());
    }

    public Parent getView() {
        return view.getView();
    }

    private void handleLoginButton() {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();
        System.out.println("Попытка входа: " + username + "/" + password);
        UserDAO userDAO = new UserDAO();
        if (userDAO.authenticateUser(username, password)) {

            User user = userDAO.findByUsername(username);
            if (user.getRoleId() == 1) {

                // TODO: меню клиента
            } else if (user.getRoleId() == 2) {
                // TODO: меню работника

            }

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
        boolean isEmployee = view.employeeCheckBox.isSelected();
        int roleId = isEmployee ? 2 : 1;

        Integer newClientId = null;
        if (!isEmployee) {
            Client newClient = new Client();
            newClient.setName(username);
            clientDAO.create(newClient);
            newClientId = newClient.getId();
        }

        try {
            User newUser = new User(0, username, password, roleId, newClientId);
            userDAO.create(newUser);

            showAlert(Alert.AlertType.INFORMATION, "Пользователь успешно зарегистрирован!");
            view.usernameField.clear();
            view.passwordField.clear();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка регистрации");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
