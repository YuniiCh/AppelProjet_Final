package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Salle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class SalleDAO extends DAO<Salle> {
    public SalleDAO() {super.setEntity(Salle.class);}
    /*----- trouver les salles disponibles pour la date début et la date fin de la séance qu'on va ajouter -----*/
    public static List<Salle> salleDispoCeMoment(String dateDebut, String dateFin)
    {
        /*----- Ouverture de la session -----*/
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            /*----- Ouverture d'une transaction -----*/
            Transaction t = session.beginTransaction();

            List<Salle> liste = session.createQuery("select distinct sa " +
                    "from com.example.appelprojet.mertier.Salle sa  " +
                    "left join sa.seances s  " +
                    "where ((s.dateDebut>='"+dateFin+"' or s.dateFin<='" + dateDebut + "') or (s.dateDebut is null and s.dateFin is null)) " +
                    "and sa.idSalle not in (select sa.idSalle " +
                    "from com.example.appelprojet.mertier.Salle sa " +
                    "left join sa.seances s  " +
                    "where (s.dateDebut<='"+dateFin+"' and s.dateFin>='" + dateDebut + "')) " ).list();

            t.commit();
            session.close();
            for (Salle a : liste)
                System.out.println(a);
            return liste;
        }
    }
}
