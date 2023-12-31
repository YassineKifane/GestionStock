package com.example.javafx_project.controllers;


import com.example.javafx_project.entities.Etablissement;
import com.example.javafx_project.services.EtablissementService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EtablissementController implements Initializable {

    @FXML
    private TableView<Etablissement> EtablissementTableView;

    @FXML
    private TableColumn<Etablissement, Integer> idColumn;

    @FXML
    private TableColumn<Etablissement, String> typeColumn;
    @FXML
    private TableColumn<Etablissement, String> nomColumn;
    @FXML
    private TableColumn<Etablissement, Button> deleteColumn;
    @FXML
    private TableColumn<Etablissement, Button> editColumn;

    @FXML
    private Button addEtablissement;



    private EtablissementService etablissementService;

    private ObservableList<Etablissement> etablissementsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        etablissementService = new EtablissementService();
        loadEtablissement();
        setupTableView();
    }

    private void setupTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        editColumn.setCellFactory(createEditButtonCellFactory());
        deleteColumn.setCellFactory(createDeleteButtonCellFactory());

        EtablissementTableView.setItems(etablissementsList);
    }

    private void loadEtablissement() {
        List<Etablissement> etablissements = etablissementService.findAll();
        etablissementsList = FXCollections.observableArrayList(etablissements);
    }


    private void refreshTableView() {
        List<Etablissement> etablissements = etablissementService.findAll();
        EtablissementTableView.getItems().clear();
        EtablissementTableView.getItems().addAll(etablissements);
    }

    public void openEtablissementForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
            Parent root = loader.load();

            tstController tstController = loader.getController();

            // Load the content of artocme.fxml into pnlArticle
            FXMLLoader etablissementLoader = new FXMLLoader(getClass().getResource("/views/etablissementForm.fxml"));
            BorderPane etablissementContent = etablissementLoader.load();
            tstController.setEtablissementContent(etablissementContent.getCenter());

            // Set the scene with the updated content
            Scene scene = new Scene(root);
            Stage stage = (Stage) addEtablissement.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private Callback<TableColumn<Etablissement, Button>, TableCell<Etablissement, Button>> createEditButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Etablissement, Button> call(TableColumn<Etablissement, Button> column) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction(event -> {
                            Etablissement etablissement = getTableView().getItems().get(getIndex());
                            System.out.println(etablissement);
                            openEditEtablissementForm(etablissement);

                            // Handle edit button action
                            // You can open a dialog or switch to an edit view
                        });
                    }

                    private void openEditEtablissementForm(Etablissement etablissement) {
                        try {
                            FXMLLoader tstLoader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
                            Parent root = tstLoader.load();

                            tstController tstController = tstLoader.getController();

                            FXMLLoader etablissementLoader = new FXMLLoader(getClass().getResource("/views/EditEtablissement.fxml"));
                            Parent etablissementContent = etablissementLoader.load();
                            EtablissementEditForm etablissementEditForm = etablissementLoader.getController(); // Get the controller after loading the FXML

                            etablissementEditForm.setEtablissement(etablissement);
                            etablissementEditForm.setEtablissementService(etablissementService);
                            tstController.setEtablissementContent(etablissementContent);

                            // Set the scene with the updated content
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) addEtablissement.getScene().getWindow();
                            stage.setScene(scene);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic(editButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }    

    private Callback<TableColumn<Etablissement, Button>, TableCell<Etablissement, Button>> createDeleteButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Etablissement, Button> call(TableColumn<Etablissement, Button> column) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction(event -> {
                            Etablissement etablissement = getTableView().getItems().get(getIndex());
                            etablissementService.remove(etablissement);
                            EtablissementTableView.getItems().remove(etablissement);
                        });
                    }

                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            setGraphic(deleteButton);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        };
    }


}
