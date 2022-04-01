package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;
import com.example.appelprojet.util.EtatPresence;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CoursDAO extends DAO<Cours> {
    public CoursDAO() {
        super.setEntity(Cours.class);
    }

    /*----- les cours d'une enseigant -----*/
    public static List<Cours> findCoursByEnseignant(Utilisateur utilisateur) {
        /*----- Ouverture de la session -----*/
        List<Cours> cours = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            cours = session.createQuery("select distinct c from com.example.appelprojet.mertier.Seance s , com.example.appelprojet.mertier.Cours c where c.idCours = s.cours.idCours and s.enseignant.idU = '" + utilisateur.getIdU() + "' ").list();
            t.commit();
            session.close();
        }
        return cours;
    }


    /*----- les cours d'id d'une enseigant -----*/
    public static List<Cours> findCoursByEnseignantId(long id) {
        /*----- Ouverture de la session -----*/
        List<Cours> cours = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            cours = session.createQuery("select distinct c from com.example.appelprojet.mertier.Seance s , com.example.appelprojet.mertier.Cours c where c.idCours = s.cours.idCours and s.enseignant.idU = '" + id + "' order by c.idCours").list();
            t.commit();
            session.close();
        }
        return cours;
    }

    /*----- les cours d'id d'une enseigant -----*/
    public static List<Cours> findCoursByEtudiantId(long id) {
        /*----- Ouverture de la session -----*/
        List<Cours> cours = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            cours = session.createQuery("select distinct s.cours from com.example.appelprojet.mertier.Seance s , com.example.appelprojet.mertier.Presence p where s.idSeance=p.seance.idSeance and p.etudiant.idU = '" + id + "' order by s.cours.idCours").list();
            t.commit();
            session.close();
        }
        return cours;
    }
    /*----- le nombre d'absences d'un cours pour un étudiant -----*/
    public static int nbAbsCoursEtudiant (long e, long c)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Query q = session.createQuery("select count(p.etatPresence) " +
                    "from com.example.appelprojet.mertier.Presence p " +
                    "where p.idPresence.codeU = '"+  e + "' " +
                    "and (p.etatPresence = '"+EtatPresence.ABSENCE + "' or p.etatPresence = '"+EtatPresence.ABSENCE_JUSTIFIE+ "' or p.etatPresence = '"+EtatPresence.ABSENCE_NON_JUSTIFIE +"' " +
                    "or p.etatPresence = '"+EtatPresence.ABSENCE_SIGNALE + "' ) " +
                    "and p.idPresence.codeSE in (select s.idSeance " +
                    "from com.example.appelprojet.mertier.Seance s " +
                    "where s.idSeance = '"+  c + "' and sysdate() >= s.dateDebut) ")  ;

            q.uniqueResult();
            System.out.println(q.uniqueResult());

            int a = ((Long)q.uniqueResult()).intValue();

            t.commit();
            session.close();
            return a;
        }
    }
    /*----- le nombre des Presences d'un cours -----*/
    public static int nbPresenceUnCours(long idEn, long idCours) {
        int nbPresence = 0;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Query q = session.createQuery("select count(p.etatPresence) " +
                    "from com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Seance s " +
                    "where s.cours.idCours = '"+  idCours + "' " +
                    "and p.idPresence.codeSE = s.idSeance " +
                    "and p.seance.enseignant.idU='" + idEn +"' " +
                    "and p.etatPresence like '%"+ EtatPresence.PRESENCE + "%' and sysdate() >= s.dateDebut ")  ;

            nbPresence = ((Long)q.uniqueResult()).intValue();
            System.out.println(q.uniqueResult());

            t.commit();
            session.close();
        }
        return nbPresence;
    }

    /*----- le nombre des retards d'un cours -----*/
    public static int nbRetardUnCours(long idEn, long idCours){
        int nbPresence = 0;
        /*----- Ouverture de la session -----*/
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Query q = session.createQuery("select count(p.etatPresence) " +
                    "from com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Seance s " +
                    "where s.cours.idCours = '"+  idCours + "' " +
                    "and p.idPresence.codeSE = s.idSeance " +
                    "and p.seance.enseignant.idU='" + idEn +"' " +
                    "and p.etatPresence = '"+ EtatPresence.RETART + "'  and sysdate() >= s.dateDebut")  ;

            nbPresence = ((Long)q.uniqueResult()).intValue();
            System.out.println(q.uniqueResult());

            t.commit();
            session.close();
        }
        return nbPresence;
    }

    /*----- le nombre des absences d'un cours -----*/
    public static int nbAbsenceUnCours(long idEn, long idCours)
    {
        int nbPresence = 0;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Query q = session.createQuery("select count(p.etatPresence) " +
                    "from com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Seance s " +
                    "where s.cours.idCours = '"+  idCours + "' " +
                    "and p.idPresence.codeSE = s.idSeance " +
                    "and p.seance.enseignant.idU='" + idEn +"' " +
                    "and p.etatPresence like '%"+ EtatPresence.ABSENCE + "%'  and sysdate() >= s.dateDebut")  ;

            nbPresence = ((Long)q.uniqueResult()).intValue();
            System.out.println(q.uniqueResult());

            t.commit();
            session.close();
        }
        return nbPresence;
    }

    /*----- le nombre d'absences presences retards d'un cours pour un étudiant -----*/
    public static int nbPresenceCoursEtudiant (long idU, long idC)
    {
        int nbPresence = 0;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("select count(p.etatPresence) " +
                    "from com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Seance s " +
                    "where s.cours.idCours = '"+  idC + "' " +
                    "and p.idPresence.codeSE = s.idSeance " +
                    "and p.etudiant.idU='" + idU +"' " +
                    "and p.etatPresence = '"+ EtatPresence.PRESENCE + "'  and sysdate() >= s.dateDebut");

            nbPresence = ((Long)q.uniqueResult()).intValue();
            System.out.println(q.uniqueResult());

            t.commit();
            session.close();
        }
        return nbPresence;
    }

    /*----- le nombre d'absences presences retards d'un cours pour un étudiant -----*/
    public static int nbRetardCoursEtudiant (long idU, long idC)
    {
        int nb= 0;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("select count(p.etatPresence) " +
                    "from com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Seance s " +
                    "where s.cours.idCours = '"+  idC + "' " +
                    "and p.idPresence.codeSE = s.idSeance " +
                    "and p.etudiant.idU='" + idU +"' " +
                    "and p.etatPresence = '"+ EtatPresence.RETART + "' and sysdate() >= s.dateDebut");

            nb = ((Long)q.uniqueResult()).intValue();
            System.out.println(q.uniqueResult());

            t.commit();
            session.close();
        }
        return nb;
    }

    /*----- le nombre d'absences presences retards d'un cours -----*/
    public static int nbAbsenceCoursEtudiant (long idU, long idC)
    {
        int nb= 0;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query q = session.createQuery("select count(p.etudiant) " +
                    "from com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Seance s " +
                    "where s.cours.idCours = '"+  idC + "' " +
                    "and p.idPresence.codeSE = s.idSeance " +
                    "and p.etudiant.idU='" + idU +"' " +
                    "and p.etatPresence like '%"+ EtatPresence.ABSENCE + "%' and sysdate() >= s.dateDebut");

            nb = ((Long)q.uniqueResult()).intValue();

            t.commit();
            session.close();
        }
        return nb;
    }

    public static List<Long> nbAbsPreRedCoursEtudiant (long e, long c)
    {
        List<Long> countPresence = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Query q = session.createSQLQuery("select p.seance.getIdSeance, SUM(CASE WHEN p.etatpresence like 'ABSENCE%' THEN 1 ELSE 0 END) as nbAbsences, " +
                    "SUM(CASE WHEN p.etatpresence = 'PRESENCE' THEN 1 ELSE 0 END) as nbPresences, " +
                    "SUM(CASE WHEN p.etatpresence = 'RETARD' THEN 1 ELSE 0 END) as nbRetards " +
                    "from Presence p " +
                    "where p.codeU = 7 " +
                    "and p.codeSE in (select codeSE " +
                    "from seance " +
                    "where CodeC = 1)");

            countPresence = (List<Long>) q.uniqueResult();
            System.out.println(q.uniqueResult());

            t.commit();
            session.close();
        }
        return countPresence;
    }



}
