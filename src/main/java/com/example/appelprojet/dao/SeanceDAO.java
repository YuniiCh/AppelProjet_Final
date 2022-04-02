package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Planning;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.FontionsUtiles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class SeanceDAO extends DAO<Seance>{
    /*----- Format de date -----*/
    private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final SimpleDateFormat SDP_FR = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat SDF_OLD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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

    public static List<Seance> findSeanceByIdUserCours(long idU, long idC){
        List<Seance> seances = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            seances = session.createQuery("from com.example.appelprojet.mertier.Seance s where s.enseignant.idU='"+idU+"' and s.cours.idCours='"+ idC+"'").list();
            System.out.println("Seance not null ");
            transaction.commit();
            session.close();
        }
        return seances;
    }

    public static List<Seance> findSeanceByEtu(Etudiant etudiant){
        List<Seance> seances = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            Query<Seance> query = session.createQuery("select s from com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Etudiant e where s.idSeance = p.idPresence.codeSE and p.idPresence.codeU = :id");
            query.setParameter("id", etudiant.getIdU());
            if (!query.getResultList().isEmpty()){
                seances = query.getResultList();
            }
        }
        return seances;
    }

    public static List<Seance> findAbsenceByEtu(Etudiant etudiant){
        List<Seance> seances = null;
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            Query<Seance> query = session.createQuery("select s from com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Presence p, com.example.appelprojet.mertier.Etudiant e where s.idSeance = p.idPresence.codeSE and p.idPresence.codeU = :id and p.etatPresence = 'ABSENCE'");
            query.setParameter("id", etudiant.getIdU());
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
                appel = (Seance) query.getResultList().get(0);
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

    /*----- Toutes les séances d'une semaine -----*/
    public static List<Seance> findSeancesSemaine(Utilisateur utilisateur, Date date) {
        /*----- Ouverture de la session -----*/
        List<Seance> seances = null;
        Planning planning = new Planning(date);
        Calendar calendar = Calendar.getInstance();
        Date monday = planning.weekDate.get(0);
        Date sunday = planning.weekDate.get(6);
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            Query query = session.createQuery("from com.example.appelprojet.mertier.Seance s where s.enseignant.idU = :id " +
                    "and s.dateDebut between :monday and :sunday");
            query.setParameter("id", utilisateur.getIdU());
            query.setParameter("monday", monday);
            query.setParameter("sunday", sunday);
            if (!query.getResultList().isEmpty()){
                seances = (List<Seance>) query.list();
            }
            t.commit();
            session.close();
        }
        return seances;
    }


    /*----- mettre à jour l'etatAppel d'une séance-----*/
    public static void updateEtatAppelBySeance (Seance s, String action)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Seance seance = session.get(Seance.class,s.getIdSeance());
            seance.setEtatAppel(action);
            session.update(seance);
            System.out.println( action + " Secessful!");
            t.commit();
            session.close();
        }catch (Exception ignored){
            System.out.println(action + " Failed !");
        }
    }

    /*----- toutes les séances absence d'un étudiant dans ce mois et pas de justificatif pour ces absences-----*/
    public static List<Seance> tousAbsEtudiantCeMois ( long e)
    {
        List<Seance> liste = new ArrayList<>();
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            liste = session.createQuery("select s " +
                    "from com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Presence p " +
                    "where s.idSeance = p.idPresence.codeSE " +
                    "and p.idPresence.codeU = '"+  e + "' " +
                    "and month(s.dateDebut)=month(sysdate()) " +
                    "and year(s.dateDebut)=year(sysdate()) " +
                    "and p.etatPresence = '"+ EtatPresence.ABSENCE + "'" +
                    "and s.idSeance not in (select distinct js.seance.idSeance " +
                    "from com.example.appelprojet.mertier.Justifier js, com.example.appelprojet.mertier.Justificatif j " +
                    "where j.idJ = js.justificatif.idJ " +
                    "and j.etudiant.idU = '" + e + "' ) " ).list();

            for (Seance a : liste)
                System.out.println(a);

            t.commit();
            session.close();
        }
        return liste;
    }


    /*----- obtenir la numéro de séance d'un cours d'après identificant d'une séance -----*/
    public static Integer getNumSeances(long id) {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            List<Seance> q = session.createQuery("select s " +
                    "from com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Cours c " +
                    "where s.cours.idCours = c.idCours " +
                    "and c.idCours = (select c1.idCours " +
                    "from com.example.appelprojet.mertier.Seance s1, com.example.appelprojet.mertier.Cours c1 " +
                    "where s1.cours.idCours = c1.idCours " +
                    "and s1.idSeance = '" + id + "') " +
                    "order by s.dateDebut asc ").list();

            for (int i = 0; i < q.size(); i++) {
                if (id == q.get(i).getIdSeance()) {
                    return i+1;

                }
            }
            t.commit();
            session.close();
        }
        return null;
    }

    /*----- récupérer les séance d'un cours d'après le nom de cour, le type de cours et le nom de formation -----*/
    public static List<Seance> getSeancesDapresFormationNomCTypeC(String nomCours, String typeCours, String nomFormation)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            List<Seance> liste = session.createQuery("select s " +
                    "from com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Cours c " +
                    "where s.cours.idCours = c.idCours " +
                    "and c.nomCours = '" + nomCours + "' " +
                    "and c.typeCours = '" + typeCours + "' " +
                    "and c.formation.nomFormation = '" + nomFormation + "' " +
                    "and s.dateFin < sysdate() " ).list();

            t.commit();
            session.close();
            for (Seance a : liste)
                System.out.println(a);
            return liste;
        }
    }


}
