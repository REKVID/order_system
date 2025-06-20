package com.ordersystem.containers;

import java.util.List;

import com.ordersystem.dao.ClientChoiceDAO;
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
    private final ClientChoiceDAO clientChoiceDAO;

    private Documents() {
        this.documentDAO = new DocumentDAO();
        this.clientChoiceDAO = new ClientChoiceDAO();

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

    public ObservableList<ClientChoice> getSelectedDocumentChoices() {
        return selectedDocumentChoices;
    }

    public void loadChoicesFor(Document document) {
        if (document == null) {
            selectedDocumentChoices.clear();
        } else {
            List<ClientChoice> choices = clientChoiceDAO.findByDocumentId(document.getId());
            selectedDocumentChoices.setAll(choices);
        }
    }
}