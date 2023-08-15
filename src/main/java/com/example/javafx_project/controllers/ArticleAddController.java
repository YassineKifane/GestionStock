package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.services.ArticleService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.util.ResourceBundle;

public class ArticleAddController implements Initializable {

    @FXML
    private ComboBox categoriebox;
    @FXML
    private ComboBox designationbox;
    @FXML
    private TextField quantitefield;
    @FXML
    private DatePicker datefield;
    @FXML
    private Button Valider;
    @FXML
    private Button Retour;

    private ArticleService articleService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articleService = new ArticleService();
        populateComboBox();
    }

    private void populateComboBox() {
        categoriebox.getItems().addAll(articleService.articleChoice());
        designationbox.getItems().addAll(articleService.designationChoice());
    }
    @FXML
    private void handleAddButtonAction() {
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


        // ... (rest of the code)

        // Additional validation for input fields
        if (cat == null || des == null  ||  date == null) {
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
        article.setQuantite(quan);
        article.setDatedajt(Date.valueOf(date));

        articleService.save(article);

        clearFields();
        closeForm();
        }
    }

    private void closeForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/article.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Valider.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void clearFields() {

        quantitefield.clear();
        designationbox.setValue(null);
        categoriebox.setValue(null);
        datefield.setValue(null);
    }

    public void handleRetourButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/article.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Retour.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
