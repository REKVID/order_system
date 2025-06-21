package com.ordersystem.controller;

import com.ordersystem.App;
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
        this.productController = new ProductController(view, productsContainer, availableDeliveryMethodsContainer);
        this.deliveryMethodController = new DeliveryMethodController(view, deliveryMethodsContainer,
                availableDeliveryMethodsContainer);
        this.availableDeliveryMethodController = new AvailableDeliveryMethodController(view,
                availableDeliveryMethodsContainer, productsContainer);

        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.documentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Documents.getInstance().loadAll();
                view.showDocuments();
            }
        });
        view.productsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Products.getInstance().loadAll();
                view.showProducts();
            }
        });
        view.deliveryMethodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DeliveryMethods.getInstance().loadAll();
                view.showDeliveryMethods();
            }
        });
        view.availableDeliveryMethodsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AvailableDeliveryMethods.getInstance().loadAll();
                view.showAvailableDeliveryMethods();
            }
        });
        view.logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                App.loadLoginWindow();
            }
        });
    }

    public Parent getView() {
        return view.getView();
    }
}