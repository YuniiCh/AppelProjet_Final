package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("enseignant")
public class Enseignant extends Utilisateur{
    //    Priorietes


    //    Relation
    //    seances
    @OneToMany (mappedBy = "enseignant", fetch = FetchType.LAZY)
    private Set<Seance>  seances = new HashSet<>(0);

    //    Contructeur

    public Enseignant() {
    }

    public Enseignant(String nomU,String prenomU, String mdp, String email, String identifiant) {
        super(nomU, prenomU, mdp, email, identifiant);
    }


    //    getter and setter

    public Set<Seance> getSeances() {return seances;}

    public void setSeances(Set<Seance> seances) {this.seances = seances;}


    //    toString

    //    equals and hashCode

}
