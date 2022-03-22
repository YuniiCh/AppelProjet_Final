package com.example.appelprojet.mertier;

import com.example.appelprojet.util.TypeEtudiant;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@DiscriminatorValue("etudiant")
public class Etudiant extends Utilisateur{
    //    Priorietes
    @Enumerated(EnumType.STRING)
    private TypeEtudiant typeEtudiant;

    private String tdGroup;

    //  Relation
    //  Appartenir formation
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFormation")
    private Formation formation ;

    //    Seance, presence
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeSE", updatable = false, insertable = false)
    private Map<Seance,Presence> seanPresences = new HashMap(0);

    //    Justificatif, presence
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeJ", updatable = false, insertable = false)
    private Map<Justificatif,Presence> justPresences = new HashMap(0);


   //    Contructeur
    public Etudiant() {
    }

    public Etudiant(String nomU, String prenomU, String mdp, String email, String identifiant, TypeEtudiant typeEtudiant,String tdGroup, Formation formation) {
        super(nomU, prenomU, mdp, email, identifiant);
        this.typeEtudiant = typeEtudiant;
        this.tdGroup = tdGroup;
        this.formation = formation;
    }


    //    getter and setter
    public String getTdGroup() {return tdGroup;}

    public void setTdGroup(String tdGroup) {this.tdGroup = tdGroup;}

    public Formation getFormation() {return formation;}

    public void setFormation(Formation formation) {this.formation = formation;}

    public Map<Seance, Presence> getSeanPresences() {
        return seanPresences;
    }

    public void setSeanPresences(Map<Seance, Presence> seanPresences) {
        this.seanPresences = seanPresences;
    }

    public Map<Justificatif, Presence> getJustPresences() {
        return justPresences;
    }

    public void setJustPresences(Map<Justificatif, Presence> justPresences) {
        this.justPresences = justPresences;
    }

    //    toString

    //    equals and hashCode

}
