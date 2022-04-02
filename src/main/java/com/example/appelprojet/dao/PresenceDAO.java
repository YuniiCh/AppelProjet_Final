package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Presence;
import com.example.appelprojet.mertier.PresenceID;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.util.EtatPresence;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

public class PresenceDAO extends DAO<Presence>{
    public PresenceDAO() {super.setEntity(Presence.class);}

    /*----- une liste doit être insérée dans la table presence -----*/
    public static List geneTablePresence()
    {
        List presences = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("select s.idSeance, e.idU " +
                    "from com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Seance s , com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.Formation f  " +
                    "where e.formation.idFormation = f.idFormation " +
                    "and f.idFormation = c.formation.idFormation " +
                    "and s.cours.idCours = c.idCours " +
                    "and (lower(e.tdGroup) = lower(c.typeCours) or lower( c.typeCours) = 'cm') ");

            if (!q.getResultList().isEmpty()){
                presences = q.getResultList();
            }

            affichage(q.getResultList());

            t.commit();
            session.close();
        }
        return presences;
    }

    /*----- liste des étudiants de la fiche d'appel d'une séance -----*/
    public static List<Presence> findPresenceBySeance(Seance seance)
    {
        List<Presence> presences = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            presences = session.createQuery("select p from com.example.appelprojet.mertier.Presence p where p.seance.idSeance = '" + seance.getIdSeance() + "' " ).list();
            t.commit();
            session.close();
        }
        return presences;
    }



    /*----- liste des étudiants de la fiche d'appel d'une séance -----*/
    public static List<Presence> findPresenceByIdSeance(Long id)
    {
        List<Presence> presences = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            presences = session.createQuery("select p from com.example.appelprojet.mertier.Presence p where p.seance.idSeance = '" + id + "' " ).list();
            t.commit();
            session.close();
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
            p.setEtatPresence(etatPresence);

            session.update(p);

            t.commit();
            session.close();
        }
    }

    /*----- mettre à jour l'etatPresence d'un étudiant dans une séance-----*/
    public static void updateEtatPreEtuByID (EtatPresence etatPresence, long e1, long s1)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Seance seance = session.get(Seance.class,s1);
            Etudiant e = session.get(Etudiant.class,e1);
            Presence p = e.getSeanPresences().get(seance);
            p.setEtatPresence(etatPresence);
            session.update(p);
            System.out.println("Update Secessful!");
            t.commit();
            session.close();
        }catch (Exception ignored){
            System.out.println("Update Failed !");
        }
    }


    /*----- supprimer une ligne presence pour un étudiant et une séance-----*/
    public static void supprEtuPresence (Etudiant e, Seance s)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Presence p = session.get(Presence.class,new PresenceID(s.getIdSeance(),e.getIdU()));

            session.delete(p);

            t.commit();
            session.close();
        }
    }


    /*----- supprimer une ligne presence pour un étudiant et une séance-----*/
    public static void supprEtuPresenceById ( long e, long s)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Presence p = session.get(Presence.class,new PresenceID(s,e));

            session.delete(p);

            t.commit();
            session.close();
        }
    }


}

