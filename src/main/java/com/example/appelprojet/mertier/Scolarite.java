package com.example.appelprojet.mertier;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("scolarite")
public class Scolarite extends Utilisateur{
    //    Priorietes

    //    Relations
    @OneToMany(mappedBy = "scolarite", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Demande> demandes = new HashSet(0);

    //    Contructeur
    public Scolarite() {
    }

    public Scolarite(String prenomU, String mdp, String email, String identifiant) {
        super(prenomU, mdp, email, identifiant);
    }


    //    getter and setter

    //    toString

    //    equals and hashCode
    public int hashCode() {return super.hashCode();}

    @Override
    public boolean equals(Object o) {return super.equals(o);}
}
