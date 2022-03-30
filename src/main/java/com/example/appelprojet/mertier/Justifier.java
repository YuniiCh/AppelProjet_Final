package com.example.appelprojet.mertier;

import com.example.appelprojet.util.EtatValidation;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Justifier {
    //    propriétés
    @EmbeddedId
    private JustifierID id;

    @Enumerated(EnumType.STRING)
    private EtatValidation etatValidation;

    //    relations
    //    etudiant
    @ManyToOne
    @JoinColumn(name = "CodeJ", insertable = false, updatable = false)
    private Justificatif justificatif;

    //    seance
    @ManyToOne
    @JoinColumn(name = "CodeSE", insertable = false, updatable = false)
    private Seance seance;

    //    constructeurs
    public Justifier() {
    }

    public Justifier(JustifierID id, EtatValidation etatValidation) {
        this.id = id;
        this.etatValidation = etatValidation;
    }

    //    getter et setter

    public JustifierID getId() {return id;}

    public void setId(JustifierID id) {this.id = id;}

    public EtatValidation getEtatValidation() {return etatValidation;}

    public void setEtatValidation(EtatValidation etatValidation) {this.etatValidation = etatValidation;}

    public Justificatif getJustificatif() {return justificatif;}

    public void setJustificatif(Justificatif justificatif) {this.justificatif = justificatif;}

    public Seance getSeance() {return seance;}

    public void setSeance(Seance seance) {this.seance = seance;}

    //   toString
    @Override
    public String toString() {
        return "Justifier{" +
                "id=" + id +
                ", etatValidation=" + etatValidation +
                ", justificatif=" + justificatif +
                ", seance=" + seance +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Justifier justifier = (Justifier) o;
        return Objects.equals(id, justifier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
