package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.sql.Timestamp;
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
    @OneToMany(mappedBy = "cours", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Cours> cours = new HashSet<>(0);

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

    public Set<Cours> getCours() {
        return cours;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Map<Etudiant, Presence> getPresences1() {
        return etuPresences;
    }

    public void setPresences1(Map<Etudiant, Presence> presences1) {
        this.etuPresences = presences1;
    }

    public Map<Justificatif, Presence> getPresences2() {
        return justPresences;
    }

    public void setPresences2(Map<Justificatif, Presence> presences2) {
        this.justPresences = presences2;
    }

    //    toString

    @Override
    public String toString() {
        return "Seance{" +
                "idSeance=" + idSeance +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", etatAppel='" + etatAppel + '\'' +
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
