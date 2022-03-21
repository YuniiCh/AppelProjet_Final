package com.example.appelprojet.mertier;

import java.util.Objects;

public class Utilisateur {
//    Prorietes
    private String idU;
    private String nomU;
    private String prenomU;
    private String mdp;
    private String email;
    private String identifiant;


//    Constructeur


    public Utilisateur() {
    }

    public Utilisateur(String prenomU, String mdp, String email, String identifiant) {
        this.prenomU = prenomU;
        this.mdp = mdp;
        this.email = email;
        this.identifiant = identifiant;
    }

//    getter and setter

    public String getIdU() {
        return idU;
    }

    public void setIdU(String idU) {
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
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
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
                ", identifiant='" + identifiant + '\'' +
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
