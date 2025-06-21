package com.ordersystem;

import java.io.IOException;

import com.ordersystem.controller.LoginController;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 720;
    private static final String INITIAL_WINDOW_TITLE = "Система заказов - Вход";
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        LoginController loginController = new LoginController();
        Parent root = loginController.getView();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(INITIAL_WINDOW_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);
        primaryStage.show();
    }

    public static void setRoot(Parent root, String title) {
        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle(title);
    }

    public static void loadLoginWindow() {
        LoginController loginController = new LoginController();
        Parent root = loginController.getView();
        setRoot(root, INITIAL_WINDOW_TITLE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
