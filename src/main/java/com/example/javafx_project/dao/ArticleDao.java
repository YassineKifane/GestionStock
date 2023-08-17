package com.example.javafx_project.dao;

import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import javafx.collections.ObservableList;

import java.util.List;

public interface ArticleDao {
    void insert(Article article);
    void operationRetrait(Article article , String type , String nom);

    void updateArticle(Article article);
    void updateOperation(Article article);
    void updateOperationRetrait(Article article);

    void deleteById(Integer id);


    void writingOpAjtExcelFile(ObservableList<Article> list) throws Exception;

    void writingOpRetraitExcelFile(ObservableList<Article> list) throws Exception;

    List<Article> findAll();
    List<Article> findAllOpAjt();
    List<Article> findAllOpRetrait();

    ObservableList<String> getCategories();
    ObservableList<String> getDesignations();
}
