package com.example.appelprojet.mertier;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class JustifierID implements java.io.Serializable{
    //    Priorietes
    @Column(name = "CodeJ")
    private long codeJ;
    @Column(name = "CodeSE")
    private long codeSE;

    //    Constructeurs
    public JustifierID() {
    }

    public JustifierID(long codeJ, long codeSE) {
        this.codeJ = codeJ;
        this.codeSE = codeSE;
    }

    //    getter and setter

    public long getCodeJ() {return codeJ;}

    public void setCodeJ(long codeJ) {this.codeJ = codeJ;}

    public long getCodeSE() {return codeSE;}

    public void setCodeSE(long codeSE) {this.codeSE = codeSE;}

    //    toString
    @Override
    public String toString() {
        return "JustifierID{" +
                "codeJ=" + codeJ +
                ", codeSE=" + codeSE +
                '}';
    }

    //    equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JustifierID that = (JustifierID) o;
        return codeJ == that.codeJ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codeJ);
    }
}
