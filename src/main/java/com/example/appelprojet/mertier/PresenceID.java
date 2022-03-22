package com.example.appelprojet.mertier;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.util.Objects;

@Embeddable
public class PresenceID implements java.io.Serializable{
//    Priorietes
    @Column(name = "CodeSEA")
    private long codeSEA;
    @Column(name = "CodeET")
    private long codeET;
    @Column(name = "CodeJU")
    private long codeJu;

//    Constructeur


    public PresenceID() {
    }

    public PresenceID(long codeET, long codeJu) {
        this.codeET = codeET;
        this.codeJu = codeJu;
    }

//    getter and setter


    public long getCodeSEA() {
        return codeSEA;
    }

    public void setCodeSEA(long codeSEA) {
        this.codeSEA = codeSEA;
    }

    public long getCodeET() {
        return codeET;
    }

    public void setCodeET(long codeET) {
        this.codeET = codeET;
    }

    public long getCodeJu() {
        return codeJu;
    }

    public void setCodeJu(long codeJu) {
        this.codeJu = codeJu;
    }

//    toString

    @Override
    public String toString() {
        return "PresenceID{" +
                "codeSEA=" + codeSEA +
                ", codeET=" + codeET +
                ", codeJu=" + codeJu +
                '}';
    }

//    equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PresenceID that = (PresenceID) o;
        return codeSEA == that.codeSEA && codeET == that.codeET && codeJu == that.codeJu;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeSEA, codeET, codeJu);
    }
}
