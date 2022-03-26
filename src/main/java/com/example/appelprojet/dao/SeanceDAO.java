package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeanceDAO extends DAO<Seance>{
    /*----- Format de date -----*/
    private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    public SeanceDAO() {super.setEntity(Seance.class);}

    public static Seance findSeanceByTimeByIdU(Date date, Long idU){
        Seance seance = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            Query<Seance> query = session.createQuery("from com.example.appelprojet.mertier.Seance s where s.enseignant.idU=:id  and s.dateDebut <:d and s.dateFin > :d");
            query.setParameter("d",new Timestamp(date.getTime()));
            query.setParameter("id", idU);
            if (query.uniqueResult()!=null){
                seance = query.uniqueResult();
            }
            transaction.commit();
            session.close();
        }
        return seance;
    }


    public static List<Seance> findSeanceByUser(Utilisateur utilisateur){
        List<Seance> seances = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            Query<Seance> query = session.createQuery("from com.example.appelprojet.mertier.Seance s where s.enseignant.idU=:id");
            query.setParameter("id", utilisateur.getIdU());
            if (!query.getResultList().isEmpty()){
                seances = query.getResultList();
            }
        }
        return seances;
    }

    public static boolean isFindSeanceActuelByUser(Utilisateur utilisateur){
        boolean find = false;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            Query<Seance> query = session.createQuery("select DISTINCT s from com.example.appelprojet.mertier.Seance s where s.enseignant.idU ='"+ utilisateur.getIdU() + "'and sysdate() >s.dateDebut " +
                            "and sysdate() <s.dateFin ");
            if (!query.getResultList().isEmpty()){
                find = true;
                session.close();
            }
        }
        return find;
    }

    /*----- les information d'une fiche d'appel -----*/
    public static Seance infoFicheAppel(Utilisateur utilisateur) {
        /*----- Ouverture de la session -----*/
        Seance appel = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("from com.example.appelprojet.mertier.Seance s where s.enseignant.idU = ' " + utilisateur.getIdU() + "' " +
                    "and sysdate() >= s.dateDebut " +
                    "and sysdate() <= s.dateFin ");

            if (!query.getResultList().isEmpty()){
                appel = (Seance) query.uniqueResult();
            }
            session.close();
        }
        return appel;
    }


    /*----- les information d'une fiche d'appel -----*/
    public static Seance infoFicheAppelById(long id) {
        /*----- Ouverture de la session -----*/
        Seance appel = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            Query query = session.createQuery("from com.example.appelprojet.mertier.Seance s where s.idSeance = ' " + id + "' " +
                    "and sysdate() >s.dateDebut " +
                    "and sysdate() <s.dateFin ");

            appel = (Seance) query.uniqueResult();
            t.commit();
            session.close();
        }
        return appel;
    }

    /*----- liste des étudiants de la fiche d'appel d'une séance -----*/
    public static List<Etudiant> findEtudiansByID(Seance seance)
    {
        List<Etudiant> etudiants = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            etudiants = session.createQuery("select e " +
                    "from com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Formation f " +
                    "where f.id = c.formation.id " +
                    "and e.formation.idFormation = f.idFormation " +
                    "and s.cours.idCours = c.idCours " +
                    "and s.idSeance = ' " + seance.getIdSeance() + "' " ).list();
            session.close();
        }
        return etudiants;
    }


//    public static Seance findSeanceActuel() {
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction t = session.beginTransaction();
//
//        Query query = session.createQuery(
//                "from com.example.appelprojet.mertier.Seance as s " +
//                        "where s.enseignant.idU = :codeUtilisateur "+
//                        "and sysdate() >s.dateDebut "+
//                        "and sysdate() <s.dateFin "
//        );
//
//        query.setParameter("codeUtilisateur", 4L);
//        query.uniqueResult();
//        System.out.println(query.uniqueResult());
//        Seance se = (Seance) query;
//
//        session.close();
//        return se;
//    }


}
