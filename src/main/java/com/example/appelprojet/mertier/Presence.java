package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Presence {
//    priorietes
    @EmbeddedId
    private PresenceID idPresence;

    @Column(name = "EtatPresence")
    private String etatPresence;

//    Relations
//    etudiant
    @ManyToOne
    @JoinColumn(name = "CodeU", insertable = false, updatable = false)
    private Etudiant etudiant;

//    seance
    @ManyToOne
    @JoinColumn(name = "CodeSE", insertable = false, updatable = false)
    private Seance seance;

//    justificatif
    @ManyToOne
    @JoinColumn(name = "CodeJ", insertable = false, updatable = false)
    private Justificatif justificatif;



//    Constructeur

    public Presence() {
    }

    public Presence(String etatPresence) {
        this.etatPresence = etatPresence;
    }

//    getter and setter
    public PresenceID getIdPresence() {return idPresence;}

    public void setIdPresence(PresenceID idPresence) {this.idPresence = idPresence;}

    public String getEtatPresence() {return etatPresence;}

    public void setEtatPresence(String etatPresence) {this.etatPresence = etatPresence;}

    public Etudiant getEtudiant() {return etudiant;}

    public void setEtudiant(Etudiant etudiant) {this.etudiant = etudiant;}

    public Seance getSeance() {return seance;}

    public void setSeance(Seance seance) {this.seance = seance;}

    public Justificatif getJustificatif() {return justificatif;}

    public void setJustificatif(Justificatif justificatif) {this.justificatif = justificatif;}
    //    toString

    @Override
    public String toString() {
        return "Presence{" +
                "idPresence=" + idPresence +
                ", etatPresence='" + etatPresence + '\'' +
                ", etudiant=" + etudiant.toString() +
                ", seance=" + seance.toString() +
                ", justificatif=" + justificatif.toString() +
                '}';
    }

    //  equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presence presence = (Presence) o;
        return Objects.equals(idPresence, presence.idPresence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPresence);
    }
}
