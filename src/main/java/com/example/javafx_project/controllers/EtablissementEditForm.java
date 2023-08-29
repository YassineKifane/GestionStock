package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import com.example.javafx_project.services.ArticleService;
import com.example.javafx_project.services.EtablissementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class EtablissementEditForm implements Initializable {

    @FXML
    private ComboBox typefield;
    @FXML
    private TextField nomfield;

    @FXML
    private Button Valider;


    private EtablissementService etablissementService;
    private Etablissement etablissement;

    public void setEtablissementService(EtablissementService etablissementService) {
        this.etablissementService = etablissementService;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
        populateFields();
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

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Update Confirmation");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to do edit the etablissement  \n" + etablissement.getType() + " , " + etablissement.getNom() + "\nto the new etablissement \n"
                    + typefield.getValue().toString() + " , " + nomfield.getText());

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");

            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == confirmButton) {
                updateEtablissement();
                etablissementService.update(etablissement);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful Operation");
                alert.setHeaderText(null);
                alert.setContentText("Update Succeeded");
                alert.showAndWait();
                closeForm();
            }
        }
    }

    private void updateEtablissement() {
        if (etablissement != null) {
            etablissement.setType(typefield.getValue().toString());
            etablissement.setNom(nomfield.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etablissementService = new EtablissementService();
        populateComboBox();
    }

    private void populateFields() {
        if (etablissement != null) {
            typefield.setValue(etablissement.getType());
            nomfield.setText(etablissement.getNom());
        }
    }
    private void populateComboBox() {
        typefield.getItems().addAll(etablissementService.etablissementType());
    }


    private void closeForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
            Parent root = loader.load();
            tstController tstController = loader.getController();

            // Load the content of Etablissement.fxml into pnlEtablissements
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
