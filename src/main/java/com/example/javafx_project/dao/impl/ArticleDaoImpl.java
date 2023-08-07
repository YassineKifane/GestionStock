package com.example.javafx_project.dao.impl;

import com.example.javafx_project.dao.ArticleDao;
import com.example.javafx_project.entities.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDaoImpl implements ArticleDao {
    private Connection conn = DB.getConnection();
    @Override
    public void insert(Article article) {
        PreparedStatement psInsertArticle = null;
        PreparedStatement psInsertOperation = null;

        try {
            // Insert or update the articles table
            psInsertArticle = conn.prepareStatement("INSERT INTO articles (categories, designation, quantite, datedajt) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE quantite = quantite+VALUES(quantite), datedajt = VALUES(datedajt), id = VALUES(id)", Statement.RETURN_GENERATED_KEYS);
            psInsertArticle.setString(1, article.getCategorie());
            psInsertArticle.setString(2, article.getDesignation());
            psInsertArticle.setInt(3, article.getQuantite());
            java.sql.Date sqlDate = java.sql.Date.valueOf(article.getDatedajt().toLocalDate());
            psInsertArticle.setDate(4, sqlDate);

            int rowsAffectedArticle = psInsertArticle.executeUpdate();
            int articleId;

            // Retrieve the generated or existing ID from the articles table
            if (rowsAffectedArticle > 0) {
                ResultSet rs = psInsertArticle.getGeneratedKeys();
                if (rs.next()) {
                    articleId = rs.getInt(1);
                    article.setId(articleId);
                }
                DB.closeResultSet(rs);
            } else {
                // The row already exists in the articles table, get its ID
                psInsertArticle = conn.prepareStatement("SELECT id FROM articles WHERE categories = ? AND designation = ?");
                psInsertArticle.setString(1, article.getCategorie());
                psInsertArticle.setString(2, article.getDesignation());
                ResultSet rs = psInsertArticle.executeQuery();
                if (rs.next()) {
                    articleId = rs.getInt("id");
                    article.setId(articleId);
                }
                DB.closeResultSet(rs);
            }

            // Insert a new row into the operations table
            psInsertOperation = conn.prepareStatement("INSERT INTO operations (id, categories, designation, quantite, datedajt) VALUES (?, ?, ?, ?, ?)");
            psInsertOperation.setInt(1, article.getId());
            psInsertOperation.setString(2, article.getCategorie());
            psInsertOperation.setString(3, article.getDesignation());
            psInsertOperation.setInt(4, article.getQuantite());
            psInsertOperation.setDate(5, sqlDate);
            psInsertOperation.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Problème d'insertion d'un Article");
            e.printStackTrace();
        } finally {
            DB.closeStatement(psInsertArticle);
            DB.closeStatement(psInsertOperation);
        }
    }



    @Override
    public void operationRetrait(Article article,String type , String nom) {
        PreparedStatement psInsertArticle = null;
        PreparedStatement psInsertOperation = null;

        try {
            // Insert or update the articles table
            psInsertArticle = conn.prepareStatement("INSERT INTO articles (categories, designation, quantite, datedajt) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE quantite = quantite-VALUES(quantite), datedajt = VALUES(datedajt), id = VALUES(id)", Statement.RETURN_GENERATED_KEYS);
            psInsertArticle.setString(1, article.getCategorie());
            psInsertArticle.setString(2, article.getDesignation());
            psInsertArticle.setInt(3, article.getQuantite());
            java.sql.Date sqlDate = java.sql.Date.valueOf(article.getDatedajt().toLocalDate());
            psInsertArticle.setDate(4, sqlDate);

            int rowsAffectedArticle = psInsertArticle.executeUpdate();
            int articleId;

            // Retrieve the generated or existing ID from the articles table
            if (rowsAffectedArticle > 0) {
                ResultSet rs = psInsertArticle.getGeneratedKeys();
                if (rs.next()) {
                    articleId = rs.getInt(1);
                    article.setId(articleId);
                }
                DB.closeResultSet(rs);
            } else {
                // The row already exists in the articles table, get its ID
                psInsertArticle = conn.prepareStatement("SELECT id FROM articles WHERE categories = ? AND designation = ?");
                psInsertArticle.setString(1, article.getCategorie());
                psInsertArticle.setString(2, article.getDesignation());
                ResultSet rs = psInsertArticle.executeQuery();
                if (rs.next()) {
                    articleId = rs.getInt("id");
                    article.setId(articleId);
                }
                DB.closeResultSet(rs);
            }

            // Insert a new row into the operations table
            psInsertOperation = conn.prepareStatement("INSERT INTO operationsretrait (id, categories, designation, quantite, datedajt,typeEtab,nomEtab) VALUES (?, ?, ?, ?, ?,?,?)");
            psInsertOperation.setInt(1, article.getId());
            psInsertOperation.setString(2, article.getCategorie());
            psInsertOperation.setString(3, article.getDesignation());
            psInsertOperation.setInt(4, article.getQuantite());
            psInsertOperation.setDate(5, sqlDate);
            psInsertOperation.setString(6, type);
            psInsertOperation.setString(7, nom);
            psInsertOperation.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Problème d'insertion d'un Article");
            e.printStackTrace();
        } finally {
            DB.closeStatement(psInsertArticle);
            DB.closeStatement(psInsertOperation);
        }
    }



    @Override
    public void updateArticle(Article article) {
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = conn.prepareStatement("UPDATE articles SET categories = ?, designation = ?, quantite = ?, datedajt = ? WHERE Id = ?");
            ps.setString(1, article.getCategorie());
            ps.setString(2, article.getDesignation());
            ps.setInt(3, article.getQuantite());
            ps.setDate(4, article.getDatedajt());
            ps.setInt(5, article.getId());
            ps.executeUpdate();



        } catch (SQLException e) {
            System.err.println("Problème de mise à jour d'un article");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);

        }
    }

    @Override
    public void updateOperation(Article article) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE operations SET categories = ?, designation = ?, quantite = ?, datedajt = ? WHERE Id = ?");
            ps.setString(1, article.getCategorie());
            ps.setString(2, article.getDesignation());
            ps.setInt(3, article.getQuantite());
            ps.setDate(4, article.getDatedajt());
            ps.setInt(5, article.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de mise à jour d'un operations");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
    }
    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM articles WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de suppression d'un Article");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Article> findAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM articles");
            rs = ps.executeQuery();

            List<Article> listArticle = new ArrayList<>();

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setCategorie(rs.getString("categories"));
                article.setDesignation(rs.getString("designation"));
                article.setQuantite(rs.getInt("quantite"));
                article.setDatedajt(rs.getDate("datedajt"));
                listArticle.add(article);
            }

            return listArticle;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner un Article");
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Article> findAllOpAjt(){
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM operations");
            rs = ps.executeQuery();

            List<Article> listArticle = new ArrayList<>();

            while (rs.next()) {
                Article article = new Article();
                article.setId(rs.getInt("id"));
                article.setCategorie(rs.getString("categories"));
                article.setDesignation(rs.getString("designation"));
                article.setQuantite(rs.getInt("quantite"));
                article.setDatedajt(rs.getDate("datedajt"));
                listArticle.add(article);
            }

            return listArticle;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner un Operations");
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }


    @Override
    public ObservableList<String> getCategories() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> categories = FXCollections.observableArrayList();
        try {
            ps = conn.prepareStatement("SELECT * FROM categorienames");
            rs = ps.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                categories.add(category);
            }
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner un Category");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
        return categories;
    }

    @Override
    public ObservableList<String> getDesignations() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> Designations = FXCollections.observableArrayList();
        try {
            ps = conn.prepareStatement("SELECT * FROM designationnames");
            rs = ps.executeQuery();

            while (rs.next()) {
                String designation = rs.getString("designation");
                Designations.add(designation);
            }
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner un Désignation");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
        return Designations;
    }
}
