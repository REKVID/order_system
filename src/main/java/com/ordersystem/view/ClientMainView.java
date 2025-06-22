package com.ordersystem.view;

import java.math.BigDecimal;

import com.ordersystem.controller.ClientMainController;
import com.ordersystem.model.Product;
import com.ordersystem.model.Client;
import com.ordersystem.model.User;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ClientMainView {
    private BorderPane view;
    private ClientMainController controller;

    public Button profileButton;
    public Button catalogButton;
    public Button saveProfileButton;
    public Button placeOrderButton;
    public Button addToCartButton;
    public TextField productIdField;
    public TextField quantityField;
    public TextField deliveryMethodField;
    public Button logoutButton;

    private Node profileView;
    private Node catalogView;

    public TextField nameField;
    public TextField phoneField;
    public TextField addressField;
    public TextField contactPersonField;
    public TableView<Product> catalogTableView;
    public TextField loginField;
    public PasswordField passwordField;

    public ClientMainView(ClientMainController controller) {
        this.controller = controller;
        view = new BorderPane();

        profileButton = new Button("Профиль");
        ViewBaseSettings.styleNavButton(profileButton, 180, 50);
        catalogButton = new Button("Каталог товаров");
        ViewBaseSettings.styleNavButton(catalogButton, 180, 50);

        logoutButton = new Button("Выход");
        ViewBaseSettings.styleNavButton(logoutButton, 180, 50);

        VBox navigationBox = new VBox(20, catalogButton, profileButton, logoutButton);
        navigationBox.setPadding(new Insets(30, 20, 30, 20));
        navigationBox.setAlignment(Pos.TOP_CENTER);
        navigationBox.setStyle("-fx-background-color:rgba(107, 121, 136, 0.52);");
        navigationBox.setPrefWidth(220);
        view.setLeft(navigationBox);

        profileView = createProfileView();
        catalogView = createCatalogView();
        view.setCenter(catalogView);
        setupEventHandlers();
    }

    private Node createCatalogView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Каталог товаров", 20);

        catalogTableView = new TableView<>();
        setupCatalogTableView();

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER);
        productIdField = new TextField();
        productIdField.setPromptText("Введите название товара");

        quantityField = new TextField();
        quantityField.setPromptText("Количество");

        deliveryMethodField = new TextField();
        deliveryMethodField.setPromptText("Метод доставки");

        addToCartButton = new Button("Добавить в сделку");
        actionBox.getChildren().addAll(productIdField, quantityField, deliveryMethodField, addToCartButton);

        placeOrderButton = new Button("Оформить заказ");
        ViewBaseSettings.stylePrimaryButton(placeOrderButton);
        VBox.setMargin(placeOrderButton, new Insets(20, 0, 0, 0));

        container.getChildren().addAll(catalogTableView, actionBox, placeOrderButton);

        return container;
    }

    private void setupCatalogTableView() {
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, BigDecimal> priceColumn = new TableColumn<>("Цена");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Описание");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        catalogTableView.getColumns().clear();
        catalogTableView.getColumns().addAll(idColumn, nameColumn, priceColumn, descriptionColumn);
    }

    private Node createProfileView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Профиль клиента", 30);

        loginField = new TextField();
        ViewBaseSettings.styleTextField(loginField, "Логин");
        passwordField = new PasswordField();
        ViewBaseSettings.styleTextField(passwordField, "Новый пароль");

        nameField = new TextField();
        ViewBaseSettings.styleTextField(nameField, "Имя или название организации");
        phoneField = new TextField();
        ViewBaseSettings.styleTextField(phoneField, "Контактный телефон");
        addressField = new TextField();
        ViewBaseSettings.styleTextField(addressField, "Адрес доставки");
        contactPersonField = new TextField();
        ViewBaseSettings.styleTextField(contactPersonField, "Контактное лицо");

        saveProfileButton = new Button("Сохранить изменения");
        ViewBaseSettings.stylePrimaryButton(saveProfileButton);

        container.getChildren().addAll(loginField, passwordField, nameField,
                phoneField, addressField, contactPersonField, saveProfileButton);
        return container;
    }

    public void fillProfileData(User user, Client client) {

        loginField.setText(user.getUsername());
        passwordField.setText(user.getPassword());

        nameField.setText(client.getName());
        phoneField.setText(client.getPhone());
        addressField.setText(client.getAddress());
        contactPersonField.setText(client.getContactPerson());

    }

    private void setupEventHandlers() {
        profileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowProfile();
            }
        });

        catalogButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowCatalog();
            }
        });

        saveProfileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleSaveProfile(
                        loginField.getText(),
                        passwordField.getText(),
                        nameField.getText(),
                        phoneField.getText(),
                        addressField.getText(),
                        contactPersonField.getText());
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleMakeDeal();
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleLogout();
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleAddToDeal(
                        productIdField.getText(),
                        quantityField.getText(),
                        deliveryMethodField.getText());
            }
        });
    }

    public void showProfile() {
        view.setCenter(profileView);
    }

    public void showCatalog() {
        view.setCenter(catalogView);
    }

    public Parent getView() {
        return view;
    }
}
