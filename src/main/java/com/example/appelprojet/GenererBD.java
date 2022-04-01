package com.example.appelprojet;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.*;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.EtatVerifier;
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
public class GenererBD extends Application {
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
            Enseignant e2 = new Enseignant("PERRUSSEL", "Laurent","laurent","laurent.perrussel@ut-capitole.fr","101");
            Enseignant e3 = new Enseignant("DOUTRE", "Sylvie","sylvie","Sylvie.Doutre@ut-capitole.fr","102");
            Enseignant en1 = new Enseignant("VALLES ", "Nathalie","nathalie","nathalie.valles-parlangeau@ut-capitole.fr","103");
            Enseignant en2 = new Enseignant("BERRO ", "Alain","alain", "Alain.Berro@ut-capitole.fr","104");
            Enseignant en3 = new Enseignant("BOUR ", "Raphaelle","raphaelle", "Raphaelle.Bour@ut-capitole.fr","105");
            // Formation
            Formation f1 = session.get(Formation.class, 1L);
            Formation f2 = session.get(Formation.class, 2L);
            Etudiant et1 = new Etudiant("Anis", "Mana","anis","anis.mana@ut-capitole.fr","105", TypeEtudiant.FA, "TD1", f1);
            Etudiant et2 = new Etudiant("CHEN", "Yuni","yuni","yunic4639@gmail.com","107", TypeEtudiant.FI, "TD1", f1);
            Etudiant et3 = new Etudiant("LI", "Jiayin","jiayin","jiayinfanny@icloud.com","108", TypeEtudiant.FI, "TD1", f1);
            Etudiant et4 = new Etudiant("ZHAO", "Mengying","mengying","zhaomengying.fr@gmail.com","109", TypeEtudiant.FI, "TD1", f1);
            Etudiant et5 = new Etudiant("CLAUDEL", "Frank","frank","fonkwa_claudel@yahoo.fr","110", TypeEtudiant.FI, "TD2", f1);
            Etudiant et6 = new Etudiant("LI", "Shuanghong","shuanghong","lishuanghong3849@gmail.com","111", TypeEtudiant.FI, "TD2", f1);
            Etudiant et7 = new Etudiant("LIU", "Tong","tong","tongliu024@gmail.com","112", TypeEtudiant.FA, "TD2", f1);
            Etudiant et8 = new Etudiant("ZHOU", "Zijing","zijing","zijing.zhou@ut-capitole.fr","113", TypeEtudiant.FI, "TD1", f2);
            Etudiant et9 = new Etudiant("GAN", "Wenhui","Wenhui","viviannegan@gmail.com","114",TypeEtudiant.FI,"TD1",f1);
            Etudiant et10 = new Etudiant("HE", "Peicong","peicong","acyeol1997@gmail.com","115",TypeEtudiant.FI,"TD1",f1);
            Etudiant et11 = new Etudiant("LI", "Zijian","zijian","james112732@gmail.com","116",TypeEtudiant.FI,"TD1",f1);
            Etudiant et12 = new Etudiant("BAH", "Maimouna","maimouna","maimouna.bah@ut-capitole.fr","117", TypeEtudiant.FI, "TD1", f1);
            Etudiant et13 = new Etudiant("JOOHUYN", "Ann","ann","joohyun.ann@ut-capitole.fr","118", TypeEtudiant.FA, "TD1", f1);
            Etudiant et14 = new Etudiant("Nogales", "Sebastien","sebastien","sebastian.nogales-pinde@ut-capitole.fr","119", TypeEtudiant.FI, "TD1", f1);
            Etudiant et15 = new Etudiant("Vlada", "Stegarescu","vlada","vlada.stegarescu@ut-capitole.fr","120", TypeEtudiant.FA, "TD2", f1);
            Etudiant et16 = new Etudiant("Monlouis", "Ruddy","ruddy","ruddy.monlouis@ut-capitole.fr","121", TypeEtudiant.FA, "TD2", f1);
            Etudiant et17 = new Etudiant("Dat", "Tien","tien","tien-Dat.hoang@ut-capitole.fr","122", TypeEtudiant.FA, "TD2", f1);
            Etudiant et18 = new Etudiant("Daggo", "Annie","annie","annie.dago@ut-capitole","123", TypeEtudiant.FA, "TD1", f2);
            Etudiant et19 = new Etudiant("LIU", "Jie","liu","jie.liu@ut-capitole.fr","124",TypeEtudiant.FI,"TD1",f1);
            Etudiant et20 = new Etudiant("Parra", "Milo","milo","milo.parra@ut-capitole.fr","125",TypeEtudiant.FI,"TD1",f1);
            Etudiant et21 = new Etudiant("PU", "Zixin","zixin","zixin.pu@ut-capitole.fr","126",TypeEtudiant.FI,"TD1",f1);
            Scolarite et22 = new Scolarite("ScolariteIPM", "Ababa","A123456789&","appelprojet1@gmail.com","127");

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
            session.save(et10);
            session.save(et11);
            session.save(et12);
            session.save(et13);
            session.save(et14);
            session.save(et15);
            session.save(et16);
            session.save(et17);
            session.save(et18);
            session.save(et19);
            session.save(et20);
            session.save(et21);
            session.save(et22);

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

            Scolarite s1 = session.get(Scolarite.class, 28L);

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
//        Seances
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
            t.commit(); // Commit et flush automatique de la session.
        } catch (ParseException e) {
            e.printStackTrace();
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



            Justificatif j1 = new Justificatif(new Date(),EtatVerifier.FINI,"css.png","justificatif",sc1, e1);
            Justificatif j2 = new Justificatif(new Date(),EtatVerifier.FINI,"css.png","justificatif",sc1, e2);
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

                        Presence presence = new Presence(EtatPresence.ABSENCE, new PresenceID(seance.getIdSeance(),etudiant.getIdU()), null);
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

    public static void main(String[] args) {
        enrgFormation();
        enrgUtilisateur();
        enrgSalle();
        enrgCours();
        enrgSeances();
        enrgPresence();
//        addJustificatif();
        ajoutEtudiantFormation();
        formationProposerCours();
    }




}