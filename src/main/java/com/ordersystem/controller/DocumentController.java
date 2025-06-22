package com.ordersystem.controller;

import com.ordersystem.containers.Documents;
import com.ordersystem.model.Document;

import javafx.scene.control.Alert;

public class DocumentController {

    private final Documents documentsContainer;

    public DocumentController(Documents documentsContainer) {
        this.documentsContainer = documentsContainer;
    }

    public void showDocumentContent(String idText) {
        if (idText.isEmpty()) {
            documentsContainer.loadChoicesFor(null);
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