package com.example.appelprojet.mertier;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("scolarite")
public class Scolarite extends Utilisateur{
    //    Priorietes

    //    Relations
    @OneToMany(mappedBy = "scolarite", fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    private Set<Justificatif> justificatifs = new HashSet(0);

    //    Contructeur
    public Scolarite() {
    }

    public Scolarite(String prenomU, String mdp, String email, String identifiant) {
        super(prenomU, mdp, email, identifiant);
    }


    //    getter and setter

    public Set<Justificatif> getJustificatifs() {
        return justificatifs;
    }

    public void setJustificatifs(Set<Justificatif> justificatifs) {
        this.justificatifs = justificatifs;
    }

    //    toString

    //    equals and hashCode
    public int hashCode() {return super.hashCode();}

    @Override
    public boolean equals(Object o) {return super.equals(o);}
}
