package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import com.example.javafx_project.services.EtablissementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EtablissementAddForm implements Initializable {

    @FXML
    private ComboBox typefield;
    @FXML
    private TextField nomfield;

    @FXML
    private Button Valider;


    private EtablissementService etablissementService;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etablissementService = new EtablissementService();
        populateComboBox();
    }

    private void populateComboBox() {
        typefield.getItems().addAll(etablissementService.etablissementType());
    }
    public void handleValiderButtonAction() {

        String cat = typefield.getValue() != null ? typefield.getValue().toString() : null;
        String des = nomfield.getText() ;

        // Additional validation for input fields
        if (cat == null || des.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Validation");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return; // Exit the method if input is not valid
        }
        else {
            Etablissement etablissement = new Etablissement();
            etablissement.setType(cat);
            etablissement.setNom(des);

            etablissementService.save(etablissement);
            clearFields();
            closeForm();
        }
    }

    private void clearFields() {

        typefield.setValue(null);
        nomfield.clear();
    }

    private void closeForm() {
        try {
            // Load the FXML file for the new scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
            Parent root = loader.load();

            tstController tstController = loader.getController();

            // Load the content of artocme.fxml into pnlArticle
            FXMLLoader etablissementLoader = new FXMLLoader(getClass().getResource("/views/etablissement.fxml"));
            BorderPane etablissementContent = etablissementLoader.load();
            tstController.setEtablissementContent(etablissementContent.getCenter());

            // Set the scene with the updated content
            Scene scene = new Scene(root);
            Stage stage = (Stage) Valider.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




}
