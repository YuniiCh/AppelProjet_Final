package com.example.appelprojet.dao;

import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Formation;

public class FormationDAO extends DAO<Formation> {
    public FormationDAO() {super.setEntity(Formation.class);}
}
