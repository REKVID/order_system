package com.ordersystem.view;

import java.util.Date;

import com.ordersystem.model.ClientChoice;
import com.ordersystem.model.Document;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ClientMainView {
    private BorderPane view;
    public Button profileButton;
    public Button documentsButton;
    public Button catalogButton;
    public Button saveProfileButton;
    public Button placeOrderButton;
    public Button addToCartButton;
    public TextField documentIdField;
    public Button showDocumentContentButton;
    public TextField productIdField;

    private Node documentsView;
    private Node profileView;
    private Node catalogView;

    public TextField nameField;
    public TextField phoneField;
    public TextField addressField;
    public TextField contactPersonField;
    public TableView<Document> documentsTableView;
    public TableView<ClientChoice> clientChoicesTableView;
    public TableView catalogTableView;
    public TextField loginField;
    public PasswordField passwordField;

    public ClientMainView() {
        view = new BorderPane();

        profileButton = new Button("Профиль");
        ViewBaseSettings.styleNavButton(profileButton, 180, 50);
        documentsButton = new Button("Мои заказы");
        ViewBaseSettings.styleNavButton(documentsButton, 180, 50);
        catalogButton = new Button("Каталог товаров");
        ViewBaseSettings.styleNavButton(catalogButton, 180, 50);

        VBox navigationBox = new VBox(20, catalogButton, documentsButton, profileButton);
        navigationBox.setPadding(new Insets(30, 20, 30, 20));
        navigationBox.setAlignment(Pos.TOP_CENTER);
        navigationBox.setStyle("-fx-background-color:rgba(107, 121, 136, 0.52);");
        navigationBox.setPrefWidth(220);
        view.setLeft(navigationBox);

        documentsView = createDocumentsView();
        profileView = createProfileView();
        catalogView = createCatalogView();
        view.setCenter(catalogView);
    }

    private Node createCatalogView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Каталог товаров", 20);

        catalogTableView = new TableView<>();
        VBox.setVgrow(catalogTableView, Priority.ALWAYS);

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER);
        productIdField = new TextField();
        productIdField.setPromptText("Введите ID товара");
        addToCartButton = new Button("Выбрать параметры и добавить");
        actionBox.getChildren().addAll(productIdField, addToCartButton);

        placeOrderButton = new Button("Оформить заказ");
        ViewBaseSettings.stylePrimaryButton(placeOrderButton);
        VBox.setMargin(placeOrderButton, new Insets(20, 0, 0, 0));

        container.getChildren().addAll(catalogTableView, actionBox, placeOrderButton);

        return container;
    }

    private Node createDocumentsView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Мои заказы", 20);

        documentsTableView = new TableView<>();
        TableColumn<Document, Integer> idColumn = new TableColumn<>("ID Заказа");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Document, Date> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        documentsTableView.getColumns().addAll(idColumn, dateColumn);
        idColumn.prefWidthProperty().bind(documentsTableView.widthProperty().multiply(0.3));
        dateColumn.prefWidthProperty().bind(documentsTableView.widthProperty().multiply(0.7));

        HBox documentActions = new HBox(10);
        documentActions.setAlignment(Pos.CENTER_LEFT);
        documentIdField = new TextField();
        documentIdField.setPromptText("Введите ID заказа");
        showDocumentContentButton = new Button("Показать состав");
        documentActions.getChildren().addAll(documentIdField, showDocumentContentButton);

        Label clientChoicesLabel = new Label("Состав заказа");
        clientChoicesLabel.setFont(new Font(24.0));
        VBox.setMargin(clientChoicesLabel, new Insets(20, 0, 10, 0));

        clientChoicesTableView = new TableView<>();
        TableColumn<com.ordersystem.model.ClientChoice, Integer> productIdColumn = new TableColumn<>("ID Продукта");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<com.ordersystem.model.ClientChoice, Integer> deliveryMethodIdColumn = new TableColumn<>(
                "ID Способа доставки");
        deliveryMethodIdColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryMethodsId"));

        TableColumn<com.ordersystem.model.ClientChoice, Integer> quantityColumn = new TableColumn<>("Количество");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        clientChoicesTableView.getColumns().addAll(productIdColumn, deliveryMethodIdColumn, quantityColumn);
        productIdColumn.prefWidthProperty().bind(clientChoicesTableView.widthProperty().multiply(0.3));
        deliveryMethodIdColumn.prefWidthProperty().bind(clientChoicesTableView.widthProperty().multiply(0.4));
        quantityColumn.prefWidthProperty().bind(clientChoicesTableView.widthProperty().multiply(0.3));

        container.getChildren().addAll(documentsTableView, documentActions, clientChoicesLabel, clientChoicesTableView);
        return container;
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
        ViewBaseSettings.styleTextField(contactPersonField, "Контактное лицо (ФИО)");

        saveProfileButton = new Button("Сохранить изменения");
        ViewBaseSettings.stylePrimaryButton(saveProfileButton);

        container.getChildren().addAll(loginField, passwordField, nameField, phoneField, addressField,
                contactPersonField,
                saveProfileButton);
        return container;
    }

    public void showProfile() {
        view.setCenter(profileView);
    }

    public void showDocuments() {
        view.setCenter(documentsView);
    }

    public void showCatalog() {
        view.setCenter(catalogView);
    }

    public Parent getView() {
        return view;
    }

    public TableView<Document> getTableView() {
        return documentsTableView;
    }

    public TableView<com.ordersystem.model.ClientChoice> getClientChoicesTableView() {
        return clientChoicesTableView;
    }
}
