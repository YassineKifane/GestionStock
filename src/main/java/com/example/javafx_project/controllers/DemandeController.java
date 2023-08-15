package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.services.ArticleService;
import com.example.javafx_project.services.EtablissementService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DemandeController implements Initializable {

    private Article article;
    @FXML
    private ComboBox categoriebox;
    @FXML
    private ComboBox designationbox;
    @FXML
    private TextField quantitefield;
    @FXML
    private ComboBox typeetab;
    @FXML
    private ComboBox nometab;
    @FXML
    private DatePicker datefield;
    @FXML
    private Button Valider;
    @FXML
    private Button Retour;

    private ArticleService articleService;
    private EtablissementService etablissementService;

    public void handleAddButtonAction() {
        String quanStr = quantitefield.getText().trim(); // Get the trimmed value from the text field
        int quan;

        if (!quanStr.isEmpty()) {
            try {
                quan = Integer.parseInt(quanStr); // Parse the integer value
            } catch (NumberFormatException e) {
                // Handle the case where the input is not a valid integer
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Input Validation");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid quantity.");
                alert.showAndWait();
                return; // Exit the method due to invalid input
            }
        } else {
            // Handle the case where the input field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Validation");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a quantity.");
            alert.showAndWait();
            return; // Exit the method due to missing input
        }

        String cat = categoriebox.getValue() != null ? categoriebox.getValue().toString() : null;
        String des = designationbox.getValue() != null ? designationbox.getValue().toString() : null;
        LocalDate date = datefield.getValue() != null ? datefield.getValue() : null;
        String type = typeetab.getValue() != null ? typeetab.getValue().toString() : null;
        String nom = nometab.getValue() != null ? nometab.getValue().toString() : null;

        // ... (rest of the code)

        // Additional validation for input fields
        if (cat == null || des == null || type == null || nom == null ||  date == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Validation");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the required fields.");
            alert.showAndWait();
            return; // Exit the method if input is not valid
        }
        else {

        Article article = new Article();
        article.setCategorie(cat);
        article.setDesignation(des);
        article.setQuantite(Integer.parseInt(quanStr));
        article.setDatedajt(Date.valueOf(date));


        articleService.Retrait(article,type,nom);
        clearFields();
        }

    }
    public void handleRetourButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
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
    public void initialize(URL location, ResourceBundle resources) {
        articleService = new ArticleService();
        etablissementService = new EtablissementService();
        populateComboBox();

        // Associate the event listener with the type ComboBox
        typeetab.setOnAction(this::onTypeSelected);
    }

    @FXML
    private void onTypeSelected(Event event) {
        String selectedType = (String) typeetab.getValue();

        if (selectedType != null) {
            ObservableList<String> namesByType = etablissementService.getNamesByType(selectedType);
            nometab.setItems(namesByType);
        } else {
            nometab.getItems().clear(); // Clear the nometab ComboBox if no type is selected
        }
    }


    private void populateComboBox() {
        categoriebox.getItems().addAll(articleService.articleChoice());
        designationbox.getItems().addAll(articleService.designationChoice());
        typeetab.getItems().addAll(etablissementService.etablissementType());
        nometab.getItems().addAll(etablissementService.etablissementNom());
    }
    private void clearFields() {

        quantitefield.clear();
        designationbox.setValue(null);
        categoriebox.setValue(null);
        datefield.setValue(null);
        typeetab.setValue(null);

    }
}
