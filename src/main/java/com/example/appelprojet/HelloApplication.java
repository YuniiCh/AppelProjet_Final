package com.example.appelprojet;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.TypeEtudiant;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.text.SimpleDateFormat;

@ApplicationPath("/api")
public class HelloApplication extends Application {
    /*----- Format de date -----*/
    private static final SimpleDateFormat DF = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


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
            Utilisateur e1 = new Utilisateur("Ravat", "Franck", "frank","frank@.com","100");
            Utilisateur e2 = new Utilisateur("Perrussel", "Laurent","lurent","lurent@.com","101");
            Utilisateur e3 = new Utilisateur("Doutre", "Sylvie","sylvie","sylvie@.com","102");
            Enseignant en1 = new Enseignant("Ravat", "Peter","peter","peter@.com","103");
            Enseignant en2 = new Enseignant("Perrussel", "Hugo","hugo", "hugo@com","104");
            // Formation
            Formation f1 = session.get(Formation.class,1);
            // Création des nouvelles employées
            Etudiant et1 = new Etudiant("Doutre", "Aline","aline","aline@com","105", TypeEtudiant.INITIAL,"TD2",f1);
            Scolarite s1 = new Scolarite("Ravat", "Annie","annie@com","106");


            session.save(e1);
            session.save(e2);
            session.save(e3);
            session.save(en1);
            session.save(en2);
            session.save(et1);
            session.save(s1);

            t.commit(); // Commit et flush automatique de la session.
        }
    }
    public static void main(String[] args) {
        enrgUtilisateur();

    }

}