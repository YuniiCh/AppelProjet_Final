package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

public class Cours {
    //    Priorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCours")
    private int idCours;

    @Column(name = "nomCours")
    private String nomCours;

    @Column(nullable = false)
    private String typeCours;

    /**
     * Relations.
     */
    /*--- Formation ---*/
    @ManyToMany(mappedBy = "lesCours",fetch = FetchType.EAGER)
    private Set<Formation> formations = new HashSet(0);

    /*---Cours Séance de cours---*/
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Seance> seances = new HashSet(0);

    //    Contructeur

    public Cours() {
    }

    public Cours(String nomCours, String typeCours) {
        this.nomCours = nomCours;
        this.typeCours = typeCours;
    }

    //    getter and setter

    public int getIdCours() {
        return idCours;
    }

    public void setIdCours(int idCours) {
        this.idCours = idCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getTypeCours() {
        return typeCours;
    }

    public void setTypeCours(String typeCours) {
        this.typeCours = typeCours;
    }

    public Set<Formation> getFormations() {
        return formations;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }

    public Set<Seance> getSeances() {
        return seances;
    }

    public void setSeances(Set<Seance> seances) {
        this.seances = seances;
    }
//    toString

    @Override
    public String toString() {
        return "Cours{" +
                "idCours=" + idCours +
                ", nomCours='" + nomCours + '\'' +
                ", typeCours='" + typeCours + '\'' +
                '}';
    }

    //    equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cours)) return false;
        Cours cours = (Cours) o;
        return getIdCours() == cours.getIdCours();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCours());
    }
}
