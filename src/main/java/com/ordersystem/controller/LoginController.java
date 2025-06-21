package com.ordersystem.controller;

import com.ordersystem.App;
import com.ordersystem.dao.ClientDAO;
import com.ordersystem.dao.UserDAO;
import com.ordersystem.model.Client;
import com.ordersystem.model.User;
import com.ordersystem.view.LoginView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

public class LoginController {
    private LoginView view;

    public LoginController() {
        view = new LoginView();
        view.loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLoginButton();
            }
        });
        view.registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRegisterButton();
            }
        });
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
                ClientMainController clientMainController = new ClientMainController(user.getClientId());
                App.setRoot(clientMainController.getView(), "Главное меню клиента");
            } else if (user.getRoleId() == 2) {
                EmployeeMainController employeeMainController = new EmployeeMainController();
                App.setRoot(employeeMainController.getView(), "Главное меню сотрудника");
            }

        } else {
            showAlert(Alert.AlertType.ERROR, "Неверное имя пользователя или пароль.");
        }
    }

    private void handleRegisterButton() {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();

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

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка регистрации");
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
