package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.FontionsUtiles;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.servlet.http.HttpSession;
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

//    public static String findEtudiantEtatByEtu(Etudiant etudiant, Seance seance){
//        String etat = null;
//        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
//            Transaction t = session.beginTransaction();
//            Query query = session.createQuery("select p.etatPresence from com.example.appelprojet.mertier.Presence p where p.seance.idSeance = '" + seance.getIdSeance() + "' and p.etudiant.idU = '" + etudiant.getIdU() + "'");
//           if (!query.getResultList().isEmpty()){
//               etat = query.getResultList().get(0).toString();
//           }
//        }
//        return etat;
//    }

}
