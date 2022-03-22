package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

@Entity
public class Seance {
    //priorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeSE")
    private long idSeance;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    private String etatAppel;

    //    Relaitons
    //    Cours
    @ManyToOne(fetch = FetchType.EAGER)
    private Cours cour;

    //    enseignant
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeU")
    private Enseignant enseignant;

    //    salle
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeS")
    private Salle salle;

    //    presence, etudiant
    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeU", updatable = false, insertable = false)
    private Map<Etudiant,Presence> etuPresences = new HashMap(0);

    //    presence, justificatif
    @OneToMany(mappedBy = "justificatif", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeJ", updatable = false, insertable = false)
    private Map<Justificatif,Presence> justPresences = new HashMap(0);


    //    Constructeur


    public Seance() {
    }

    public Seance(Date dateDebut, Date dateFin, String etatAppel) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.etatAppel = etatAppel;
    }

    //    getter and setter

    public long getIdSeance() {return idSeance;}

    public void setIdSeance(long idSeance) {this.idSeance = idSeance;}

    public Date getDateDebut() {return dateDebut;}

    public void setDateDebut(Date dateDebut) {this.dateDebut = dateDebut;}

    public Date getDateFin() {return dateFin;}

    public void setDateFin(Date dateFin) {this.dateFin = dateFin;}

    public String getEtatAppel() {return etatAppel;}

    public void setEtatAppel(String etatAppel) {this.etatAppel = etatAppel;}

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Cours getCour() {
        return cour;
    }

    public void setCour(Cours cour) {
        this.cour = cour;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Map<Etudiant, Presence> getEtuPresences() {
        return etuPresences;
    }

    public void setEtuPresences(Map<Etudiant, Presence> etuPresences) {
        this.etuPresences = etuPresences;
    }

    public Map<Justificatif, Presence> getJustPresences() {
        return justPresences;
    }

    public void setJustPresences(Map<Justificatif, Presence> justPresences) {
        this.justPresences = justPresences;
    }
//    toString

    @Override
    public String toString() {
        return "Seance{" +
                "idSeance=" + idSeance +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", etatAppel='" + etatAppel + '\'' +
                ", cour=" + cour.toString() +
                ", enseignant=" + enseignant.toString() +
                ", salle=" + salle.toString() +
                '}';
    }

    //    equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seance seance = (Seance) o;
        return Objects.equals(idSeance, seance.idSeance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSeance);
    }
}
