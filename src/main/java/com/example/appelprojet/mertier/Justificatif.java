package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

@Entity
public class Justificatif {

    // Propriétés
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeJ")
    private Long idJ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepot;

    private String etatValidation;
    private String linkFile;
    private String nameFile;

    // Relations
    //    Scolarite
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeSC")
    private Scolarite scolarite;


    //    Seances
    @OneToMany(mappedBy = "justificatif", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeSE")
    private Map<Seance,Justifier> lesSeances = new HashMap(0);

    // Etudiant
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeE")
    private Etudiant etudiant;

    // Constructors
    public Justificatif() {
    }

    public Justificatif(Date dateDepot, String etatValidation, String linkFile, String nameFile, Scolarite scolarite, Etudiant etudiant) {
        this.dateDepot = dateDepot;
        this.etatValidation = etatValidation;
        this.linkFile = linkFile;
        this.nameFile = nameFile;
        this.scolarite = scolarite;
        this.etudiant = etudiant;
    }

    // Getter / Setter
    public long getIdJ() {return idJ;}
    public void setIdJ(long idJ) {this.idJ = idJ;}
    public Date getDateDepot() {return dateDepot;}
    public void setDateDepot(Date dateDepot) {this.dateDepot = dateDepot;}
    public String getEtatValidation() {return etatValidation;}
    public void setEtatValidation(String etatValidation) {this.etatValidation = etatValidation;}

    public String getLinkFile() {
        return linkFile;
    }

    public void setLinkFile(String linkFile) {
        this.linkFile = linkFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public Scolarite getScolarite() {
        return scolarite;
    }

    public void setScolarite(Scolarite scolarite) {
        this.scolarite = scolarite;
    }

    public Map<Seance, Justifier> getJustifiers() {return lesSeances;}

    public void setJustifiers(Map<Seance, Justifier> justifiers) {this.lesSeances = justifiers;}

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Justificatif that = (Justificatif) o;
        return idJ == that.idJ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJ);
    }

    @Override
    public String toString() {
        return "Justificatif{" +
                "idJ='" + idJ + '\'' +
                ", dateDepot=" + dateDepot +
                ", etatValidation='" + etatValidation + '\'' +
                ", linkFile='" + linkFile + '\'' +
                ", nameFile='" + nameFile + '\'' +
                ", scolarite=" + scolarite.toString() +
                ", etudiant=" + etudiant.toString() +
                '}';
    }
}
