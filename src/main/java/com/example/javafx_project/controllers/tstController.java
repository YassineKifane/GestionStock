package com.example.javafx_project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class tstController implements Initializable {

    @FXML
    private Button btnArticle;

    @FXML
    private Button btnOpRetrait;

    @FXML
    private Button btnEtablissement;

    @FXML
    private Button btnOpAjt;

    @FXML
    private Button btnDemande;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlEtablissements;

    @FXML
    private Pane pnlOpRetrait;

    @FXML
    private Pane pnlArticle;

    @FXML
    private Pane pnlOpAjt;

    @FXML
    private Pane pnlDemande;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnEtablissement) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/etablissement.fxml"));
                BorderPane newContent = loader.load();
                pnlEtablissements.getChildren().setAll(newContent.getCenter());
                pnlEtablissements.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == btnOpAjt) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/opAjt.fxml"));
                BorderPane newContent = loader.load();
                pnlOpAjt.getChildren().setAll(newContent.getCenter());
                pnlOpAjt.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == btnDemande) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/demande.fxml"));
                BorderPane newContent = loader.load();
                pnlDemande.getChildren().setAll(newContent.getCenter());
                pnlDemande.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (actionEvent.getSource() == btnArticle) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/article.fxml"));
                BorderPane newContent = loader.load();
                pnlArticle.getChildren().setAll(newContent.getCenter());
                pnlArticle.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(actionEvent.getSource()== btnOpRetrait)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/opRetrait.fxml"));
                BorderPane newContent = loader.load();
                pnlOpRetrait.getChildren().setAll(newContent.getCenter());
                pnlOpRetrait.toFront();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) btnSignout.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setEtablissementContent(Node content) {
        pnlEtablissements.getChildren().setAll(content);
        pnlEtablissements.toFront();
    }

    public void setArticleContent(Node content) {
        pnlArticle.getChildren().setAll(content);
        pnlArticle.toFront();
    }


}
