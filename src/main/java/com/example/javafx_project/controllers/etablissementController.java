package com.example.javafx_project.controllers;


import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import com.example.javafx_project.services.EtablissementService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class etablissementController implements Initializable {

    @FXML
    private TableView<Etablissement> etablissementTableView;

    @FXML
    private TableColumn<Etablissement, Integer> idColumn;

    @FXML
    private TableColumn<Etablissement, String> typeColumn;
    @FXML
    private TableColumn<Etablissement, String> nomColumn;

    private EtablissementService etablissementService;

    private ObservableList<Etablissement> etablissementsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setupTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));


        etablissementTableView.setItems(etablissementsList);
    }
    private void startAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            refreshTableView();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void refreshTableView() {
        List<Etablissement> etablissements = etablissementService.findAll();
        etablissementTableView.getItems().clear();
        etablissementTableView.getItems().addAll(etablissements);
    }
}
