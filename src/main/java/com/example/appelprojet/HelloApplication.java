package com.example.appelprojet;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.EtudiantDAO;
import com.example.appelprojet.dao.PresenceDAO;
import com.example.appelprojet.dao.SeanceDAO;
import com.example.appelprojet.dao.UtilisateurDAO;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.TypeEtudiant;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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


    /*----- Création et enregistrement des Scolarites -----*/
    public static void enrgScolarite () {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();
            Scolarite sc = new Scolarite("cyn", "cyn","cyn@com","112");
            session.save(sc);
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


            //Scolarite
            Scolarite s1 = session.get(Scolarite.class, 1L);

            // Création des nouvelles séances
            Formation f1 = new Formation("M2 IPM",s1);
            Formation f2 = new Formation("M2 IPM",s1);
            Formation f3 = new Formation("M2 ISIAD",s1);
            Formation f4 = new Formation("M2 ISIAD",s1);

            session.save(f1);
            session.save(f2);
            session.save(f3);
            session.save(f4);


            t.commit(); // Commit et flush automatique de la session.
        }
    }

    /*----- Création et enregistrement des utilisateurs -----*/
    public static void enrgUtilisateur ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles employées
            Utilisateur e1 = new Utilisateur("Ravat", "Franck", "frank","frank@.com","100");
            Utilisateur e2 = new Utilisateur("Perrussel", "Laurent","lurent","lurent@.com","101");
            Utilisateur e3 = new Utilisateur("Doutre", "Sylvie","sylvie","sylvie@.com","102");
            Enseignant en1 = new Enseignant("Ravat", "Peter","peter","peter@.com","103");
            Enseignant en2 = new Enseignant("Perrussel", "Hugo","hugo", "hugo@com","104");
            // Formation
            Formation f1 = session.get(Formation.class, 1L);
            Etudiant et1 = new Etudiant("Doutre", "Aline","aline","aline@com","105", TypeEtudiant.INITIAL, "TD1", f1);
            Etudiant et2 = new Etudiant("abc", "abc","abc","abc@com","107", TypeEtudiant.INITIAL, "TD1", f1);
            Etudiant et3 = new Etudiant("asd", "asd","asd","aline@com","108", TypeEtudiant.INITIAL, "TD1", f1);
            Etudiant et4 = new Etudiant("zxc", "zxc","zxc","zxc@com","109", TypeEtudiant.INITIAL, "TD1", f1);
            Etudiant et5 = new Etudiant("qwe", "qwe","qwe","qwe@com","110", TypeEtudiant.INITIAL, "TD1", f1);
            Etudiant et6 = new Etudiant("tyu", "tyu","tyu","tyu@com","111", TypeEtudiant.INITIAL, "TD1", f1);
            Scolarite et7 = new Scolarite("cyn", "cyn","cyn@com","112");

            session.save(e1);
            session.save(e2);
            session.save(e3);
            session.save(en1);
            session.save(en2);
            session.save(et1);
            session.save(et2);
            session.save(et3);
            session.save(et4);
            session.save(et5);
            session.save(et6);
            session.save(et7);

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

            Formation f1 = session.get(Formation.class, 1L);
            // Création des nouvelles cours
            Cours c1 = new Cours("bigdata","cm",f1);
            Cours c2 = new Cours("bigdata","td1",f1);
            Cours c3 = new Cours("bigdata","td2",f1);
            Cours c4 = new Cours("dai","cm", f1);
            Cours c5 = new Cours("dai","td1", f1);
            Cours c6 = new Cours("dai","td2", f1);
            /*Cours c1 = new Cours("Développement d'applications internet", "CM");
            Cours c2 = new Cours("Développement d'applications internet", "TD1");
            Cours c3 = new Cours("Développement d'applications internet", "TD2");
            Cours c4 = new Cours("Management agile", "CM");
            Cours c5 = new Cours("Management agile", "TD1");
            Cours c6 = new Cours("Management agile", "TD2");
            Cours c7 = new Cours("Transformation du SI", "CM");
            Cours c8 = new Cours("Accompagnement client", "CM");*/

            session.save(c1);
            session.save(c2);
            session.save(c3);
            session.save(c4);
            session.save(c5);
            session.save(c6);
            /*session.save(c7);
            session.save(c8);*/

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

            //Cours
            Cours c1 = session.get(Cours.class,1L);
            Cours c2 = session.get(Cours.class,2L);
            Cours c3 = session.get(Cours.class,3L);

            //Enseignant
            Enseignant en1 = session.get(Enseignant.class, 4L);
            Enseignant en2 = session.get(Enseignant.class, 5L);

            //Salle
            Salle s1 = session.get(Salle.class, 1L);
            Salle s2 = session.get(Salle.class, 2L);
            Salle s3 = session.get(Salle.class, 3L);


            //Création des séances
            Seance se1 = new Seance(DF.parse("15-03-2021 09:30"), DF.parse("15-03-2021 12:30"), "false",c1,en1,s1);
            Seance se2 = new Seance(DF.parse("16-03-2021 09:30"), DF.parse("16-03-2021 12:30"), "false",c2,en1,s2);
            Seance se3 = new Seance(DF.parse("16-03-2021 14:00"), DF.parse("16-03-2021 17:00"), "false",c3,en2,s3);
            Seance se4 = new Seance(DF.parse("17-03-2021 09:30"), DF.parse("17-03-2021 12:30"), "false",c2,en2,s1);
            Seance se5 = new Seance(DF.parse("23-03-2022 12:00"), DF.parse("23-03-2022 19:00"), "false",c3,en1,s3);
            Seance se6 = new Seance(DF.parse("24-03-2022 10:10"), DF.parse("24-03-2022 12:30"), "false",c3,en2,s1);
            Seance se7 = new Seance(DF.parse("24-03-2022 12:00"), DF.parse("24-03-2022 19:00"), "false",c3,en2,s3);
            Seance se8 = new Seance(DF.parse("24-03-2022 19:00"), DF.parse("25-03-2022 19:00"), "false",c3,en2,s3);
            Seance se9 = new Seance(DF.parse("25-03-2022 19:00"), DF.parse("30-03-2022 22:00"), "false",c3,en2,s3);

//            session.save(se1);
//            session.save(se2);
//            session.save(se3);
//            session.save(se4);
//            session.save(se5);
//            session.save(se6);
//            session.save(se7);
//            session.save(se8);
            session.save(se9);


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

            Cours c1 = session.get(Cours.class, 1L);
            Cours c2 = session.get(Cours.class, 2L);
            Cours c3 = session.get(Cours.class, 3L);

            f1.getCours().add(c1);

            f1.getCours().add(c2);

            f1.getCours().add(c3);

            t.commit();

            session.close();
        }
    }


    /*----- Création et enregistrement des salles -----*/
    public static void enrgPresence ()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession())
        {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            // Création des nouvelles séances
            List presences = PresenceDAO.geneTablePresence();
            Iterator e = presences.iterator();
//            List<HashMap> listSeanEtu =  new ArrayList<>();
//            HashMap<Long,Long> mapid = new HashMap<>();
            while (e.hasNext())
            {
                Object[] tab_obj = ((Object[]) e.next());
                for (int i =0 ; i<tab_obj.length-1; i++){
                    if (i%2==0){
                        EtudiantDAO etudiantDAO = new EtudiantDAO();
                        SeanceDAO seanceDAO = new SeanceDAO();
                        Etudiant etudiant = etudiantDAO.find(Long.parseLong(tab_obj[0].toString()));
                        Seance seance = seanceDAO.find(Long.parseLong(tab_obj[1].toString()));
                        Presence presence = new Presence( "presence","", etudiant,seance,null);
//                session.save(new PresenceID(listEtudiant.get(0), listEtudiant.get(1)));
                        session.save(presences);
                        t.commit();
//                        mapid.put(Long.parseLong(tab_obj[0].toString()),Long.parseLong(tab_obj[1].toString()));

                         }
                }
               // Commit et flush automatique de la session.
//                session.close();
            }

//
        }
    }

    /*----- une formation récupère des étudiants -----*/

    public static void ajoutEtudiantFormation()
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();

            Formation f1 = session.get(Formation.class, 1L);/* e est persistant*/

            Etudiant e1 = session.get(Etudiant.class, 6L);
            Etudiant e2 = session.get(Etudiant.class, 8L);
            Etudiant e3 = session.get(Etudiant.class, 9L);
            Etudiant e4 = session.get(Etudiant.class, 10L);

            f1.getEtudiants().add(e1);

            f1.getEtudiants().add(e2);

            f1.getEtudiants().add(e3);

            f1.getEtudiants().add(e4);

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

    public static void main(String[] args) {
//        enrgScolarite();
//        enrgFormation();
//        enrgUtilisateur();
//        enrgSalle();
//        enrgCours();
//        enrgSeances();
//        enrgPresence();
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

EtudiantDAO.findByName("chen");

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
    }

}