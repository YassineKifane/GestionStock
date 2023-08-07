package com.example.javafx_project.services;

import com.example.javafx_project.dao.ArticleDao;
import com.example.javafx_project.dao.impl.ArticleDaoImpl;
import com.example.javafx_project.entities.Article;
import javafx.collections.ObservableList;

import java.util.List;

public class ArticleService {

    private ArticleDao articleDao = new ArticleDaoImpl();

    public List<Article> findAll() {
        return articleDao.findAll();
    }
    public List<Article> findAllOpAjt() {
        return articleDao.findAllOpAjt();
    }

    public void save(Article article) {
        articleDao.insert(article);
    }

    public void Retrait(Article article,String type,String nom) {
        articleDao.operationRetrait(article,type,nom);
    }

    public void update(Article article) {
        articleDao.updateArticle(article);
    }
    public void updateOperation(Article article) {
        articleDao.updateOperation(article);
    }

    public void remove(Article article) {
        articleDao.deleteById(article.getId());
    }

    public ObservableList<String> articleChoice(){ return articleDao.getCategories();}
    public ObservableList<String> designationChoice(){ return articleDao.getDesignations();}
}
