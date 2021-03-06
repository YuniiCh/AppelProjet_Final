package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Cours")
public class Cours implements java.io.Serializable{
    //    Priorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeC")
    private long idCours;

    @Column(name = "nomCours")
    private String nomCours;

    @Column(nullable = false)
    private String typeCours;

    /**
     * Relations.
     */
    /*--- Formation ---*/
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeF")
    private Formation formation;

    /*---Cours Séance de cours---*/
    @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Seance> seances = new HashSet(0);

    //        Contructeur

    public Cours() {
    }

    public Cours(String nomCours, String typeCours, Formation formation) {
        this.nomCours = nomCours;
        this.typeCours = typeCours;
        this.formation = formation;
    }

    public Cours(String nomCours, String typeCours) {
        this.nomCours = nomCours;
        this.typeCours = typeCours;
    }

    //    getter and setter

    public long getIdCours() {
        return idCours;
    }

    public void setIdCours(long idCours) {
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


    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
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
