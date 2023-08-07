package com.example.javafx_project.dao.impl;


import com.example.javafx_project.dao.EtablissementDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
