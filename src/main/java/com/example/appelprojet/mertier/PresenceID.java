package com.example.appelprojet.mertier;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.util.Objects;

@Embeddable
public class PresenceID implements java.io.Serializable{
    //    Priorietes
    @Column(name = "CodeSE")
    private long codeSE;
    @Column(name = "CodeU")
    private long codeU;


    //    Constructeur
    public PresenceID() {
    }

    public PresenceID(long codeSE, long codeU) {
        this.codeSE = codeSE;
        this.codeU = codeU;
    }

    //    getter and setter

    public long getCodeSEA() {
        return codeSE;
    }

    public void setCodeSEA(long codeSEA) {
        this.codeSE = codeSEA;
    }

    public long getCodeET() {
        return codeU;
    }

    public void setCodeET(long codeET) {
        this.codeU = codeET;
    }


//    toString

    @Override
    public String toString() {
        return "PresenceID{" +
                "codeSEA=" + codeSE +
                ", codeET=" + codeU +
                '}';
    }

//    equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PresenceID that = (PresenceID) o;
        return codeSE == that.codeSE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeSE);
    }
}