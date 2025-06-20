package com.ordersystem.containers;

import java.util.List;

import com.ordersystem.dao.DocumentDAO;
import com.ordersystem.model.ClientChoice;
import com.ordersystem.model.Document;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Documents {

    private static Documents instance;

    private final ObservableList<Document> documents;
    private final ObservableList<ClientChoice> selectedDocumentChoices;

    private final DocumentDAO documentDAO;

    private Documents() {
        this.documentDAO = new DocumentDAO();

        List<Document> documentList = documentDAO.findAll();
        this.documents = FXCollections.observableArrayList(documentList);
        this.selectedDocumentChoices = FXCollections.observableArrayList();
    }

    public static Documents getInstance() {
        if (instance == null) {
            instance = new Documents();
        }
        return instance;
    }

    public ObservableList<Document> getDocuments() {
        return documents;
    }

    public void loadAll() {
        List<Document> documentList = documentDAO.findAll();
        documents.setAll(documentList);
    }

    public ObservableList<ClientChoice> getSelectedDocumentChoices() {
        return selectedDocumentChoices;
    }

    public void loadChoicesFor(Document document) {
        if (document == null) {
            selectedDocumentChoices.clear();
        } else {
            ClientChoices clientChoices = new ClientChoices(document.getId());
            selectedDocumentChoices.setAll(clientChoices.getClientChoicesList());
        }
    }
}