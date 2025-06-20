package com.ordersystem.controller;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.containers.Documents;
import com.ordersystem.containers.Products;
import com.ordersystem.view.EmployeeMainView;

import javafx.scene.Parent;

public class EmployeeMainController {

    private final EmployeeMainView view;
    private final DocumentController documentController;
    private final ProductController productController;
    private final DeliveryMethodController deliveryMethodController;
    private final AvailableDeliveryMethodController availableDeliveryMethodController;

    public EmployeeMainController() {
        this.view = new EmployeeMainView();

        Documents documentsContainer = Documents.getInstance();
        Products productsContainer = Products.getInstance();
        DeliveryMethods deliveryMethodsContainer = DeliveryMethods.getInstance();
        AvailableDeliveryMethods availableDeliveryMethodsContainer = AvailableDeliveryMethods.getInstance();

        this.documentController = new DocumentController(view, documentsContainer);
        this.productController = new ProductController(view, productsContainer);
        this.deliveryMethodController = new DeliveryMethodController(view, deliveryMethodsContainer);
        this.availableDeliveryMethodController = new AvailableDeliveryMethodController(view,
                availableDeliveryMethodsContainer);

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.documentsButton.setOnAction(e -> view.showDocuments());
        view.productsButton.setOnAction(e -> view.showProducts());
        view.deliveryMethodsButton.setOnAction(e -> view.showDeliveryMethods());
        view.availableDeliveryMethodsButton.setOnAction(e -> view.showAvailableDeliveryMethods());
    }

    public Parent getView() {
        return view.getView();
    }
}