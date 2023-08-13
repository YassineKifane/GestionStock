package com.example.javafx_project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button Demandes;

    @FXML
    private Button Articles;

    @FXML
    private Button Operations;
    @FXML
    private Button Logout;

    public void handleArticlesButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/article.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Articles.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleOperationButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/operations.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Operations.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void handleDemandesButton() {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/demande.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Get the stage from the switchButton and set the new scene
        Stage stage = (Stage) Operations.getScene().getWindow();
        stage.setScene(scene);

    } catch (IOException ex) {
        ex.printStackTrace();
    }
    }

    public void handleEtablissementButton() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/etablissement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Operations.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Logout.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
