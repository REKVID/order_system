package com.ordersystem.containers;

import java.util.List;

import com.ordersystem.dao.DeliveryMethodDAO;
import com.ordersystem.model.DeliveryMethod;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeliveryMethods {

    private static DeliveryMethods instance;

    private final ObservableList<DeliveryMethod> deliveryMethods;
    private final DeliveryMethodDAO deliveryMethodDAO;

    private DeliveryMethods() {
        this.deliveryMethodDAO = new DeliveryMethodDAO();
        List<DeliveryMethod> methodList = deliveryMethodDAO.findAll();
        this.deliveryMethods = FXCollections.observableArrayList(methodList);
    }

    public static DeliveryMethods getInstance() {
        if (instance == null) {
            instance = new DeliveryMethods();
        }
        return instance;
    }

    public ObservableList<DeliveryMethod> getDeliveryMethods() {
        return deliveryMethods;
    }

    public void loadAll() {
        List<DeliveryMethod> methodList = deliveryMethodDAO.findAll();
        deliveryMethods.setAll(methodList);
    }

    public void create(DeliveryMethod method) {
        deliveryMethodDAO.create(method);
        deliveryMethods.add(method);
    }

    public void update(DeliveryMethod method) {
        deliveryMethodDAO.update(method);
        int index = deliveryMethods.indexOf(method);
        if (index != -1) {
            deliveryMethods.set(index, method);
        }
    }

    public boolean delete(DeliveryMethod method) {
        try {
            deliveryMethodDAO.delete(method.getId());
            deliveryMethods.remove(method);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public DeliveryMethod findById(int id) {
        for (DeliveryMethod method : deliveryMethods) {
            if (method.getId() == id) {
                return method;
            }
        }
        return null;
    }
}
