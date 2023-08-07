package com.example.javafx_project;

import com.example.javafx_project.controllers.LoginController;
import com.example.javafx_project.services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        UserService userService = new UserService();
        LoginController loginController = fxmlLoader.getController();
        loginController.setUserService(userService);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setResizable(false);
        stage.getIcons().add(new Image("C:/Users/HP/Desktop/Stage DPO/JavaFX-CRUD/src/main/resources/img/logo.jpg"));
        stage.setTitle("Direction Provinciale d'Oriental");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}