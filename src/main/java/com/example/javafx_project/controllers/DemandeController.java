package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.services.ArticleService;
import com.example.javafx_project.services.EtablissementService;
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
        String cat = categoriebox.getValue().toString(); // Get the selected category from the ComboBox
        String des = designationbox.getValue().toString();
        int quan = Integer.parseInt(quantitefield.getText());
        LocalDate date = datefield.getValue();
        String type = typeetab.getValue().toString();
        String nom = nometab.getValue().toString();

        Article article = new Article();
        article.setCategorie(cat);
        article.setDesignation(des);
        article.setQuantite(quan);
        article.setDatedajt(Date.valueOf(date));


        articleService.Retrait(article,type,nom);
        clearFields();

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
        nometab.setValue(null);
    }
}
