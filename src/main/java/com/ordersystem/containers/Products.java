package com.ordersystem.containers;

import java.util.List;

import com.ordersystem.dao.ProductDAO;
import com.ordersystem.model.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Products {

    private static Products instance;

    private final ObservableList<Product> productsList;
    private final ProductDAO productDAO;

    private Products() {
        this.productDAO = new ProductDAO();
        List<Product> productList = productDAO.findAll();
        this.productsList = FXCollections.observableArrayList(productList);
    }

    public static Products getInstance() {
        if (instance == null) {
            instance = new Products();
        }
        return instance;
    }

    public ObservableList<Product> getProductsList() {
        return productsList;
    }

    public void loadAll() {
        List<Product> productList = productDAO.findAll();
        productsList.setAll(productList);
    }

    public void create(Product product) {
        productDAO.create(product);
        productsList.add(product);
    }

    public void update(Product product) {
        productDAO.update(product);
        int index = productsList.indexOf(product);
        if (index != -1) {
            productsList.set(index, product);
        }
    }

    public void delete(Product product) {
        productDAO.delete(product.getId());
        productsList.remove(product);
    }
}
