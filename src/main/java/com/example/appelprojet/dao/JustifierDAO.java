package com.example.appelprojet.dao;


import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Justifier;
import com.example.appelprojet.util.EtatValidation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import java.util.List;

public class JustifierDAO extends DAO<Justifier>{

    public JustifierDAO() {super.setEntity(Justifier.class);}

    /*----- toutes les justificatifs reçus une scolarité mais pas encore traités dans ce mois -----*/
    public static List<Justifier> lesJustifPasEncoreTraites(long s)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            List<Justifier> liste = session.createQuery("select js " +
                    "from com.example.appelprojet.mertier.Justificatif j, com.example.appelprojet.mertier.Justifier js " +
                    "where j.scolarite.idU = '"+  s + "' " +
                    "and j.idJ = js.justificatif.idJ " +
                    "and month(j.dateDepot) = month(sysdate()) " +
                    "and js.etatValidation = '" + EtatValidation.NULL + "' " +
                    "order by j.dateDepot desc  ").list();

            t.commit();
            session.close();
            for (Justifier a : liste)
                System.out.println(a);
            return liste;

        }
    }
}
