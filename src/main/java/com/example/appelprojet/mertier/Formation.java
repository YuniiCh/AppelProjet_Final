package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

@Entity
public class Formation {
    //    Priorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeF")
    private long idFormation;

    @Column(nullable = false)
    private String nomFormation;


    /**
     * Relations.
     */
    @OneToMany(mappedBy = "formation", fetch = FetchType.LAZY)
    private Set<Cours> cours = new HashSet(0);

    //    Formation_Etudiants
    @OneToMany(mappedBy = "formation", fetch = FetchType.LAZY)
    private Set<Etudiant> etudiants = new HashSet(0);

    //    Formation_Scolarites
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeU")
    private Scolarite scolarite;


    //    Contructeur

    public Formation() {
    }

    public Formation(String nomFormation, Scolarite scolarite) {
        this.nomFormation = nomFormation;
        this.scolarite = scolarite;
    }


    //    getter and setter

    public long getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(long idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }

    public Set<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(Set<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public Scolarite getScolarite() {
        return scolarite;
    }

    public void setScolarite(Scolarite scolarite) {
        this.scolarite = scolarite;
    }

    //    toString

    @Override
    public String toString() {
        return "Formation{" +
                "idFormation=" + idFormation +
                ", nomFormation='" + nomFormation +
                ", scolarite=" + scolarite.toString() +
                '}';
    }

    //    equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Formation)) return false;
        Formation formation = (Formation) o;
        return getIdFormation() == formation.getIdFormation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdFormation());
    }
}
