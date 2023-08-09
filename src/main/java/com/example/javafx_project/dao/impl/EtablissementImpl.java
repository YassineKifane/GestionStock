package com.example.javafx_project.dao.impl;


import com.example.javafx_project.dao.EtablissementDao;
import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            ps = conn.prepareStatement("SELECT etabtype FROM etablissement ");
            rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("etabtype");
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
                etablissement.setType(rs.getString("type"));
                etablissement.setNom(rs.getString("nom"));
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



}
