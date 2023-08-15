package com.example.javafx_project.dao.impl;


import com.example.javafx_project.dao.EtablissementDao;
import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void updateEtablissement(Etablissement etablissement) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE etablissement SET etabtype = ?, etabnom = ? WHERE id = ?");
            ps.setString(1, etablissement.getType());
            ps.setString(2, etablissement.getNom());
            ps.setInt(3, etablissement.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Problème de mise à jour d'un Etablissement");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
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
    public ObservableList<String> getNamesByType(String type) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ObservableList<String> etabnameByType = FXCollections.observableArrayList();
        try {
            ps = conn.prepareStatement("SELECT etabnom FROM etablissement WHERE etabtype = ?");
            ps.setString(1, type);
            rs = ps.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("etabnom");
                etabnameByType.add(nom);
            }
        } catch (SQLException e) {
            System.err.println("Problème de requête pour sélectionner les noms d'un Etablissement par type");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
        return etabnameByType;
    }


    @Override
    public void insert(Etablissement etablissement) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Insertion Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to do add a new Etablissement  \n"+etablissement.getType()+" , "+etablissement.getNom());

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");

        confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful Operation");
            alert.setHeaderText(null);
            alert.setContentText("You just accepted the next operation \n"+etablissement.getType()+" , "+etablissement.getNom());
            alert.showAndWait();
        } catch (SQLException e) {
            System.err.println("Problème d'insertion d'un Etablissement");
            e.printStackTrace();
        } finally {
            DB.closeStatement(ps);
        }
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
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this?");

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType cancelButton = new ButtonType("Cancel");

        confirmationAlert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {
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

}
