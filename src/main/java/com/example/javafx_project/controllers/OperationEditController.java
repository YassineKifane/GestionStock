package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.services.ArticleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class OperationEditController implements Initializable {
    private Article article;
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
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        articleService = new ArticleService();
        populateComboBox();
    }

    private void populateComboBox() {
        categoriebox.getItems().addAll(articleService.articleChoice());
        designationbox.getItems().addAll(articleService.designationChoice());
    }
    public void handleValiderButtonAction(ActionEvent actionEvent) {
        updateArticle();
        articleService.updateOperation(article); // Assuming you have the update method in your ProducerService implementation
        closeForm();
    }

    private void updateArticle() {
        if (article != null) {
            article.setCategorie(categoriebox.getValue().toString());
            article.setDesignation(designationbox.getValue().toString());
            article.setQuantite(Integer.parseInt(quantitefield.getText()));
            article.setDatedajt(Date.valueOf(datefield.getValue()));
        }
    }



    public void handleRetourButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/opAjt.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) Retour.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    private void closeForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/opAjt.fxml"));
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
