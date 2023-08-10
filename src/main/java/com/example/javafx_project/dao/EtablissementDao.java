package com.example.javafx_project.dao;



import com.example.javafx_project.entities.Etablissement;
import javafx.collections.ObservableList;

import java.util.List;

public interface EtablissementDao {
    void insert(Etablissement etablissement);
    List<Etablissement> findAll();
    ObservableList<String> getType();
    ObservableList<String> getName();
    void deleteById(Integer id);
}
