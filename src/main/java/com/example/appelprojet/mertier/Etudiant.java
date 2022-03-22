package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@DiscriminatorValue("etudiant")
public class Etudiant extends Utilisateur{
//    Priorietes

//  Relation
//    Appartenir formation
    @ManyToMany(mappedBy = "etudiants")
    private Set<Formation> formations = new HashSet<>(0);
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

    public Etudiant(String nomU, String prenomU, String mdp, String email, String identifiant) {
        super(nomU, prenomU, mdp, email, identifiant);
    }

//    getter and setter

    public Set<Formation> getFormations() {
        return formations;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }

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
