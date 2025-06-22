package com.ordersystem.controller;

import com.ordersystem.App;
import com.ordersystem.dao.ClientDAO;
import com.ordersystem.dao.RoleDAO;
import com.ordersystem.dao.UserDAO;
import com.ordersystem.model.Client;
import com.ordersystem.model.Role;
import com.ordersystem.model.User;
import com.ordersystem.view.LoginView;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.util.StringConverter;

public class LoginController {
    private LoginView view;
    private RoleDAO roleDAO;

    public LoginController() {
        view = new LoginView();
        roleDAO = new RoleDAO();

        loadRoles();

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

    private void loadRoles() {
        view.roleComboBox.setItems(FXCollections.observableArrayList(roleDAO.findAll()));
        view.roleComboBox.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                return role == null ? "" : role.getName();
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });
    }

    public Parent getView() {
        return view.getView();
    }

    private void handleLoginButton() {
        String username = view.usernameField.getText();
        String password = view.passwordField.getText();
        UserDAO userDAO = new UserDAO();
        if (userDAO.authenticateUser(username, password)) {

            User user = userDAO.findByUsername(username);
            Role userRole = roleDAO.findById(user.getRoleId());

            if (userRole != null) {
                String roleName = userRole.getName();
                if (roleName.equalsIgnoreCase("client") && user.getClientId() != null) {
                    ClientMainController clientMainController = new ClientMainController(user.getClientId());
                    App.setRoot(clientMainController.getView(), "Главное меню клиента");
                } else if (roleName.equalsIgnoreCase("employee") && user.getClientId() == null) {
                    EmployeeMainController employeeMainController = new EmployeeMainController();
                    App.setRoot(employeeMainController.getView(), "Главное меню сотрудника");
                } else {
                    showAlert(Alert.AlertType.ERROR, "оибка роли");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Не удалось определить роль пользователя.");
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

        Role selectedRole = view.roleComboBox.getValue();
        if (selectedRole == null) {
            showAlert(Alert.AlertType.ERROR, "Необходимо выбрать роль.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        ClientDAO clientDAO = new ClientDAO();

        Integer newClientId = null;
        if (selectedRole.getName().equalsIgnoreCase("client")) {
            Client newClient = new Client();
            newClient.setName(username);
            clientDAO.create(newClient);
            newClientId = newClient.getId();
        }

        try {
            User newUser = new User(0, username, password, selectedRole.getId(), newClientId);
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
