package com.ordersystem.view;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.containers.Documents;
import com.ordersystem.containers.Products;
import com.ordersystem.controller.EmployeeMainController;
import com.ordersystem.model.AvailableDeliveryMethod;
import com.ordersystem.model.ClientChoice;
import com.ordersystem.model.DeliveryMethod;
import com.ordersystem.model.Document;
import com.ordersystem.model.Product;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EmployeeMainView {

    private BorderPane view;
    private EmployeeMainController controller;

    public Button documentsButton;
    public Button productsButton;
    public Button deliveryMethodsButton;
    public Button availableDeliveryMethodsButton;
    public Button logoutButton;

    private Node documentsView;
    private Node productsView;
    private Node deliveryMethodsView;
    private Node availableDeliveryMethodsView;

    public TableView<Document> documentsTableView;
    public TableView<ClientChoice> clientChoicesTableView;
    public TextField documentIdField;
    public Button showDocumentContentButton;

    public TableView<Product> productsTableView;
    public TextField productIdField;
    public TextField productNameField;
    public TextField productPriceField;
    public TextArea productDescriptionArea;
    public CheckBox productDeliveryAvailableCheckBox;
    public Button addProductButton;
    public Button saveProductButton;
    public Button deleteProductButton;

    public TableView<DeliveryMethod> deliveryMethodsTableView;
    public TextField deliveryMethodIdField;
    public TextField deliveryMethodNameField;
    public Button addDeliveryMethodButton;
    public Button saveDeliveryMethodButton;
    public Button deleteDeliveryMethodButton;
    public TextField deliveryMethodSpeedDaysField;

    public TableView<AvailableDeliveryMethod> availableDeliveryMethodsTableView;
    public TextField formProductIdField;
    public TextField availableDeliveryMethodIdField;
    public TextField availableDeliveryCostField;
    public Button addAvailableDeliveryMethodButton;
    public Button saveAvailableDeliveryMethodButton;
    public Button deleteAvailableDeliveryMethodButton;

    public EmployeeMainView(EmployeeMainController controller) {
        this.controller = controller;
        view = new BorderPane();

        documentsButton = new Button("Документы");
        ViewBaseSettings.styleNavButton(documentsButton, 200, 50);
        productsButton = new Button("Товары");
        ViewBaseSettings.styleNavButton(productsButton, 200, 50);
        deliveryMethodsButton = new Button("Способы доставки");
        ViewBaseSettings.styleNavButton(deliveryMethodsButton, 200, 50);
        availableDeliveryMethodsButton = new Button("Доступные способы доставки");
        ViewBaseSettings.styleNavButton(availableDeliveryMethodsButton, 200, 50);

        logoutButton = new Button("Выход");
        ViewBaseSettings.styleNavButton(logoutButton, 200, 50);

        VBox navigationBox = new VBox(20,
                documentsButton, productsButton, deliveryMethodsButton,
                availableDeliveryMethodsButton, logoutButton);

        navigationBox.setPadding(new Insets(30, 20, 30, 20));
        navigationBox.setAlignment(Pos.TOP_CENTER);
        navigationBox.setStyle("-fx-background-color:rgba(107, 121, 136, 0.52);");
        navigationBox.setPrefWidth(220);
        view.setLeft(navigationBox);

        documentsView = createDocumentsView();
        productsView = createProductsView();
        deliveryMethodsView = createDeliveryMethodsView();
        availableDeliveryMethodsView = createAvailableDeliveryMethodsView();

        view.setCenter(documentsView);

        setupEventHandlers();
        setupTableViews();
    }

    private void setupTableViews() {
        documentsTableView.setItems(Documents.getInstance().getDocuments());
        clientChoicesTableView.setItems(Documents.getInstance().getSelectedDocumentChoices());
        productsTableView.setItems(Products.getInstance().getProductsList());
        deliveryMethodsTableView.setItems(DeliveryMethods.getInstance().getDeliveryMethods());
        availableDeliveryMethodsTableView
                .setItems(AvailableDeliveryMethods.getInstance().getAvailableDeliveryMethodsList());
    }

    private void setupEventHandlers() {
        documentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowDocuments();
            }
        });
        productsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowProducts();
            }
        });
        deliveryMethodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowDeliveryMethods();
            }
        });
        availableDeliveryMethodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowAvailableDeliveryMethods();
            }
        });
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleLogout();
            }
        });

        showDocumentContentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleShowDocumentContent(documentIdField.getText());
            }
        });

        addProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleCreateProduct(
                        productNameField.getText(),
                        productPriceField.getText(),
                        productDescriptionArea.getText(),
                        productDeliveryAvailableCheckBox.isSelected());
            }
        });
        saveProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleUpdateProduct(
                        productIdField.getText(),
                        productNameField.getText(),
                        productPriceField.getText(),
                        productDescriptionArea.getText(),
                        productDeliveryAvailableCheckBox.isSelected());
            }
        });
        deleteProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleDeleteProduct(productIdField.getText());
            }
        });

        addDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleCreateDeliveryMethod(deliveryMethodNameField.getText(),
                        deliveryMethodSpeedDaysField.getText());
            }
        });
        saveDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleUpdateDeliveryMethod(
                        deliveryMethodIdField.getText(),
                        deliveryMethodNameField.getText(),
                        deliveryMethodSpeedDaysField.getText());
            }
        });
        deleteDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleDeleteDeliveryMethod(deliveryMethodIdField.getText());
            }
        });

        addAvailableDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleCreateAvailableDeliveryMethod(
                        formProductIdField.getText(),
                        availableDeliveryMethodIdField.getText(),
                        availableDeliveryCostField.getText());
            }
        });
        saveAvailableDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleUpdateAvailableDeliveryMethod(
                        formProductIdField.getText(),
                        availableDeliveryMethodIdField.getText(),
                        availableDeliveryCostField.getText());
            }
        });
        deleteAvailableDeliveryMethodButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleDeleteAvailableDeliveryMethod(
                        formProductIdField.getText(),
                        availableDeliveryMethodIdField.getText());
            }
        });
    }

    private Node createDocumentsView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Все заказы", 20);

        documentsTableView = new TableView<>();
        TableColumn<Document, Integer> docIdColumn = new TableColumn<>("ID Заказа");
        docIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Document, Integer> clientIdColumn = new TableColumn<>("ID Клиента");
        clientIdColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        TableColumn<Document, Date> dateColumn = new TableColumn<>("Дата");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        documentsTableView.getColumns().addAll(docIdColumn, clientIdColumn, dateColumn);

        documentIdField = new TextField();
        documentIdField.setPromptText("Введите ID заказа");
        showDocumentContentButton = new Button("Показать состав заказа");
        HBox idInputBox = new HBox(10, new Label("ID Заказа:"), documentIdField, showDocumentContentButton);
        idInputBox.setAlignment(Pos.CENTER);
        VBox.setMargin(idInputBox, new Insets(20, 0, 20, 0));

        Label clientChoicesLabel = new Label("Состав заказа");
        clientChoicesLabel.setFont(new Font(24.0));
        VBox.setMargin(clientChoicesLabel, new Insets(0, 0, 10, 0));

        clientChoicesTableView = new TableView<>();
        TableColumn<ClientChoice, String> productNameColumn = new TableColumn<>("Название товара");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<ClientChoice, BigDecimal> productPriceColumn = new TableColumn<>("Цена за шт.");
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        TableColumn<ClientChoice, Integer> quantityColumn = new TableColumn<>("Кол-во");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<ClientChoice, BigDecimal> totalProductsPriceColumn = new TableColumn<>("Сумма");
        totalProductsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalProductsPrice"));

        TableColumn<ClientChoice, String> deliveryMethodNameColumn = new TableColumn<>("Доставка");
        deliveryMethodNameColumn.setCellValueFactory(new PropertyValueFactory<>("deliveryMethodName"));

        TableColumn<ClientChoice, BigDecimal> totalDeliveryCostColumn = new TableColumn<>("Сумма доставки");
        totalDeliveryCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalDeliveryCost"));

        clientChoicesTableView.getColumns().setAll(Arrays.asList(productNameColumn, productPriceColumn, quantityColumn,
                totalProductsPriceColumn, deliveryMethodNameColumn, totalDeliveryCostColumn));

        container.getChildren().addAll(documentsTableView, idInputBox, clientChoicesLabel, clientChoicesTableView);
        return container;
    }

    private Node createProductsView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Управление товарами", 20);

        productsTableView = new TableView<>();
        TableColumn<Product, Integer> productIdCol = new TableColumn<>("ID");
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> productNameCol = new TableColumn<>("Название");
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> productPriceCol = new TableColumn<>("Цена");
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Boolean> productDeliveryCol = new TableColumn<>("Доставка");
        productDeliveryCol.setCellValueFactory(new PropertyValueFactory<>("deliveryAvailable"));

        productsTableView.getColumns().addAll(productIdCol, productNameCol, productPriceCol, productDeliveryCol);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);

        productIdField = new TextField();
        productIdField.setPromptText("ID (пусто для нового)");
        productNameField = new TextField();
        productNameField.setPromptText("Название товара");
        productPriceField = new TextField();
        productPriceField.setPromptText("Цена");
        productDescriptionArea = new TextArea();
        productDescriptionArea.setPromptText("Описание");
        productDescriptionArea.setPrefRowCount(3);
        productDeliveryAvailableCheckBox = new CheckBox("Доставка доступна");

        formGrid.add(new Label("ID:"), 0, 0);
        formGrid.add(productIdField, 1, 0);
        formGrid.add(new Label("Название:"), 0, 1);
        formGrid.add(productNameField, 1, 1);
        formGrid.add(new Label("Цена:"), 0, 2);
        formGrid.add(productPriceField, 1, 2);
        formGrid.add(new Label("Описание:"), 0, 3);
        formGrid.add(productDescriptionArea, 1, 3);
        formGrid.add(productDeliveryAvailableCheckBox, 1, 4);

        addProductButton = new Button("Добавить товар");
        saveProductButton = new Button("Обновить товар");
        deleteProductButton = new Button("Удалить по ID");
        HBox buttonBox = new HBox(10, addProductButton, saveProductButton, deleteProductButton);
        buttonBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(productsTableView, formGrid, buttonBox);
        return container;
    }

    private Node createDeliveryMethodsView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Способы доставки", 20);

        deliveryMethodsTableView = new TableView<>();

        TableColumn<DeliveryMethod, Integer> methodIdCol = new TableColumn<>("ID");
        methodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DeliveryMethod, String> methodNameCol = new TableColumn<>("Название");
        methodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<DeliveryMethod, Integer> methodSpeedDaysCol = new TableColumn<>("Скорость доставки");
        methodSpeedDaysCol.setCellValueFactory(new PropertyValueFactory<>("speedDays"));

        deliveryMethodsTableView.getColumns().addAll(methodIdCol, methodNameCol, methodSpeedDaysCol);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);

        deliveryMethodIdField = new TextField();
        deliveryMethodIdField.setPromptText("ID (пусто для нового)");
        deliveryMethodNameField = new TextField();
        deliveryMethodNameField.setPromptText("Название способа доставки");
        deliveryMethodSpeedDaysField = new TextField();
        deliveryMethodSpeedDaysField.setPromptText("Скорость доставки");

        formGrid.add(new Label("ID:"), 0, 0);
        formGrid.add(deliveryMethodIdField, 1, 0);

        formGrid.add(new Label("Название:"), 0, 1);
        formGrid.add(deliveryMethodNameField, 1, 1);

        formGrid.add(new Label("Скорость доставки:"), 0, 2);
        formGrid.add(deliveryMethodSpeedDaysField, 1, 2);

        addDeliveryMethodButton = new Button("Добавить способ");
        saveDeliveryMethodButton = new Button("Обновить способ");
        deleteDeliveryMethodButton = new Button("Удалить по ID");

        HBox buttonBox = new HBox(10, addDeliveryMethodButton, saveDeliveryMethodButton, deleteDeliveryMethodButton);
        buttonBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(deliveryMethodsTableView, formGrid, buttonBox);
        return container;
    }

    private Node createAvailableDeliveryMethodsView() {
        VBox container = ViewBaseSettings.createBaseViewContainer("Доступные способы доставки", 20);

        availableDeliveryMethodsTableView = new TableView<>();
        TableColumn<AvailableDeliveryMethod, Integer> productIdCol = new TableColumn<>(
                "ID Продукта");
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<AvailableDeliveryMethod, Integer> deliveryMethodIdCol = new TableColumn<>(
                "ID Способа доставки");
        deliveryMethodIdCol.setCellValueFactory(new PropertyValueFactory<>("deliveryMethodId"));

        TableColumn<AvailableDeliveryMethod, Double> deliveryCostCol = new TableColumn<>(
                "Стоимость доставки");
        deliveryCostCol.setCellValueFactory(new PropertyValueFactory<>("deliveryCost"));

        availableDeliveryMethodsTableView.getColumns().addAll(productIdCol, deliveryMethodIdCol, deliveryCostCol);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);

        formProductIdField = new TextField();
        formProductIdField.setPromptText("ID Продукта");
        availableDeliveryMethodIdField = new TextField();
        availableDeliveryMethodIdField.setPromptText("ID Способа доставки");
        availableDeliveryCostField = new TextField();
        availableDeliveryCostField.setPromptText("Стоимость доставки");

        formGrid.add(new Label("ID Продукта:"), 0, 0);
        formGrid.add(formProductIdField, 1, 0);
        formGrid.add(new Label("ID Способа доставки:"), 0, 1);
        formGrid.add(availableDeliveryMethodIdField, 1, 1);
        formGrid.add(new Label("Стоимость:"), 0, 2);
        formGrid.add(availableDeliveryCostField, 1, 2);

        addAvailableDeliveryMethodButton = new Button("Добавить");
        saveAvailableDeliveryMethodButton = new Button("Обновить");
        deleteAvailableDeliveryMethodButton = new Button("Удалить");
        HBox buttonBox = new HBox(10, addAvailableDeliveryMethodButton, saveAvailableDeliveryMethodButton,
                deleteAvailableDeliveryMethodButton);
        buttonBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(availableDeliveryMethodsTableView, formGrid, buttonBox);
        return container;
    }

    public void showDocuments() {
        view.setCenter(documentsView);
    }

    public void showProducts() {
        view.setCenter(productsView);
    }

    public void showDeliveryMethods() {
        view.setCenter(deliveryMethodsView);
    }

    public void showAvailableDeliveryMethods() {
        view.setCenter(availableDeliveryMethodsView);
    }

    public Parent getView() {
        return view;
    }
}
