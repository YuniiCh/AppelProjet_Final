package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Justificatif {

    // Propriétés
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeJ")
    private String idJ;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateDepot;

    private String etatValidation;
    private String text;

    // Relations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeU")
    private Scolarite scolarite;

    // Constructors
    public Justificatif() {
    }

    public Justificatif(String idJ, Timestamp dateDepot, String etatValidation, String text, Scolarite scolarite) {
        this.idJ = idJ;
        this.dateDepot = dateDepot;
        this.etatValidation = etatValidation;
        this.text = text;
        this.scolarite = scolarite;
    }

    // Getter / Setter
    public String getIdJ() {return idJ;}
    public void setIdJ(String idJ) {this.idJ = idJ;}
    public Timestamp getDateDepot() {return dateDepot;}
    public void setDateDepot(Timestamp dateDepot) {this.dateDepot = dateDepot;}
    public String getEtatValidation() {return etatValidation;}
    public void setEtatValidation(String etatValidation) {this.etatValidation = etatValidation;}
    public String getText() {return text;}
    public void setText(String text) {this.text = text;}

    // HashCode / Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Justificatif that = (Justificatif) o;
        return idJ.equals(that.idJ);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJ);
    }
}
