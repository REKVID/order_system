package com.ordersystem.controller;

import com.ordersystem.dao.ClientDAO;
import com.ordersystem.dao.RoleDAO;
import com.ordersystem.dao.UserDAO;
import com.ordersystem.model.Client;
import com.ordersystem.model.Role;
import com.ordersystem.model.User;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class LoginController {
    private final Stage stage;
    private final RoleDAO roleDAO;
    private final UserDAO userDAO;
    private final ClientDAO clientDAO;

    public LoginController(Stage stage) {
        this.stage = stage;
        this.roleDAO = new RoleDAO();
        this.userDAO = new UserDAO();
        this.clientDAO = new ClientDAO();
    }

    public void loadRoles(ComboBox<Role> roleComboBox) {
        roleComboBox.setItems(FXCollections.observableArrayList(roleDAO.findAll()));
        roleComboBox.setConverter(new StringConverter<Role>() {
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

    public void handleLoginButton(String username, String password) {
        if (userDAO.authenticateUser(username, password)) {
            User user = userDAO.findByUsername(username);
            Role userRole = roleDAO.findById(user.getRoleId());

            if (userRole != null) {
                String roleName = userRole.getName();
                if (roleName.equalsIgnoreCase("client") && user.getClientId() != null) {
                    ClientMainController clientMainController = new ClientMainController(stage, user.getClientId());
                    stage.getScene().setRoot(clientMainController.getView());
                    stage.setTitle("Главное меню клиента");
                } else if (roleName.equalsIgnoreCase("employee") && user.getClientId() == null) {
                    EmployeeMainController employeeMainController = new EmployeeMainController(stage);
                    stage.getScene().setRoot(employeeMainController.getView());
                    stage.setTitle("Главное меню сотрудника");
                } else {
                    showAlert(Alert.AlertType.ERROR, "неверные данные");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Не удалось определить роль пользователя.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Неверное имя пользователя или пароль.");
        }
    }

    public void handleRegisterButton(String username, String password, Role selectedRole) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Имя пользователя и пароль не могут быть пустыми.");
            return;
        }

        if (selectedRole == null) {
            showAlert(Alert.AlertType.ERROR, "Необходимо выбрать роль.");
            return;
        }

        Integer newClientId = null;
        if (selectedRole.getName().equalsIgnoreCase("client")) {
            Client newClient = new Client();
            newClient.setName(username);
            clientDAO.create(newClient);
            newClientId = newClient.getId();
            clientDAO.update(newClient);
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
