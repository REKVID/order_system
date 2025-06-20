package com.ordersystem.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public final class ViewBaseSettings {

    private ViewBaseSettings() {
    }

    private static final String PRIMARY_BUTTON_STYLE = "-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 10;";

    public static VBox createBaseViewContainer(String title, double spacing) {
        VBox container = new VBox(spacing);
        container.setPadding(new Insets(40));
        container.setStyle("-fx-background-color: white;");
        container.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label(title);
        titleLabel.setFont(new Font(48.0));
        VBox.setMargin(titleLabel, new Insets(0, 0, 20, 0));

        container.getChildren().add(titleLabel);
        return container;
    }

    public static void styleNavButton(Button button, double Width, double Height) {
        button.setPrefSize(Width, Height);
        button.setStyle(PRIMARY_BUTTON_STYLE);
    }

    public static void stylePrimaryButton(Button button) {
        button.setMinSize(420, 60);
        button.setStyle(PRIMARY_BUTTON_STYLE);
    }

    public static void styleTextField(TextField field, String prompt) {
        field.setPromptText(prompt);
        field.setMaxWidth(500);
        field.setPrefHeight(50);
    }
}