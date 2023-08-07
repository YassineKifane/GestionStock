package com.example.javafx_project.services;

import com.example.javafx_project.dao.ArticleDao;
import com.example.javafx_project.dao.EtablissementDao;
import com.example.javafx_project.dao.impl.ArticleDaoImpl;
import com.example.javafx_project.dao.impl.EtablissementImpl;
import javafx.collections.ObservableList;

public class EtablissementService {
    private EtablissementDao etablissementDao = new EtablissementImpl();
    public ObservableList<String> etablissementType(){ return etablissementDao.getType();}
    public ObservableList<String> etablissementNom(){ return etablissementDao.getName();}

}
