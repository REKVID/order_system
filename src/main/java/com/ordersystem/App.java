package com.ordersystem;

import java.io.IOException;

import com.ordersystem.controller.LoginController;
import com.ordersystem.view.LoginView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 720;
    private static final String INITIAL_WINDOW_TITLE = "Система заказов - Вход";

    @Override
    public void start(Stage stage) throws IOException {
        
        LoginController loginController = new LoginController(stage);
        LoginView loginView = new LoginView(loginController);

        Scene scene = new Scene(loginView.getView());
        stage.setScene(scene);
        stage.setTitle(INITIAL_WINDOW_TITLE);
        stage.setResizable(false);
        stage.setWidth(WINDOW_WIDTH);
        stage.setHeight(WINDOW_HEIGHT);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
