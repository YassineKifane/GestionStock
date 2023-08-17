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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ArticleController implements Initializable {

    @FXML
    private TableView<Article> ArticleTableView;

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
    private Button addArticleForm;

    @FXML
    private Button Retour;

    private ArticleService articleService;
    private ObservableList<Article> articleList;
    public void openArticleForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/articleform.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) addArticleForm.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openEditArticleForm(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/articleEditForm.fxml"));
            Parent root = loader.load();
            ArticleEditController articleEditController = loader.getController();
            articleEditController.setArticle(article);
            articleEditController.setArticleService(articleService);
            Scene scene = new Scene(root);

            // Get the stage from the switchButton and set the new scene
            Stage stage = (Stage) addArticleForm.getScene().getWindow();
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
                            openEditArticleForm(article);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        articleService = new ArticleService();

        loadArticle();
        setupTableView();
    }

    private void loadArticle() {
        List<Article> producers = articleService.findAll();
        articleList = FXCollections.observableArrayList(producers);
    }

    private void setupTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantiteColumn.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        editColumn.setCellFactory(createEditButtonCellFactory());


        ArticleTableView.setItems(articleList);
    }
    private void startAutoRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            refreshTableView();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void refreshTableView() {
        List<Article> articles = articleService.findAll();
        ArticleTableView.getItems().clear();
        ArticleTableView.getItems().addAll(articles);
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
}
