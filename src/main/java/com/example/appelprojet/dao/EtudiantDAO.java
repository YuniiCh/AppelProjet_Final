package com.example.appelprojet.dao;

import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Etudiant;

public class EtudiantDAO extends DAO<Etudiant> {
    public EtudiantDAO() {super.setEntity(Etudiant.class);}
}
