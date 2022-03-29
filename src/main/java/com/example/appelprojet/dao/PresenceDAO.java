package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Presence;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.util.EtatPresence;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PresenceDAO extends DAO<Presence>{
    public PresenceDAO() {super.setEntity(Presence.class);}

    /*----- une liste doit être insérée dans la table presence -----*/
    public static List geneTablePresence()
    {
        List presences = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
//            Transaction t = session.beginTransaction();
            Query q = session.createQuery("select s.idSeance, e.idU " +
                    "from com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Seance s , com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.Formation f  " +
                    "where e.formation.idFormation = f.idFormation " +
                    "and f.idFormation = c.formation.idFormation " +
                    "and s.cours.idCours = c.idCours " +
                    "and (e.tdGroup = c.typeCours or c.typeCours = 'cm') ");

            if (!q.getResultList().isEmpty()){
                presences = q.getResultList();
            }

            affichage(q.getResultList());

//            t.commit();
//            session.close();
        }
        return presences;
    }


    /*----- mettre à jour l'etatPresence d'un étudiant dans une séance-----*/
    public static void updateEtatPreEtu (EtatPresence etatPresence, Etudiant e, Seance s)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Presence p = e.getSeanPresences().get(s);
            p.setEtatPresence(etatPresence.toString());

            session.update(p);

            t.commit();
            session.close();
        }
    }

}

//    /*----- une liste doit être insérée dans la table presence -----*/
//    public static void geneTablePresence()
//    {
//        /*----- Ouverture de la session -----*/
//        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
//            Transaction t = session.beginTransaction();
//
//            Query q = session.createQuery("select s.idSeance, e.idU, c.nomCours, c.typeCours " +
//                    "from com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Seance s , com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.Formation f  " +
//                    "where e.formation.idFormation = f.idFormation " +
//                    "and f.idFormation = c.formation.idFormation " +
//                    "and s.cours.idCours = c.idCours " +
//                    "and (e.tdGroup = c.typeCours or c.typeCours = 'cm') ");
//
//            q.getResultList();
//            affichage(q.getResultList());
//
//            t.commit();
//            session.close();
//        }
//    }
//}
