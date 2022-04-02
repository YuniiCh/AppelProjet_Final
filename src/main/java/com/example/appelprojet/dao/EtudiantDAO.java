package com.example.appelprojet.dao;


import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.FontionsUtiles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.example.appelprojet.config.HibernateUtil;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EtudiantDAO extends DAO<Etudiant> {
    public EtudiantDAO() {super.setEntity(Etudiant.class);}

    public static List<Etudiant> findByName(String name){
        List<Etudiant> etudiants = null;
        List<String> list  = Arrays.asList(name.split(" "));
        List<String> list_name = new ArrayList(list);
        if (list_name.size() < 2){
            list_name.add("");
            list_name.add("");
            System.out.println("prenom: " + list_name.get(1).toLowerCase() + "%");
        }else if(list_name.size() < 3){
            list_name.add("");
            System.out.println("id: " + list_name.get(2) + "%");
        }else{
            if(list_name.size() == 3){
                if (!FontionsUtiles.isNumber(list_name.get(2))){
                    String nom1 = list_name.get(0);
                    String nom2 = list_name.get(1);
                    String nom3 = list_name.get(2);
                    list_name.add(0,nom1 + " " + nom2);
                    list_name.add(1, nom3);
                    list_name.add("");
                }
            }else{
                StringBuilder nom = new StringBuilder();
                for(int i = 0; i <list_name.size()-2; i++){
                    nom.append(list_name.get(i));
                }
                String prenom = list_name.get(list_name.size()-2);
                String id = list_name.get(list_name.size()-1);
                list_name.clear();
                list_name.add(nom.toString());
                list_name.add(prenom);
                list_name.add(id);
            }
        }

        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()){
            Transaction transaction = getTransaction(session);
            Query query = session.createQuery("from com.example.appelprojet.mertier.Etudiant e where lower(e.nomU) like :nom  and lower(e.prenomU ) like :prenom ");
            query.setParameter("nom",list_name.get(0).toLowerCase() + "%");
            query.setParameter("prenom","%" + list_name.get(1).toLowerCase() + "%" );
//            query.setParameter("id",list_name.get(2) + "%" );
            System.out.println("nom: " + list_name.get(0).toLowerCase() + "%");
            System.out.println("prenom: " + list_name.get(1).toLowerCase() + "%");
            System.out.println("id: " + list_name.get(2) + "%");

            etudiants = query.list();
            transaction.commit();
            session.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    /*----- liste des étudiants de la fiche d'appel d'une séance -----*/
    public static List<Etudiant> findEtudiansByID(Seance seance)
    {
        List<Etudiant> etudiants = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            etudiants = session.createQuery("select p.etudiant from com.example.appelprojet.mertier.Presence p where p.seance.idSeance = '" + seance.getIdSeance() + "' " ).list();
            session.close();
        }
        return etudiants;
    }

    /*----- liste des étudiants du même cours mais pas le même td -----*/
    public static List<Etudiant> etudiantsHorsSeanceByIdSeance(long id)
    { List<Etudiant> etudiants = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            etudiants = session.createQuery("select distinct e " +
                    "from com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Formation f " +
                    "where f.id = c.formation.id " +
                    "and e.formation.idFormation = f.idFormation " +
                    "and f.idFormation = (select f.idFormation " +
                    "from com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Formation f, com.example.appelprojet.mertier.Seance s " +
                    "where c.formation.idFormation = f.idFormation " +
                    "and s.cours.idCours = c.idCours " +
                    "and s.idSeance = ' " + id + "' " + " )" +
                    "and e.idU not in (select e.idU " +
                    "from com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Formation f  " +
                    "where e.formation.idFormation = f.idFormation " +
                    "and f.idFormation = c.formation.idFormation " +
                    "and s.cours.idCours = c.idCours " +
                    "and s.idSeance = '" + id + "' " +
                    "and e.tdGroup = c.typeCours )" ).list();
            for (Etudiant a : etudiants)
                System.out.println(a);

            t.commit();

            session.close();
        }
        return etudiants;
    }


    /*----- liste des étudiants du même cours mais pas le même td -----*/
    public static List<Etudiant> etudiantsMemeCoursBySeance(Seance seance)
    {
        List<Etudiant> etudiants = null;
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            etudiants = session.createQuery("select distinct e from com.example.appelprojet.mertier.Cours c, com.example.appelprojet.mertier.Formation f, com.example.appelprojet.mertier.Etudiant e where f.idFormation=c.formation.idFormation and e.formation.idFormation = f.idFormation and c.nomCours= '" + seance.getCours().getNomCours() + "' " ).list();
            t.commit();
            session.close();
        }
        return etudiants;
    }



    public static void addEtudiantSeanceById(long idEtudiant, long idSeance){
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles séances
            Etudiant etudiant = session.get(Etudiant.class, idEtudiant);
            Seance seance = session.get(Seance.class, idSeance);

            Presence presence = new Presence(EtatPresence.PRESENCE, new PresenceID(seance.getIdSeance(),etudiant.getIdU()), null);
            etudiant.getSeanPresences().put(seance,presence);
            seance.getEtuPresences().put(etudiant,presence);
            presence.setEtudiant(etudiant);
            presence.setSeance(seance);
            t.commit();
            session.close();
        }
    }



    public Scolarite findScoByEtu(Etudiant etudiant) {
        Scolarite scolarite = new Scolarite();
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            Query query = session.createQuery("select s from com.example.appelprojet.mertier.Scolarite s, " +
                    "com.example.appelprojet.mertier.Formation f, com.example.appelprojet.mertier.Etudiant e " +
                    "where e.formation.idFormation = f.idFormation " +
                    "and f.scolarite.idU = s.idU");

            scolarite = (Scolarite) query.uniqueResult();
            t.commit();
            session.close();
        }
        return scolarite;
    }


    /*----- liste étudiants dans un groupe TD d'une foramtion -----*/
    public static List<Etudiant> listeEtudiantDapresFormationTD(String tdGroupe, long idFormaton)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            List<Etudiant> liste = session.createQuery("select e " +
                    "from com.example.appelprojet.mertier.Etudiant e  " +
                    "where e.tdGroup = '"+ tdGroupe + "' " +
                    "and e.formation.idFormation = '" + idFormaton + "' " ).list();

            t.commit();
            session.close();
            for (Etudiant a : liste)
                System.out.println(a);
            return liste;
        }
    }
}
