package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

@Entity
public class Formation {
    //    Priorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFormation;

    @Column(nullable = false)
    private String nomFormation;

    /**
     * Relations.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Proposer",
            joinColumns = @JoinColumn(name = "CodeF"),
            inverseJoinColumns = @JoinColumn(name = "CodeC"))
    private Set<Cours> cours = new HashSet(0);

//    Formation_etudiants
    @ManyToMany
    @JoinTable(name = "Appartenir",
            joinColumns = @JoinColumn(name = "CodeF", referencedColumnName = "CodeE"))
    private List<Etudiant> etudiants = new ArrayList<>();

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }


    //    Contructeur

    public Formation() {
    }

    public Formation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    //    getter and setter

    public int getIdFormation() {
        return idFormation;
    }

    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNomFormation() {
        return nomFormation;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    //    toString

    @Override
    public String toString() {
        return "Formation{" +
                "idFormation=" + idFormation +
                ", nomFormation='" + nomFormation + '\'' +
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
