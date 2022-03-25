package com.example.appelprojet.dao;

import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Enseignant;

public class EnseignantDAO extends DAO<Enseignant>{
    public EnseignantDAO() {super.setEntity(Enseignant.class);}
}
