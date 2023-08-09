package com.example.javafx_project.services;

import com.example.javafx_project.dao.ArticleDao;
import com.example.javafx_project.dao.EtablissementDao;
import com.example.javafx_project.dao.impl.ArticleDaoImpl;
import com.example.javafx_project.dao.impl.EtablissementImpl;
import com.example.javafx_project.entities.Article;
import com.example.javafx_project.entities.Etablissement;
import javafx.collections.ObservableList;

import java.util.List;

public class EtablissementService {
    private EtablissementDao etablissementDao = new EtablissementImpl();

    public List<Etablissement> findAll() {
        return etablissementDao.findAll();
    }
    public ObservableList<String> etablissementType(){ return etablissementDao.getType();}
    public ObservableList<String> etablissementNom(){ return etablissementDao.getName();}

}
