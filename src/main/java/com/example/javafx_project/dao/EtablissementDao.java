package com.example.javafx_project.dao;


import com.example.javafx_project.entities.Etablissement;
import javafx.collections.ObservableList;

import java.util.List;

public interface EtablissementDao {

    List<Etablissement> findAll();
    ObservableList<String> getType();
    ObservableList<String> getName();
}
