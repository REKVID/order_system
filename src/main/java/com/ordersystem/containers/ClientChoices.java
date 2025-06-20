package com.ordersystem.containers;

import java.util.List;

import com.ordersystem.dao.ClientChoiceDAO;
import com.ordersystem.model.ClientChoice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientChoices {
    private final ObservableList<ClientChoice> clientChoicesList;
    private final ClientChoiceDAO clientChoiceDAO;

    public ClientChoices(int documentId) {
        this.clientChoiceDAO = new ClientChoiceDAO();
        List<ClientChoice> clientChoiceList = clientChoiceDAO.findByDocumentId(documentId);
        this.clientChoicesList = FXCollections.observableArrayList(clientChoiceList);
    }

    public ObservableList<ClientChoice> getClientChoicesList() {
        return clientChoicesList;
    }

    public void create(ClientChoice clientChoice) {
        clientChoiceDAO.create(clientChoice);
        clientChoicesList.add(clientChoice);
    }
}
