package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Utilisateur")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Categorie", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("utilisateur")
public class Utilisateur {
//    Prorietes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodeU")
    private long idU;
    private String nomU;
    private String prenomU;
    private String mdp;
    private String email;
    private String tel;

//    Relation


//    Constructeur


    public Utilisateur() {
    }

    public Utilisateur(String nomU, String prenomU, String mdp, String email, String tel) {
        this.nomU = nomU;
        this.prenomU = prenomU;
        this.mdp = mdp;
        this.email = email;
        this.tel = tel;
    }

    public Utilisateur(String prenomU, String mdp, String email, String tel) {
        this.prenomU = prenomU;
        this.mdp = mdp;
        this.email = email;
        this.tel = tel;
    }

//    getter and setter

    public long getIdU() {
        return idU;
    }

    public void setIdU(long idU) {
        this.idU = idU;
    }

    public String getNomU() {
        return nomU;
    }

    public void setNomU(String nomU) {
        this.nomU = nomU;
    }

    public String getPrenomU() {
        return prenomU;
    }

    public void setPrenomU(String prenomU) {
        this.prenomU = prenomU;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentifiant() {
        return tel;
    }

    public void setIdentifiant(String tel) {
        this.tel = tel;
    }

//    toString

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idU='" + idU + '\'' +
                ", nomU='" + nomU + '\'' +
                ", prenomU='" + prenomU + '\'' +
                ", mdp='" + mdp + '\'' +
                ", email='" + email + '\'' +
                ", identifiant='" + tel + '\'' +
                '}';
    }

//    equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(idU, that.idU);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idU);
    }

}
