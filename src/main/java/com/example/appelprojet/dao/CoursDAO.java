package com.example.appelprojet.dao;

import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Seance;

public class CoursDAO extends DAO<Cours>{
    public CoursDAO() {super.setEntity(Cours.class);}
}
