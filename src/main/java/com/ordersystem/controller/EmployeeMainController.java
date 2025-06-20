package com.ordersystem.controller;

import com.ordersystem.containers.AvailableDeliveryMethods;
import com.ordersystem.containers.DeliveryMethods;
import com.ordersystem.containers.Documents;
import com.ordersystem.containers.Products;
import com.ordersystem.view.EmployeeMainView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        view.documentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.showDocuments();
            }
        });
        view.productsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.showProducts();
            }
        });
        view.deliveryMethodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.showDeliveryMethods();
            }
        });
        view.availableDeliveryMethodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.showAvailableDeliveryMethods();
            }
        });
    }

    public Parent getView() {
        return view.getView();
    }
}