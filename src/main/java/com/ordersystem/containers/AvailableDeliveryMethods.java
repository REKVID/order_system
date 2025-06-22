package com.ordersystem.containers;

import java.util.List;

import com.ordersystem.dao.AvailableDeliveryMethodDAO;
import com.ordersystem.model.AvailableDeliveryMethod;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AvailableDeliveryMethods {

    private static AvailableDeliveryMethods instance;
    private final AvailableDeliveryMethodDAO dao;
    private final ObservableList<AvailableDeliveryMethod> availableDeliveryMethodsList;

    private AvailableDeliveryMethods() {
        this.dao = new AvailableDeliveryMethodDAO();
        List<AvailableDeliveryMethod> allMethods = dao.findAll();
        this.availableDeliveryMethodsList = FXCollections.observableArrayList(allMethods);
    }

    public static synchronized AvailableDeliveryMethods getInstance() {
        if (instance == null) {
            instance = new AvailableDeliveryMethods();
        }
        return instance;
    }

    public ObservableList<AvailableDeliveryMethod> getAvailableDeliveryMethodsList() {
        return availableDeliveryMethodsList;
    }

    public void loadAll() {
        availableDeliveryMethodsList.setAll(dao.findAll());
    }

    public void create(AvailableDeliveryMethod method) {
        dao.save(method);
        availableDeliveryMethodsList.add(method);
    }

    public void update(AvailableDeliveryMethod method) {
        dao.save(method);

        AvailableDeliveryMethod toRemove = null;
        for (AvailableDeliveryMethod m : availableDeliveryMethodsList) {
            if (m.getProductId() == method.getProductId() && m.getDeliveryMethodId() == method.getDeliveryMethodId()) {
                toRemove = m;
                break;
            }
        }
        if (toRemove != null) {
            availableDeliveryMethodsList.remove(toRemove);
        }

        availableDeliveryMethodsList.add(method);
    }

    public boolean delete(AvailableDeliveryMethod method) {
        try {
            dao.delete(method.getProductId(), method.getDeliveryMethodId());

            AvailableDeliveryMethod toRemove = null;
            for (AvailableDeliveryMethod m : availableDeliveryMethodsList) {
                if (m.getProductId() == method.getProductId()
                        && m.getDeliveryMethodId() == method.getDeliveryMethodId()) {
                    toRemove = m;
                    break;
                }
            }
            if (toRemove != null) {
                availableDeliveryMethodsList.remove(toRemove);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}