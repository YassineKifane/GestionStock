package com.example.javafx_project.dao;

import com.example.javafx_project.entities.Article;
import javafx.collections.ObservableList;

import java.util.List;

public interface ArticleDao {
    void insert(Article article);
    void operationRetrait(Article article , String type , String nom);

    void updateArticle(Article article);
    void updateOperation(Article article);

    void deleteById(Integer id);

    List<Article> findAll();
    List<Article> findAllOpAjt();

    ObservableList<String> getCategories();
    ObservableList<String> getDesignations();
}
