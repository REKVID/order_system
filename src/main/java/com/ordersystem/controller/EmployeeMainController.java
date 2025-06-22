package com.ordersystem.controller;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.containers.Documents;
import com.ordersystem.containers.Products;
import com.ordersystem.view.EmployeeMainView;
import com.ordersystem.view.LoginView;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class EmployeeMainController {

    private final Stage stage;
    private final EmployeeMainView view;
    private final DocumentController documentController;
    private final ProductController productController;
    private final DeliveryMethodController deliveryMethodController;
    private final AvailableDeliveryMethodController availableDeliveryMethodController;

    public EmployeeMainController(Stage stage) {
        this.stage = stage;

        Documents documentsContainer = Documents.getInstance();
        Products productsContainer = Products.getInstance();
        DeliveryMethods deliveryMethodsContainer = DeliveryMethods.getInstance();
        AvailableDeliveryMethods availableDeliveryMethodsContainer = AvailableDeliveryMethods.getInstance();

        this.documentController = new DocumentController(documentsContainer);
        this.productController = new ProductController(productsContainer, availableDeliveryMethodsContainer);
        this.deliveryMethodController = new DeliveryMethodController(deliveryMethodsContainer,availableDeliveryMethodsContainer);
        this.availableDeliveryMethodController = new AvailableDeliveryMethodController(availableDeliveryMethodsContainer, productsContainer);

        this.view = new EmployeeMainView(this);
    }

    public void handleShowDocuments() {
        Documents.getInstance().loadAll();
        view.showDocuments();
    }

    public void handleShowProducts() {
        Products.getInstance().loadAll();
        view.showProducts();
    }

    public void handleShowDeliveryMethods() {
        DeliveryMethods.getInstance().loadAll();
        view.showDeliveryMethods();
    }

    public void handleShowAvailableDeliveryMethods() {
        AvailableDeliveryMethods.getInstance().loadAll();
        view.showAvailableDeliveryMethods();
    }

    public void handleLogout() {
        LoginController loginController = new LoginController(stage);
        LoginView loginView = new LoginView(loginController);
        stage.getScene().setRoot(loginView.getView());
        stage.setTitle("Система заказов - Вход");
    }

    public void handleShowDocumentContent(String idText) {
        documentController.showDocumentContent(idText);
    }

    public void handleCreateProduct(String name, String priceText, String description, boolean isDeliveryAvailable) {
        productController.createProduct(name, priceText, description, isDeliveryAvailable);
    }

    public void handleUpdateProduct(String idText, String name, String priceText, String description,
            boolean isDeliveryAvailable) {
        productController.updateProduct(idText, name, priceText, description, isDeliveryAvailable);
    }

    public void handleDeleteProduct(String idText) {
        productController.deleteProduct(idText);
    }

    public void handleCreateDeliveryMethod(String name) {
        deliveryMethodController.createDeliveryMethod(name);
    }

    public void handleUpdateDeliveryMethod(String idText, String name) {
        deliveryMethodController.updateDeliveryMethod(idText, name);
    }

    public void handleDeleteDeliveryMethod(String idText) {
        deliveryMethodController.deleteDeliveryMethod(idText);
    }

    public void handleCreateAvailableDeliveryMethod(String productIdText, String deliveryMethodIdText,
            String deliveryCostText) {
        availableDeliveryMethodController.createAvailableDeliveryMethod(productIdText, deliveryMethodIdText,
                deliveryCostText);
    }

    public void handleUpdateAvailableDeliveryMethod(String productIdText, String deliveryMethodIdText,
            String deliveryCostText) {
        availableDeliveryMethodController.updateAvailableDeliveryMethod(productIdText, deliveryMethodIdText,
                deliveryCostText);
    }

    public void handleDeleteAvailableDeliveryMethod(String productIdText, String deliveryMethodIdText) {
        availableDeliveryMethodController.deleteAvailableDeliveryMethod(productIdText, deliveryMethodIdText);
    }

    public Parent getView() {
        return view.getView();
    }
}