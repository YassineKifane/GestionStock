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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    @FXML
    private Button Retour;

    private EtablissementService etablissementService;

    public void handleRetourButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/etablissement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Retour.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etablissementService = new EtablissementService();
        populateComboBox();
    }

    private void populateComboBox() {
        typefield.getItems().addAll(etablissementService.etablissementType());
    }
    public void handleValiderButtonAction() {
        String cat = typefield.getValue().toString();
        String st = nomfield.getText();

        Etablissement etablissement = new Etablissement();
        etablissement.setType(cat);
        etablissement.setNom(st);

        etablissementService.save(etablissement);
        clearFields();
        closeForm();
    }

    private void clearFields() {

        typefield.setValue(null);
        nomfield.clear();
    }

    private void closeForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/etablissement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Valider.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }




}
