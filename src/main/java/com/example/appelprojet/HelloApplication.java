package com.example.appelprojet;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.*;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.FontionsUtiles;
import com.example.appelprojet.util.TypeEtudiant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.text.StyleConstants;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    /*----- Format de date -----*/
    private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    /**
     * Création, enregistrement et lecture d'objets.
     */


    /*----- Création et enregistrement des utilisateurs -----*/
    public static void enrgUtilisateur ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles employées
            Enseignant e1 = new Enseignant("RAVAT ", "Franck", "frank","Franck.Ravat@ut-capitole.fr","100");
            Enseignant e2 = new Enseignant("PERRUSSEL", "Laurent","lurent","lurent@ut-capitole.fr","101");
            Enseignant e3 = new Enseignant("DOUTRE", "Sylvie","sylvie","Sylvie.Doutre@ut-capitole.fr","102");
            Enseignant en1 = new Enseignant("VALLES ", "Nathalie","nathalie","nathalie.valles-parlangeau@ut-capitole.fr","103");
            Enseignant en2 = new Enseignant("BERRO ", "Alain","alain", "Alain.Berro@ut-capitole.fr","104");
            Enseignant en3 = new Enseignant("BOUR ", "Raphaelle","raphaelle", "Raphaelle.Bour@ut-capitole.fr","105");
            // Formation
            Formation f1 = session.get(Formation.class, 1L);
            Formation f2 = session.get(Formation.class, 2L);
            Etudiant et1 = new Etudiant("Doutre", "Aline","aline","aline@gmail.com","105", TypeEtudiant.FI, "TD1", f1);
            Etudiant et2 = new Etudiant("CHEN", "Yuni","yuni","yuni@gmail.com","107", TypeEtudiant.FI, "TD1", f1);
            Etudiant et3 = new Etudiant("LI", "Jiayin","jiayin","jiayin@gmail.com","108", TypeEtudiant.FI, "TD1", f1);
            Etudiant et4 = new Etudiant("ZHAO", "Mengying","mengying","mengying@gmail.com","109", TypeEtudiant.FI, "TD1", f1);
            Etudiant et5 = new Etudiant("CLAUDEL", "Frank","frank","frank@gmail.com","110", TypeEtudiant.FI, "TD2", f1);
            Etudiant et6 = new Etudiant("LI", "Shuanghong","shuanghong","shuanghong@com","111", TypeEtudiant.FI, "TD2", f1);
            Etudiant et7 = new Etudiant("LIU", "Tong","tong","tong.liu@gmail.com","112", TypeEtudiant.FA, "TD2", f1);
            Etudiant et8 = new Etudiant("ZHOU", "Zhiyi","zhiyi","zhiyi@gmail.com","113", TypeEtudiant.FI, "TD1", f2);
            Scolarite et9 = new Scolarite("CYN", "cyn","cyn@com","112");

            session.save(e1);
            session.save(e2);
            session.save(e3);
            session.save(en1);
            session.save(en2);
            session.save(en3);
            session.save(et1);
            session.save(et2);
            session.save(et3);
            session.save(et4);
            session.save(et5);
            session.save(et6);
            session.save(et7);
            session.save(et8);
            session.save(et9);
            f1.setScolarite(session.get(Scolarite.class, 15L));
            f2.setScolarite(session.get(Scolarite.class, 15L));

            t.commit(); // Commit et flush automatique de la session.
        }
    }

    /*----- Création et enregistrement des formations -----*/
    public static void enrgFormation ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            Scolarite s1 = session.get(Scolarite.class, 1L);

            // Création des nouvelles séances
            Formation f1 = new Formation("M2 IPM",s1);
            Formation f2 = new Formation("M2 ISIAD",s1);

            session.save(f1);
            session.save(f2);

            t.commit(); // Commit et flush automatique de la session.
        }
    }

    /*----- Création et enregistrement des salles -----*/
    public static void enrgSalle ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles séances
            Salle s1 = new Salle("ME401");
            Salle s2 = new Salle("ME402");
            Salle s3 = new Salle("ME403");
            Salle s4 = new Salle("ME404");
            Salle s5 = new Salle("ME405");


            session.save(s1);
            session.save(s2);
            session.save(s3);
            session.save(s4);
            session.save(s5);

            t.commit(); // Commit et flush automatique de la session.
        }
    }


    /*----- Création et enregistrement des cours -----*/
    public static void enrgCours ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles cours
            Formation f1 = session.get(Formation.class,1l);
            Formation f2 = session.get(Formation.class,2l);

            Cours c1 = new Cours("Développement d'applications internet", "CM", f1);
            Cours c2 = new Cours("Développement d'applications internet", "TD1", f1);
            Cours c3 = new Cours("Développement d'applications internet", "TD2", f1);
            Cours c4 = new Cours("Management agile", "CM", f1);
            Cours c5 = new Cours("Management agile", "TD1", f1);
            Cours c6 = new Cours("Management agile", "TD2", f1);
            Cours c7 = new Cours("Transformation du SI", "CM", f1);
            Cours c8 = new Cours("Accompagnement client", "CM", f1);
            Cours c9 = new Cours("Big Data","CM",f1);
            Cours c10 = new Cours("Big Data","TD1",f1);
            Cours c11 = new Cours("Big Data","TD2",f1);
            Cours c12 = new Cours("Big Data","CM",f2);
            Cours c13 = new Cours("Big Data","TD1",f2);
            Cours c14 = new Cours("Big Data","TD2",f2);
            Cours c15 = new Cours("Communication","CM",f1);
            Cours c16 = new Cours("Communication","TD1",f1);
            Cours c17 = new Cours("Communication","TD2",f1);
            Cours c18 = new Cours("Anglais","CM",f1);
            Cours c19 = new Cours("Anglais","TD1",f1);
            Cours c20 = new Cours("Anglais","TD2",f1);
            Cours c21 = new Cours("Démarche de développement agile","CM",f1);
            Cours c22 = new Cours("Démarche de développement agile","TD1",f1);
            Cours c23 = new Cours("Démarche de développement agile","CM",f2);
            Cours c24 = new Cours("Démarche de développement agile","TD1",f2);
            Cours c25 = new Cours("Démarche de développement agile","TD2",f2);
            Cours c26 = new Cours("Programmation","CM",f1);
            Cours c27 = new Cours("Programmation","TD1",f1);
            Cours c28 = new Cours("Programmation","TD2",f1);

            session.save(c1);
            session.save(c2);
            session.save(c3);
            session.save(c4);
            session.save(c5);
            session.save(c6);
            session.save(c7);
            session.save(c8);
            session.save(c9);
            session.save(c10);
            session.save(c11);
            session.save(c12);
            session.save(c13);
            session.save(c14);
            session.save(c15);
            session.save(c16);
            session.save(c17);
            session.save(c18);
            session.save(c19);
            session.save(c20);
            session.save(c21);
            session.save(c22);
            session.save(c23);
            session.save(c24);
            session.save(c25);
            session.save(c26);
            session.save(c27);
            session.save(c28);

            t.commit(); // Commit et flush automatique de la session.
        }
    }


    /*----- Création et enregistrement des seances -----*/
    public static void enrgSeances ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Cours c1 = session.get(Cours.class,9L);
            Cours c2 = session.get(Cours.class,10L);
            Cours c3 = session.get(Cours.class,11L);
            Cours c4 = session.get(Cours.class,12L);
            Cours c5 = session.get(Cours.class,4L);
            Cours c6 = session.get(Cours.class,5L);
            Cours c7 = session.get(Cours.class,21L);
            Cours c8 = session.get(Cours.class,22L);
            Cours c9 = session.get(Cours.class,23L);
            Cours c10 = session.get(Cours.class,24L);
            Cours c11 = session.get(Cours.class,1L);
            Cours c12 = session.get(Cours.class,2L);
            Cours c13 = session.get(Cours.class,8L);

//
//            //Enseignant
            Enseignant en1 = session.get(Enseignant.class, 1L);
            Enseignant en2 = session.get(Enseignant.class, 4L);
            Enseignant en3 = session.get(Enseignant.class, 5L);
            Enseignant en4 = session.get(Enseignant.class, 6L);

//
//            //Salle
            Salle salle1 = session.get(Salle.class, 1L);
            Salle salle2 = session.get(Salle.class, 2L);
            Salle salle3 = session.get(Salle.class, 3L);
            Salle salle4 = session.get(Salle.class, 4L);
            Salle salle5 = session.get(Salle.class, 5L);


            //Création des séances
//            Seance se1 = new Seance(DF.parse("15-03-2021 09:30"), DF.parse("15-03-2021 12:30"), "false",c1,en1,s1);
//            Seance se2 = new Seance(DF.parse("16-03-2021 09:30"), DF.parse("16-03-2021 12:30"), "false",c2,en1,s2);
//            Seance se3 = new Seance(DF.parse("16-03-2021 14:00"), DF.parse("16-03-2021 17:00"), "false",c3,en2,s3);
//            Seance se4 = new Seance(DF.parse("17-03-2021 09:30"), DF.parse("17-03-2021 12:30"), "false",c2,en2,s1);
//            Seance se5 = new Seance(DF.parse("23-03-2022 12:00"), DF.parse("23-03-2022 19:00"), "false",c2,en1,s3);
//            Seance se6 = new Seance(DF.parse("24-03-2022 10:10"), DF.parse("24-03-2022 12:30"), "false",c3,en2,s1);
//            Seance se7 = new Seance(DF.parse("24-03-2022 12:00"), DF.parse("24-03-2022 19:00"), "false",c3,en2,s3);
//            Seance se8 = new Seance(DF.parse("24-03-2022 19:00"), DF.parse("25-03-2022 19:00"), "false",c3,en2,s3);
//            Seance se9 = new Seance(DF.parse("25-03-2022 19:00"), DF.parse("30-03-2022 22:00"), "false",c3,en2,s3);
//            Seance se10 = new Seance(DF.parse("28-03-2022 9:30"), DF.parse("28-03-2022 12:30"), "false",c3,en2,s3);
//            Seance se11 = new Seance(DF.parse("28-03-2022 11:00"), DF.parse("28-03-2022 12:30"), "false",c3,en2,s3);
//            Seance se12 = new Seance(DF.parse("29-03-2022 14:00"), DF.parse("29-03-2022 17:00"), "false",c3,en2,s3);
//            Seance se13 = new Seance(DF.parse("30-03-2022 15:00"), DF.parse("29-03-2022 18:00"), "false",c3,en2,s3);
//            Seance se14 = new Seance(DF.parse("30-03-2022 17:00"), DF.parse("29-03-2022 18:30"), "false",c3,en2,s3);
//            Seance se15 = new Seance(DF.parse("27-03-2022 17:00"), DF.parse("27-03-2022 18:30"), "false",c2,en1,s3);
//            Seance se16 = new Seance(DF.parse("28-03-2022 09:30"), DF.parse("28-03-2022 12:30"), "false",c2,en1,s2);
//            Seance se17 = new Seance(DF.parse("29-03-2022 09:30"), DF.parse("29-03-2022 12:30"), "false",c2,en1,s2);
//            Seance se18 = new Seance(DF.parse("29-03-2022 09:30"), DF.parse("29-03-2022 12:30"), "false",c1,en3,s1);
//
//            session.save(se1);
//            session.save(se2);
//            session.save(se3);
//            session.save(se4);
//            session.save(se5);
//            session.save(se6);
//            session.save(se7);
//            session.save(se8);
//            session.save(se9);
//            session.save(se10);
//            session.save(se11);
//            session.save(se12);
//            session.save(se13);
//            session.save(se14);
//            session.save(se15);
//            session.save(se16);
//            session.save(se17);
//            session.save(se18);
//
//            Seance s1 = new Seance(DF.parse("01-03-2022 09:30"), DF.parse("01-03-2022 12:30"),"valide",cours.get(0),enseignants.get(0),salles.get(0));
//            Seance s2 = new Seance(DF.parse("01-03-2022 14:00"), DF.parse("01-03-2022 17:00"),"valide",cours.get(0),enseignants.get(0),salles.get(0));
//            Seance s3 = new Seance(DF.parse("01-03-2022 17:00"), DF.parse("01-03-2022 20:00"),"valide",cours.get(1),enseignants.get(1),salles.get(0));
//            Seance s4 = new Seance(DF.parse("01-03-2022 09:30"), DF.parse("01-03-2022 12:30"),"valide",cours.get(1),enseignants.get(1),salles.get(1));
//            Seance s5 = new Seance(DF.parse("01-03-2022 14:00"), DF.parse("01-03-2022 17:00"),"valide",cours.get(0),enseignants.get(0),salles.get(1));
//            Seance s6 = new Seance(DF.parse("01-03-2022 17:00"), DF.parse("01-03-2022 20:00"),"valide",cours.get(0),enseignants.get(0),salles.get(1));
//            Seance s7 = new Seance(DF.parse("02-03-2022 09:30"), DF.parse("02-03-2022 12:30"),"valide",cours.get(0),enseignants.get(0),salles.get(0));
//            Seance s8 = new Seance(DF.parse("02-03-2022 14:00"), DF.parse("02-03-2022 17:00"),"valide",cours.get(3),enseignants.get(3),salles.get(0));
//            Seance s9 = new Seance(DF.parse("02-03-2022 17:00"), DF.parse("02-03-2022 20:00"),"valide",cours.get(3),enseignants.get(3),salles.get(0));
//            Seance s10 = new Seance(DF.parse("02-03-2022 09:30"), DF.parse("02-03-2022 12:30"),"valide",cours.get(1),enseignants.get(1),salles.get(1));
//            Seance s11 = new Seance(DF.parse("02-03-2022 14:00"), DF.parse("02-03-2022 17:00"),"valide",cours.get(1),enseignants.get(1),salles.get(1));
//            Seance s12 = new Seance(DF.parse("02-03-2022 17:00"), DF.parse("02-03-2022 20:00"),"valide",cours.get(2),enseignants.get(2),salles.get(1));
//            Seance s13 = new Seance(DF.parse("03-03-2022 09:30"), DF.parse("03-03-2022 12:30"),"valide",cours.get(2),enseignants.get(2),salles.get(0));
//            Seance s14 = new Seance(DF.parse("03-03-2022 14:00"), DF.parse("03-03-2022 17:00"),"valide",cours.get(0),enseignants.get(0),salles.get(0));
//            Seance s15 = new Seance(DF.parse("03-03-2022 08:00"), DF.parse("03-03-2022 09:30"),"valide",cours.get(1),enseignants.get(1),salles.get(0));
//            Seance s16 = new Seance(DF.parse("03-03-2022 17:00"), DF.parse("03-03-2022 20:00"),"valide",c3,e1,3);
//            Seance s17 = new Seance(DF.parse("03-03-2022 09:30"), DF.parse("03-03-2022 12:30"),"valide",c4,e2,4);
//            Seance s18 = new Seance(DF.parse("03-03-2022 14:00"), DF.parse("03-03-2022 17:00"),"valide",c5,e2,5);
//            Seance s19 = new Seance(DF.parse("03-03-2022 17:00"), DF.parse("03-03-2022 20:00"),"valide",c6,e2,6);
//            Seance s20 = new Seance(DF.parse("04-03-2022 09:30"), DF.parse("04-03-2022 12:30"),"valide",c7,e3,7);
//            Seance s21 = new Seance(DF.parse("04-03-2022 14:00"), DF.parse("04-03-2022 17:00"),"valide",c8,e3,8);
//            Seance s22 = new Seance(DF.parse("04-03-2022 17:00"), DF.parse("04-03-2022 20:00"),"valide",c9,e3,9);
//            Seance s23 = new Seance(DF.parse("04-03-2022 09:30"), DF.parse("04-03-2022 12:30"),"valide",c10,en1,10);
//            Seance s24 = new Seance(DF.parse("04-03-2022 14:00"), DF.parse("04-03-2022 17:00"),"valide",c11,en2,11);
//            Seance s25 = new Seance(DF.parse("07-03-2022 09:30"), DF.parse("07-03-2022 12:30"),"enregistre",c1,e1,1);
//            Seance s26 = new Seance(DF.parse("07-03-2022 14:00"), DF.parse("07-03-2022 17:00"),"valide",c2,e1,2);
//            Seance s27 = new Seance(DF.parse("07-03-2022 17:00"), DF.parse("07-03-2022 20:00"),"enregistre",c3,e1,3);
//            Seance s28 = new Seance(DF.parse("07-03-2022 09:30"), DF.parse("07-03-2022 12:30"),"valide",c4,e2,4);
//            Seance s29 = new Seance(DF.parse("07-03-2022 14:00"), DF.parse("07-03-2022 17:00"),"enregistre",c5,e2,5);
//            Seance s30 = new Seance(DF.parse("07-03-2022 17:00"), DF.parse("07-03-2022 20:00"),"enregistre",c6,e2,6);
//            Seance s31 = new Seance(DF.parse("08-03-2022 09:30"), DF.parse("08-03-2022 12:30"),"enregistre",c7,e3,7);
//            Seance s32 = new Seance(DF.parse("09-03-2022 14:00"), DF.parse("09-03-2022 17:00"),"enregistre",c8,e3,8);
//            Seance s33 = new Seance(DF.parse("10-03-2022 17:00"), DF.parse("10-03-2022 20:00"),"enregistre",c9,e3,9);
//            Seance s34 = new Seance(DF.parse("11-03-2022 09:30"), DF.parse("11-03-2022 12:30"),"valide",c10,en1,10);
//            Seance s35 = new Seance(DF.parse("11-03-2022 14:00"), DF.parse("11-03-2022 17:00"),"enregistre",c11,en2,11);
//            Seance s36 = new Seance(DF.parse("14-03-2022 17:00"), DF.parse("14-03-2022 20:00"),"valide",c12,e1,12);
//            Seance s37 = new Seance(DF.parse("14-03-2022 09:30"), DF.parse("14-03-2022 12:30"),"valide",c13,e2,1);
//            Seance s38 = new Seance(DF.parse("14-03-2022 14:00"), DF.parse("14-03-2022 17:00"),"valide",c14,e3,2);
//            Seance s39 = new Seance(DF.parse("15-03-2022 08:00"), DF.parse("15-03-2022 09:30"),"enregistre",c19,e1,1);
//            Seance s40 = new Seance(DF.parse("16-03-2022 17:00"), DF.parse("16-03-2022 20:00"),"valide",c15,e1,3);
//            Seance s41 = new Seance(DF.parse("17-03-2022 09:30"), DF.parse("17-03-2022 12:30"),"valide",c16,e2,4);
//            Seance s42 = new Seance(DF.parse("17-03-2022 14:00"), DF.parse("17-03-2022 17:00"),"valide",c5,e2,5);
//            Seance s43 = new Seance(DF.parse("18-03-2022 17:00"), DF.parse("18-03-2022 20:00"),"valide",c20,e2,6);
//            Seance s44 = new Seance(DF.parse("18-03-2022 09:30"), DF.parse("18-03-2022 12:30"),"valide",c21,e3,7);
//            Seance s45 = new Seance(DF.parse("18-03-2022 14:00"), DF.parse("18-03-2022 17:00"),"valide",c21,e3,8);
//            Seance s46 = new Seance(DF.parse("21-03-2022 17:00"), DF.parse("21-03-2022 20:00"),"valide",c9,e3,9);
//            Seance s47 = new Seance(DF.parse("21-03-2022 09:30"), DF.parse("21-03-2022 12:30"),"valide",c10,en1,10);
//            Seance s48 = new Seance(DF.parse("21-03-2022 14:00"), DF.parse("21-03-2022 17:00"),"valide",c11,en2,11);
//            Seance s49 = new Seance(DF.parse("22-03-2022 09:30"), DF.parse("22-03-2022 12:30"),"valide",c10,en1,10);
//            Seance s50 = new Seance(DF.parse("23-03-2022 14:00"), DF.parse("23-03-2022 17:00"),"valide",c11,en2,11);
//            Seance s51 = new Seance(DF.parse("17-03-2022 09:30"), DF.parse("17-03-2022 12:30"),"enregistre",c20,e2,4);
//            Seance s52 = new Seance(DF.parse("17-03-2022 14:00"), DF.parse("17-03-2022 17:00"),"enregistre",c21,e3,5);
//            Seance s53 = new Seance(DF.parse("18-03-2022 17:00"), DF.parse("18-03-2022 20:00"),"valide",c6,e2,6);
            Seance s54 = new Seance(DF.parse("18-03-2022 09:30"), DF.parse("18-03-2022 12:30"),"valide",c1,en1,salle1);
            Seance s55 = new Seance(DF.parse("18-03-2022 14:00"), DF.parse("18-03-2022 17:00"),"valide",c1,en1,salle1);
            Seance s56 = new Seance(DF.parse("21-03-2022 17:00"), DF.parse("21-03-2022 20:00"),"valide",c2,en1,salle1);
            Seance s57 = new Seance(DF.parse("21-03-2022 09:30"), DF.parse("21-03-2022 12:30"),"valide",c2,en1,salle1);
            Seance s58 = new Seance(DF.parse("21-03-2022 14:00"), DF.parse("21-03-2022 17:00"),"valide",c5,en2,salle1);
            Seance s59 = new Seance(DF.parse("22-03-2022 09:30"), DF.parse("22-03-2022 12:30"),"valide",c10,en1,salle1);
            Seance s60 = new Seance(DF.parse("23-03-2022 14:00"), DF.parse("23-03-2022 17:00"),"valide",c11,en2,salle1);
            Seance s61 = new Seance(DF.parse("23-03-2022 09:30"), DF.parse("23-03-2022 12:30"),"valide",c1,en1,salle1);
            Seance s62 = new Seance(DF.parse("24-03-2022 14:00"), DF.parse("24-03-2022 17:00"),"valide",c2,en1,salle1);
            Seance s63 = new Seance(DF.parse("25-03-2022 17:00"), DF.parse("25-03-2022 20:00"),"enregistre",c5,en2,salle1);
            Seance s64 = new Seance(DF.parse("25-03-2022 09:30"), DF.parse("25-03-2022 12:30"),"valide",c6,en2,salle1);
            Seance s65 = new Seance(DF.parse("25-03-2022 14:00"), DF.parse("25-03-2022 17:00"),"valide",c11,en3,salle1);
            Seance s66 = new Seance(DF.parse("28-03-2022 17:00"), DF.parse("28-03-2022 20:00"),"valide",c11,en3,salle1);
            Seance s67 = new Seance(DF.parse("28-03-2022 09:30"), DF.parse("28-03-2022 12:30"),"enregistre",c10,en2,salle2);
            Seance s68 = new Seance(DF.parse("29-03-2022 14:00"), DF.parse("29-03-2022 17:00"),"valide",c1,en2,salle2);
            Seance s69 = new Seance(DF.parse("29-03-2022 17:00"), DF.parse("29-03-2022 20:00"),"null",c13 ,en4,salle3);
            Seance s70 = new Seance(DF.parse("29-03-2022 09:30"), DF.parse("29-03-2022 12:30"),"null",c10,en1,salle3);
            Seance s71 = new Seance(DF.parse("29-03-2022 14:00"), DF.parse("29-03-2022 17:00"),"null",c11,en3,salle2);
            Seance s72 = new Seance(DF.parse("30-03-2022 17:00"), DF.parse("30-03-2022 20:00"),"null",c12,en3,salle3);
            Seance s73 = new Seance(DF.parse("30-03-2022 09:30"), DF.parse("30-03-2022 12:30"),"null",c2,en2,salle4);
            Seance s74 = new Seance(DF.parse("31-03-2022 14:00"), DF.parse("31-03-2022 17:00"),"null",c2,en2,salle4);
            Seance s75 = new Seance(DF.parse("31-03-2022 08:00"), DF.parse("31-03-2022 09:30"),"null",c13,en4,salle1);
            Seance s76 = new Seance(DF.parse("31-03-2022 17:00"), DF.parse("31-03-2022 20:00"),"null",c13,en4,salle2);
            Seance s77 = new Seance(DF.parse("31-03-2022 09:30"), DF.parse("31-03-2022 12:30"),"null",c8 ,en2,salle4);
            Seance s78 = new Seance(DF.parse("31-03-2022 14:00"), DF.parse("31-03-2022 17:00"),"null",c10 ,en2,salle3);
            Seance s79 = new Seance(DF.parse("01-04-2022 17:00"), DF.parse("01-04-2022 20:00"),"null",c6,en2,salle5);
            Seance s80 = new Seance(DF.parse("01-04-2022 09:30"), DF.parse("01-04-2022 12:30"),"null",c7,en2,salle5);
            Seance s81 = new Seance(DF.parse("01-04-2022 14:00"), DF.parse("01-04-2022 17:00"),"null",c13,en4,salle2);
            Seance s82 = new Seance(DF.parse("01-04-2022 17:00"), DF.parse("01-04-2022 20:00"),"null",c11 ,en3,salle3);
            Seance s83 = new Seance(DF.parse("02-04-2022 09:30"), DF.parse("04-04-2022 12:30"),"null",c13,en4,salle3);
            Seance s84 = new Seance(DF.parse("04-04-2022 14:00"), DF.parse("04-04-2022 17:00"),"null",c11,en3,salle2);
            Seance s85 = new Seance(DF.parse("04-04-2022 09:30"), DF.parse("04-04-2022 12:30"),"null",c12,en3,salle4);
//            Seance s86 = new Seance(DF.parse("04-04-2022 14:00"), DF.parse("04-04-2022 17:00"),"null",c2,e1,2);
//            Seance s87 = new Seance(DF.parse("05-04-2022 08:00"), DF.parse("05-04-2022 09:30"),"null",c3,e1,3);
//            Seance s88 = new Seance(DF.parse("05-04-2022 09:30"), DF.parse("05-04-2022 12:30"),"null",c4,e2,4);
//            Seance s89 = new Seance(DF.parse("05-04-2022 14:00"), DF.parse("05-04-2022 17:00"),"null",c5,e2,5);
//            Seance s90 = new Seance(DF.parse("05-04-2022 17:00"), DF.parse("05-04-2022 20:00"),"null",c6,e2,6);
//            Seance s91 = new Seance(DF.parse("06-04-2022 09:30"), DF.parse("06-04-2022 12:30"),"null",c7,e3,7);
//            Seance s92 = new Seance(DF.parse("06-04-2022 14:00"), DF.parse("06-04-2022 17:00"),"null",c8,e3,8);
//            Seance s93 = new Seance(DF.parse("06-04-2022 17:00"), DF.parse("06-04-2022 20:00"),"null",c9,e3,9);
//            Seance s94 = new Seance(DF.parse("07-04-2022 09:30"), DF.parse("07-04-2022 12:30"),"null",c10,en1,10);
//            Seance s95 = new Seance(DF.parse("07-04-2022 14:00"), DF.parse("07-04-2022 17:00"),"null",c11,en2,11);
//            Seance s96 = new Seance(DF.parse("07-04-2022 17:00"), DF.parse("07-04-2022 20:00"),"null",c12,e1,12);
//            Seance s97 = new Seance(DF.parse("08-04-2022 09:30"), DF.parse("08-04-2022 12:30"),"null",c13,e2,1);
//            Seance s98 = new Seance(DF.parse("08-04-2022 14:00"), DF.parse("08-04-2022 17:00"),"null",c14,e3,2);
//            Seance s99 = new Seance(DF.parse("11-04-2022 08:00"), DF.parse("11-04-2022 09:30"),"null",c19,e1,1);
//            Seance s100 = new Seance(DF.parse("15-04-2022 17:00"), DF.parse("15-04-2022 20:00"),"null",c15,e1,3);

//            session.save(s1);
//            session.save(s2);
//            session.save(s3);
//            session.save(s4);
//            session.save(s5);
//            session.save(s6);
//            session.save(s7);
//            session.save(s8);
//            session.save(s9);
//            session.save(s10);
//            session.save(s11);
//            session.save(s12);
//            session.save(s13);
//            session.save(s14);
//            session.save(s15);
//            session.save(s16);
//            session.save(s17);
//            session.save(s18);
//            session.save(s19);
//            session.save(s20);
//            session.save(s21);
//            session.save(s22);
//            session.save(s23);
//            session.save(s24);
//            session.save(s25);
//            session.save(s26);
//            session.save(s27);
//            session.save(s28);
//            session.save(s29);
//            session.save(s30);
//            session.save(s31);
//            session.save(s32);
//            session.save(s33);
//            session.save(s34);
//            session.save(s35);
//            session.save(s36);
//            session.save(s37);
//            session.save(s38);
//            session.save(s39);
//            session.save(s40);
//            session.save(s41);
//            session.save(s42);
//            session.save(s43);
//            session.save(s44);
//            session.save(s45);
//            session.save(s46);
//            session.save(s47);
//            session.save(s48);
//            session.save(s49);
//            session.save(s50);
//            session.save(s51);
//            session.save(s52);
//            session.save(s53);
            session.save(s54);
            session.save(s55);
            session.save(s56);
            session.save(s57);
            session.save(s58);
            session.save(s59);
            session.save(s60);
            session.save(s61);
            session.save(s62);
            session.save(s63);
            session.save(s64);
            session.save(s65);
            session.save(s66);
            session.save(s67);
            session.save(s68);
            session.save(s69);
            session.save(s70);
            session.save(s71);
            session.save(s72);
            session.save(s73);
            session.save(s74);
            session.save(s75);
            session.save(s76);
            session.save(s77);
            session.save(s78);
            session.save(s79);
            session.save(s80);
            session.save(s81);
            session.save(s82);
            session.save(s83);
            session.save(s84);
            session.save(s85);
//            session.save(s86);
//            session.save(s87);
//            session.save(s88);
//            session.save(s89);
//            session.save(s90);
//            session.save(s91);
//            session.save(s92);
//            session.save(s93);
//            session.save(s94);
//            session.save(s95);
//            session.save(s96);
//            session.save(s97);
//            session.save(s98);
//            session.save(s99);
//            session.save(s100);


            t.commit(); // Commit et flush automatique de la session.
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /*----- une formation propose des cours -----*/
    public static void formationProposerCours()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            Formation f1 = session.get(Formation.class, 1L);/*l带上小写l 因为这是Long e est persistant*/
            Formation f2 = session.get(Formation.class, 2L);
            Cours c1 = session.get(Cours.class,9L);
            Cours c2 = session.get(Cours.class,10L);
            Cours c3 = session.get(Cours.class,11L);
            Cours c4 = session.get(Cours.class,12L);
            Cours c5 = session.get(Cours.class,4L);
            Cours c6 = session.get(Cours.class,5L);
            Cours c7 = session.get(Cours.class,21L);
            Cours c8 = session.get(Cours.class,22L);
            Cours c9 = session.get(Cours.class,23L);
            Cours c10 = session.get(Cours.class,24L);
            Cours c11 = session.get(Cours.class,1L);
            Cours c12 = session.get(Cours.class,2L);
            Cours c13 = session.get(Cours.class,8L);

            f1.getCours().add(c1);
            f1.getCours().add(c2);
            f1.getCours().add(c5);
            f1.getCours().add(c6);
            f1.getCours().add(c7);
            f1.getCours().add(c8);
            f1.getCours().add(c11);
            f1.getCours().add(c12);
            f1.getCours().add(c13);
            f2.getCours().add(c3);
            f2.getCours().add(c4);
            f2.getCours().add(c9);
            f2.getCours().add(c10);


            t.commit();
            session.close();
        }
    }

    /*----- une liste doit être insérée dans la table presence -----*/
    public static void addJustificatif()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            Scolarite sc1 = session.get(Scolarite.class, 15L);
            Etudiant e1 = session.get(Etudiant.class,8L);
            Etudiant e2 = session.get(Etudiant.class,9L);
//            PresenceID idP1 = new PresenceID(1L, 8L);
//            Presence p1 = new Presence();
//            p1.setIdPresence(idP1);



            Justificatif j1 = new Justificatif(new Date(),"Fin","css.png","justificatif",sc1, e1);
            Justificatif j2 = new Justificatif(new Date(),"Fin","css.png","justificatif",sc1, e2);
//            p1.setJustificatif(j1);

            session.save(j1);
            session.save(j2);

            t.commit();

            session.close();
        }
    }

    /*----- Création et enregistrement des présence -----*/
    public static void enrgPresence ()
    {
        List presences = PresenceDAO.geneTablePresence();
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles séances
            Iterator e = presences.iterator();

            while (e.hasNext())
            {
                Object[] tab_obj = ((Object[]) e.next());
                for (int i =0 ; i<tab_obj.length-1; i++){
                    if(tab_obj[0]!=null && tab_obj[1]!=null) {
                        //EtudiantDAO etudiantDAO = new EtudiantDAO();
                        //SeanceDAO seanceDAO = new SeanceDAO();
                        //Etudiant etudiant = etudiantDAO.find(Long.parseLong(String.valueOf(tab_obj[1])));
                        //Seance seance = seanceDAO.find(Long.parseLong(String.valueOf(tab_obj[0])));

                        Etudiant etudiant = session.get(Etudiant.class,Long.parseLong(String.valueOf(tab_obj[1])));
                        Seance seance = session.get(Seance.class, Long.parseLong(String.valueOf(tab_obj[0])));

                        Presence presence = new Presence(EtatPresence.PRESENCE, new PresenceID(seance.getIdSeance(),etudiant.getIdU()), null);
                        etudiant.getSeanPresences().put(seance,presence);
                        seance.getEtuPresences().put(etudiant,presence);

                        presence.setEtudiant(etudiant);
                        presence.setSeance(seance);
                    }
                }
            }
            t.commit();
            session.close();
        }
    }

    /*----- une formation récupère des étudiants -----*/

    public static void ajoutEtudiantFormation()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            Formation f1 = session.get(Formation.class, 1L);/* e est persistant*/
            Formation f2 = session.get(Formation.class, 2L);

            Etudiant e1 = session.get(Etudiant.class, 7L);
            Etudiant e2 = session.get(Etudiant.class, 8L);
            Etudiant e3 = session.get(Etudiant.class, 9L);
            Etudiant e4 = session.get(Etudiant.class, 10L);
            Etudiant e5 = session.get(Etudiant.class, 11L);
            Etudiant e6 = session.get(Etudiant.class, 12L);
            Etudiant e7 = session.get(Etudiant.class, 13L);
            Etudiant e8 = session.get(Etudiant.class, 14L);

            f1.getEtudiants().add(e1);
            f1.getEtudiants().add(e2);
            f1.getEtudiants().add(e3);
            f1.getEtudiants().add(e4);
            f1.getEtudiants().add(e5);
            f1.getEtudiants().add(e6);
            f1.getEtudiants().add(e7);
            f2.getEtudiants().add(e8);

            t.commit();

            session.close();
        }
    }


    /*----- les information d'une fiche d'appel -----*/
    public static void infoFicheAppel(long id)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            List q = session.createQuery("select s.enseignant.nomU, s.enseignant.prenomU, s.dateDebut, s.salle.nomSalle, s.cour.nomCours, s.cour.typeCours, f.validee " +
                    "from com.example.appelprojet.mertier.Seance s, com.example.appelprojet.mertier.FicheAppel f " +
                    "where f.id = s.ficheAppel.id " +
                    "and s.enseignant.idU = ' " + id + "' " +
                    "and sysdate() >s.dateDebut " +
                    "and sysdate() <s.dateFin " ).list();

            affichage(q);

            t.commit();

            session.close();
        }
    }

    /*----- liste des étudiants d'une fiche d'appel-----*/
    public static void listeEtudiants(long id)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            Query q = session.createQuery("select e.nomU, e.prenomU, s.salle.nomSalle, s.cour.nomCours, s.cour.typeCours, f.validee " +
                    "from com.example.appelprojet.mertier.Etudiant e, com.example.appelprojet.mertier.FicheAppel f " +
                    "where f.id = s.ficheAppel.id " +
                    "and s.enseignant.idU = ' " + id + "' " +
                    "and sysdate() >s.dateDebut " +
                    "and sysdate() <s.dateFin " );

            t.commit();

            session.close();
        }
    }

    public static void affichage(List l)
    {
        Iterator e = l.iterator();
        while (e.hasNext())
        {
            Object[] tab_obj = ((Object[]) e.next());

            for (Object obj : tab_obj)
                System.out.print(obj + " ");

            System.out.println("");
        }
    }

    public static void main(String[] args) throws Exception {
//        enrgFormation();
//        enrgUtilisateur();
//        enrgSalle();
//        enrgCours();
//        enrgSeances();
//        enrgPresence();
//        addJustificatif();
//        ajoutEtudiantFormation();
//        formationProposerCours();
//        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
//        List<Utilisateur> utilisateurList = (List<Utilisateur>) utilisateurDAO.findAll();
//        for (Utilisateur u: utilisateurList ) {
//            System.out.println("Nom: " + u.getNomU() + "  Prenom: " + u.getPrenomU() + " Email: "
//                    + u.getEmail() + " Mode de passe: " + u.getMdp());
//            System.out.println("User Role : " + u.getClass());
//        }
//        Utilisateur utilisateur = UtilisateurDAO.getLoginInfo("hugo@com", "hugo");
//
//if (utilisateur != null){
//    boolean user = UtilisateurDAO.emailExiste(utilisateur.getEmail());
//    System.out.println("User exist : " + user);
//}else {
//    System.out.println("User NULL");
//}

//EtudiantDAO.findByName("chen");

//        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
//        Utilisateur utilisateur = utilisateurDAO.find(5L);
//        List<Seance> seances = SeanceDAO.findSeancesSemaine(utilisateur, new Date());
//        for (Seance s: seances) {
//            System.out.println("Seances list : " + s.getIdSeance());
//        }
//List<Etudiant> etudiants = EtudiantDAO.findByName("a");
//        for (Etudiant e: etudiants){
//            System.out.println("Nom :" + e.getNomU() + " Prenom: " + e.getPrenomU() ) ;
//        }
//        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
//        Date date = new Date();
//        List<Seance> seances = SeanceDAO.findSeancesSemaine(utilisateurDAO.find(4L), date);
//if (seances !=null){
//    for (Seance seance : seances){
//        System.out.println(seance.getIdSeance() + " enseignant: " + seance.getEnseignant() + " date: " + seance.getDateDebut());
//        float startTime = seance.getDateDebut().getHours();
//        float endTime = seance.getDateDebut().getHours();
//        if (seance.getDateDebut().getMinutes() == 30){
//            startTime = seance.getDateDebut().getHours() + 0.5F;
//        }
//        if (seance.getDateFin().getMinutes()== 30){
//            endTime = seance.getDateFin().getHours() + 0.5F;
//        }
//        int startLine = (int) ((startTime - 8) / 0.5 + 1);
//        int endLine = (int) ((endTime - 8) / 0.5 + 1);
//        Planning planning = new Planning(seance.getDateDebut());
//        List<Date> week = planning.weekDate;
//        System.out.println(week);
////        System.out.println(planning.day);
//        System.out.println("Start_line : " + startLine + " End_Line: " + endLine + " Week Day: " + planning.day);
//    }
//}else {
//    System.out.println("NULL");
//}
////SeanceDAO seanceDAO = new SeanceDAO();
//boolean exist = SeanceDAO.isFindSeanceActuelByUser(utilisateurDAO.find(5L));
//System.out.println("Appel existe: " + exist);
//Seance appel = SeanceDAO.infoFicheAppel(utilisateurDAO.find(5L));
//System.out.println("Appel infos : " + appel);
//List<Seance> seances = SeanceDAO.findSeanceByUser(utilisateurDAO.find(5L));
//        for (Seance s: seances) {
//            System.out.println("Seances list : " + s.getIdSeance());
//        }

//        SeanceDAO seanceDAO = new SeanceDAO();
//        Date date = new Date();
//        System.out.println("Get SEANCE ACTUEL DE l'Enseignat 1");
//        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        System.out.println(seanceDAO.findSeanceByTimeByIdU(date, 1L));
//        EtudiantDAO etudiantDAO = new EtudiantDAO();
//        Etudiant etudiant = etudiantDAO.find(7L);
//        SeanceDAO seanceDAO = new SeanceDAO();
//        Seance seance = seanceDAO.find(17L);
//        PresenceDAO.updateEtatPreEtuByID(EtatPresence.RETART,7L,9L);
//        SeanceDAO seanceDAO = new SeanceDAO();
//        SeanceDAO.updateEtatAppelBySeance(seanceDAO.find(10L), "valide");
//       List<Presence> presences =  PresenceDAO.findPresenceByIdSeance(20L);
//       for (Presence p: presences){
//           System.out.println("Find presence: " + p.getEtudiant().getEmail());
//       }
//        List<Presence> presences = PresenceDAO.findPresenceByIdSeance(15L);
//        FontionsUtiles.notifyAbsenceSeance();
    }

}