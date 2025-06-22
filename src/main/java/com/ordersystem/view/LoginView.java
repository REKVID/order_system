package com.ordersystem.view;

import com.ordersystem.controller.LoginController;
import com.ordersystem.model.Role;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LoginView {
    private VBox root;
    private LoginController controller;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Button registerButton;
    public ComboBox<Role> roleComboBox;

    public LoginView(LoginController controller) {
        this.controller = controller;

        Label titleLabel = new Label("Вход");
        titleLabel.setFont(new Font(48.0));
        VBox.setMargin(titleLabel, new Insets(0, 0, 20.0, 0));

        usernameField = new TextField();
        ViewBaseSettings.styleTextField(usernameField, "Логин");

        passwordField = new PasswordField();
        ViewBaseSettings.styleTextField(passwordField, "Пароль");

        loginButton = new Button("Войти");
        ViewBaseSettings.stylePrimaryButton(loginButton);
        loginButton.setMinWidth(200);

        registerButton = new Button("Зарегистрироваться");
        ViewBaseSettings.stylePrimaryButton(registerButton);
        registerButton.setMinWidth(200);
        registerButton.setStyle(
                "-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 10; -fx-font-size: 16px;");

        HBox actionButtons = new HBox(20, loginButton, registerButton);
        actionButtons.setAlignment(Pos.CENTER);

        roleComboBox = new ComboBox<>();
        roleComboBox.setPromptText("Выберите роль");
        roleComboBox.setMinWidth(200);

        root = new VBox(30, titleLabel, usernameField, passwordField, actionButtons, roleComboBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40.0));
        root.setStyle("-fx-background-color: white;");

        setupEventHandlers();
        controller.loadRoles(roleComboBox);
    }

    private void setupEventHandlers() {
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleLoginButton(
                        usernameField.getText(),
                        passwordField.getText());
            }
        });

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleRegisterButton(
                        usernameField.getText(),
                        passwordField.getText(),
                        roleComboBox.getValue());
            }
        });
    }

    public VBox getView() {
        return root;
    }
}
