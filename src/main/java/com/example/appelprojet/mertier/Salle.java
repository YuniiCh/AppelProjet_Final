package com.example.appelprojet.mertier;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Salle {
    /*----- Propriétés -----*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CodeS")
    private long idSalle;
    private String nomSalle;


//    Relation
//    seance
    @OneToMany(mappedBy = "salle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapKeyJoinColumn(name = "CodeSE",insertable = false, updatable = false)
    private Set<Seance> seances = new HashSet<>(0);

    /*----- Constructeur -----*/

    public Salle() {
    }

    public Salle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    /*----- Getter/Setter -----*/

    public long getIdSalle() {return idSalle;}
    public void setIdSalle(long idSalle) {this.idSalle = idSalle;}
    public String getNomSalle() {return nomSalle;}
    public void setNomSalle(String nomSalle) {this.nomSalle = nomSalle;}

    @java.lang.Override
    public java.lang.String toString() {
        return "Salle{" +
                "idSalle=" + idSalle +
                ", nomSalle='" + nomSalle + '\'' +
                '}';
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Salle salle = (Salle) object;
        return idSalle == salle.idSalle;
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), idSalle);
    }
}
