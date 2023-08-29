package com.example.javafx_project.controllers;

import com.example.javafx_project.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private Button LoginButton;

    UserService userService = new UserService();

    public void setUserService(UserService userService) {
        this.userService = userService;
    }




    @FXML
    private void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Authenticate the user
        boolean isAuthenticated = userService.authenticateUser(username, password);

        if (isAuthenticated) {
            // Successful login
            showInfoAlert("Login Successful", "Welcome, " + username + "!");
            try {
                // Load the FXML file for the new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
                Parent root = loader.load();

                tstController tstController = loader.getController();

                // Load the content of artocme.fxml into pnlArticle
                FXMLLoader articleLoader = new FXMLLoader(getClass().getResource("/views/article.fxml"));
                BorderPane articleContent = articleLoader.load();
                tstController.setArticleContent(articleContent.getCenter());

                // Set the scene with the updated content
                Scene scene = new Scene(root);
                Stage stage = (Stage) LoginButton.getScene().getWindow();
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Failed login
            showErrorAlert("Login Failed", "Invalid username or password.");
        }
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
