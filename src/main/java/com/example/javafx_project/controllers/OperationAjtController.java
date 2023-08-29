package com.example.javafx_project.controllers;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.services.ArticleService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OperationAjtController implements Initializable {

    @FXML
    private TableView<Article> OperationAjtTableView;

    @FXML
    private TableColumn<Article, Integer> idColumn;

    @FXML
    private TableColumn<Article, String> categorieColumn;

    @FXML
    private TableColumn<Article, String> designationColumn;


    @FXML
    private TableColumn<Article, Integer> quantiteColumn;
    @FXML
    private TableColumn<Article, LocalDate> dateColumn;

    @FXML
    private TableColumn<Article, Button> editColumn;

    @FXML
    private Button btn;


    private ArticleService articleService;
    private ObservableList<Article> articleList;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articleService = new ArticleService();
        loadOperation();
        setupTableView();
    }

    private void loadOperation() {
        List<Article> operations = articleService.findAllOpAjt();
        articleList = FXCollections.observableArrayList(operations);
    }

    private void setupTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datedajt"));
        editColumn.setCellFactory(createEditButtonCellFactory());


        OperationAjtTableView.setItems(articleList);
    }

    public void openEditOperationForm(Article article) {
        try {

            FXMLLoader tstLoader = new FXMLLoader(getClass().getResource("/views/tst.fxml"));
            Parent root = tstLoader.load();

            tstController tstController = tstLoader.getController();

            FXMLLoader opLoader = new FXMLLoader(getClass().getResource("/views/operationEditForm.fxml"));
            Parent OpAjtContent = opLoader.load();
            OperationAjtEditController  operationAjtEditController = opLoader.getController(); // Get the controller after loading the FXML

            operationAjtEditController.setArticle(article);
            operationAjtEditController.setArticleService(articleService);
            tstController.setArticleContent(OpAjtContent);

            // Set the scene with the updated content
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    private Callback<TableColumn<Article, Button>, TableCell<Article, Button>> createEditButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Article, Button> call(TableColumn<Article, Button> column) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction(event -> {
                            Article article = getTableView().getItems().get(getIndex());
                            System.out.println(article);
                            openEditOperationForm(article);

                        });
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

    private void startAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            refreshTableView();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void refreshTableView() {
        List<Article> articles = articleService.findAllOpAjt();
        OperationAjtTableView.getItems().clear();
        OperationAjtTableView.getItems().addAll(articles);
    }


    public void handleExportButton() throws Exception {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Export Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("You are about to export this Tableview to excel.");

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");

        confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {

            articleService.writingOpAjtExcelFile(articleList);
        }
    }
}


