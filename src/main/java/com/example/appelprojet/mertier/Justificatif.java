package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.*;

@Entity
public class Justificatif {

    // Propriétés
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeJ")
    private String idJ;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDepot;

    private String etatValidation;
    private String linkFile;
    private String nameFile;

    // Relations
//    Scolarite
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CodeU")
    private Scolarite scolarite;
//    Seances-Presences
//    Seance, presence
@OneToMany(mappedBy = "justificatif")
@MapKeyJoinColumn(name = "CodeSE", updatable = false, insertable = false)
private Map<Seance,Presence> seanPresences = new HashMap(0);

//    Etudiant, presence
    @OneToMany(mappedBy = "justificatif", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeE", updatable = false, insertable = false)
    private Map<Etudiant,Presence> etudiantPresences = new HashMap(0);

////    Etudiant
//@ManyToOne(fetch = FetchType.EAGER)
//@JoinColumn(name = "CodeU", insertable = false, updatable = false)
//private Etudiant etudiant;

    // Constructors
    public Justificatif() {
    }

//    public Justificatif(Date dateDepot, String etatValidation, String linkFile, String nameFile, Scolarite scolarite) {
//        this.dateDepot = dateDepot;
//        this.etatValidation = etatValidation;
//        this.linkFile = linkFile;
//        this.nameFile = nameFile;
//        this.scolarite = scolarite;
//    }


    public Justificatif(Date dateDepot, String etatValidation, String linkFile, String nameFile, Scolarite scolarite) {
        this.dateDepot = dateDepot;
        this.etatValidation = etatValidation;
        this.linkFile = linkFile;
        this.nameFile = nameFile;
        this.scolarite = scolarite;
        this.seanPresences = seanPresences;
//        this.etudiant = etudiant;
    }

    // Getter / Setter
    public String getIdJ() {return idJ;}
    public void setIdJ(String idJ) {this.idJ = idJ;}
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

    public Map<Seance, Presence> getSeanPresences() {
        return seanPresences;
    }

    public void setSeanPresences(Map<Seance, Presence> seanPresences) {
        this.seanPresences = seanPresences;
    }

    public Map<Etudiant, Presence> getEtudiantPresences() {
        return etudiantPresences;
    }

    public void setEtudiantPresences(Map<Etudiant, Presence> etudiantPresences) {
        this.etudiantPresences = etudiantPresences;
    }

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

    @Override
    public String toString() {
        return "Justificatif{" +
                "idJ='" + idJ + '\'' +
                ", dateDepot=" + dateDepot +
                ", etatValidation='" + etatValidation + '\'' +
                ", linkFile='" + linkFile + '\'' +
                ", nameFile='" + nameFile + '\'' +
                ", scolarite=" + scolarite +
                '}';
    }
}
