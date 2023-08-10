package com.example.javafx_project.dao.impl;


import com.example.javafx_project.dao.EtablissementDao;
import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EtablissementImpl implements EtablissementDao {
    private Connection conn = DB.getConnection();
    @Override
    public ObservableList<String> getType() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> etabType = FXCollections.observableArrayList();
        try {
            ps = conn.prepareStatement("SELECT type FROM etabtype");
            rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                etabType.add(type);
            }
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner le type d'un Etablissement");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
        return etabType;
    }

    @Override
    public ObservableList<String> getName() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> etabname = FXCollections.observableArrayList();
        try {
            ps = conn.prepareStatement("SELECT etabnom FROM etablissement ");
            rs = ps.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("etabnom");
                etabname.add(nom);
            }
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner le Nom d'un Etablissement");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
        return etabname;
    }

    @Override
    public void insert(Etablissement etablissement) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO etablissement (etabtype, etabnom) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, etablissement.getType());
            ps.setString(2, etablissement.getNom());


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    etablissement.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("Problème d'insertion d'un Etablissement");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Etablissement> findAll(){
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM etablissement");
            rs = ps.executeQuery();

            List<Etablissement> listEtablissement = new ArrayList<>();

            while (rs.next()) {
                Etablissement etablissement = new Etablissement();
                etablissement.setId(rs.getInt("id"));
                etablissement.setType(rs.getString("etabtype"));
                etablissement.setNom(rs.getString("etabnom"));
                listEtablissement.add(etablissement);
            }

            return listEtablissement;
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner un Etablissement");
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM etablissement WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de suppression d'un Etablissement");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
    }

}
