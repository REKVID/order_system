package com.ordersystem.controller;

import com.ordersystem.containers.Documents;
import com.ordersystem.model.Document;
import com.ordersystem.view.EmployeeMainView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;

public class DocumentController {

    private final EmployeeMainView view;
    private final Documents documentsContainer;

    public DocumentController(EmployeeMainView view, Documents documentsContainer) {
        this.view = view;
        this.documentsContainer = documentsContainer;
        view.documentsTableView.setItems(documentsContainer.getDocuments());
        view.clientChoicesTableView.setItems(documentsContainer.getSelectedDocumentChoices());
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        view.showDocumentContentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDocumentContent();
            }
        });
    }

    private void showDocumentContent() {
        String idText = view.documentIdField.getText();
        if (idText.isEmpty()) {
            return;
        }

        try {
            int id = Integer.parseInt(idText);

            Document selectedDocument = null;
            for (Document doc : documentsContainer.getDocuments()) {
                if (doc.getId() == id) {
                    selectedDocument = doc;
                    break;
                }
            }

            if (selectedDocument != null) {
                documentsContainer.loadChoicesFor(selectedDocument);
            } else {
                showAlert(Alert.AlertType.ERROR, "Документ с ID " + id + " не найден.");
                documentsContainer.loadChoicesFor(null);
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "ID документа должен быть числом.");
            documentsContainer.loadChoicesFor(null);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}