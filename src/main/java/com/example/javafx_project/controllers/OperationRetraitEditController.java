package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class OperationRetraitEditController implements Initializable {
    private Article article;
    @FXML
    private ComboBox categoriebox;
    @FXML
    private ComboBox designationbox;
    @FXML
    private TextField quantitefield;

    @FXML
    private ComboBox typeColumn;
    @FXML
    private ComboBox nomColumn;

    @FXML
    private DatePicker datefield;
    @FXML
    private Button Valider;


    private ArticleService articleService;
    private EtablissementService etablissementService;

    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public void setArticle(Article article) {
        this.article = article;
        populateFields();
    }

    private void populateFields() {
        if (article != null) {
            categoriebox.setValue(article.getCategorie());
            designationbox.setValue(article.getDesignation());
            quantitefield.setText(String.valueOf(article.getQuantite()));
            datefield.setValue(article.getDatedajt().toLocalDate());
            typeColumn.setValue(article.getEtablissement().getType());
            nomColumn.setValue(article.getEtablissement().getNom());

        }
    }

    private void updateArticle() {
        if (article != null) {
            article.setCategorie(categoriebox.getValue().toString());
            article.setDesignation(designationbox.getValue().toString());
            article.setQuantite(Integer.parseInt(quantitefield.getText()));
            article.setDatedajt(Date.valueOf(datefield.getValue()));
            Etablissement etablissement = new Etablissement();
            etablissement.setType(typeColumn.getValue().toString());
            etablissement.setNom(nomColumn.getValue().toString());
            article.setEtablissement(etablissement);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleService = new ArticleService();
        etablissementService = new EtablissementService();
        populateComboBox();

        // Associate the event listener with the type ComboBox
        typeColumn.setOnAction(this::onTypeSelected);
    }

    @FXML
    private void onTypeSelected(Event event) {
        String selectedType = (String) typeColumn.getValue();

        if (selectedType != null) {
            ObservableList<String> namesByType = etablissementService.getNamesByType(selectedType);
            nomColumn.setItems(namesByType);
        } else {
            nomColumn.getItems().clear(); // Clear the nometab ComboBox if no type is selected
        }
    }

    public void handleValiderButtonAction(ActionEvent actionEvent) {
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
        String typ = typeColumn.getValue() != null ? typeColumn.getValue().toString() : null;
        String nom = nomColumn.getValue() != null ? nomColumn.getValue().toString() : null;
        LocalDate date = datefield.getValue() != null ? datefield.getValue() : null;

        if (cat == null || des == null  ||  date == null || typ == null || nom == null) {
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
            confirmationAlert.setContentText("Are you sure you want to do edit the Operation  \n"+article.getCategorie()+" , "+article.getDesignation()+" , "+article.getQuantite()+" , "+article.getEtablissement().getType()+" , " +article.getEtablissement().getNom()+" , " +article.getDatedajt()+"\nto the new article \n"
                    +categoriebox.getValue().toString()+" , " + designationbox.getValue().toString()+" , "+Integer.parseInt(quantitefield.getText())+" , "+typeColumn.getValue().toString()+" , "+ nomColumn.getValue().toString()+" , "+ Date.valueOf(datefield.getValue()));

            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType cancelButton = new ButtonType("Cancel");

            confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == confirmButton) {
                updateArticle();
                articleService.updateOperationRetrait(article); // Assuming you have the update method in your ProducerService implementation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful Operation");
                alert.setHeaderText(null);
                alert.setContentText("Update Succeeded");
                alert.showAndWait();
                closeForm();
            }
        }
    }

    private void populateComboBox() {
        categoriebox.getItems().addAll(articleService.articleChoice());
        designationbox.getItems().addAll(articleService.designationChoice());
        typeColumn.getItems().addAll(etablissementService.etablissementType());
        nomColumn.getItems().addAll(etablissementService.etablissementNom());
    }


    private void closeForm() {
        try {
            FXMLLoader tstLoader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
            Parent root = tstLoader.load();

            tstController tstController = tstLoader.getController();

            FXMLLoader opLoader = new FXMLLoader(getClass().getResource("/views/opRetrait.fxml"));
            Parent OpAjtContent = opLoader.load();

            tstController.setArticleContent(OpAjtContent);

            // Set the scene with the updated content
            Scene scene = new Scene(root);
            Stage stage = (Stage) Valider.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
