package com.ordersystem.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class LoginView {
    public VBox root;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    public Button registerButton;
    public CheckBox employeeCheckBox;

    public LoginView() {
        Label titleLabel = new Label("Вход");
        titleLabel.setFont(new Font(48.0));
        VBox.setMargin(titleLabel, new Insets(0, 0, 20.0, 0));

        usernameField = new TextField();
        usernameField.setPromptText("Логин");
        usernameField.setMaxWidth(500);
        usernameField.setPrefHeight(50);

        passwordField = new PasswordField();
        passwordField.setPromptText("Пароль");
        passwordField.setMaxWidth(500);
        passwordField.setPrefHeight(50);

        loginButton = new Button("Войти");
        loginButton.setMinSize(200, 60);
        loginButton.setStyle(
                "-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;");

        registerButton = new Button("Зарегистрироваться");
        registerButton.setMinSize(200, 60);
        registerButton.setStyle(
                "-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 10; -fx-border-radius: 10;");

        HBox actionButtons = new HBox(20, loginButton, registerButton);
        actionButtons.setAlignment(Pos.CENTER);

        employeeCheckBox = new CheckBox("Я работник компании");

        root = new VBox(30, titleLabel, usernameField, passwordField, actionButtons, employeeCheckBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40.0));
        root.setStyle("-fx-background-color: white;");
    }

    public Parent getView() {
        return root;
    }
}
