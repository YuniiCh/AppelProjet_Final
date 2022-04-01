package com.example.appelprojet.dao;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Utilisateur;
import com.example.appelprojet.util.FontionsUtiles;
import com.example.appelprojet.util.Role;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UtilisateurDAO extends DAO<Utilisateur> {

    public UtilisateurDAO() {
        super.setEntity(Utilisateur.class);
    }

    public static Utilisateur getLoginInfo(String email, String pwd) {/*----- Ouverture d'une transaction -----*/
        Utilisateur utilisateur = null;
        System.out.println("Open session!");
        try (Session session =HibernateUtil.getSessionFactory().getCurrentSession()) {
            System.out.println("get email: " + email);
            System.out.println("get pwd: " + pwd);
            System.out.println("get session!");
            Transaction transaction = getTransaction(session);
            org.hibernate.query.Query query = session.createQuery("from com.example.appelprojet.mertier.Utilisateur u " +
                    "where u.email = :email " +
                    "and u.mdp = :pwd");
            query.setParameter("email", email);
            query.setParameter("pwd", pwd);
            System.out.println("Excute query!");
            if (!query.getResultList().isEmpty()){
                utilisateur = (Utilisateur) query.uniqueResult();
                System.out.println("utilisateur existe!");
                return utilisateur;
//                for (Utilisateur u: (List<Utilisateur>) query.list()) {
//                    if (Objects.equals(u.getMdp(), pwd) && Objects.equals(u.getEmail(), email)){
//                        System.out.println("User login: " + "Nom: " + u.getNomU() + "  Prenom: " + u.getPrenomU() + " Email: "
//                                + u.getEmail() + " Mode de passe: " + u.getMdp());
//                    }
//                }
            }
            session.close();
        } catch (Exception e) {
            System.out.println("Exception: Nont Found!");
            e.printStackTrace();
        }
        return utilisateur;
    }

    public static boolean emailExiste(String email) {
        boolean existe = false;
        if (email != null){
            String hql = "from com.example.appelprojet.mertier.Utilisateur u where u.email = :email";
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()){
                getTransaction(session);
                Query<Utilisateur> query = session.createQuery(hql, Utilisateur.class);
                query.setParameter("email", email);
                if (query.uniqueResult()!=null){
                    existe = true;
                }
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existe;
    }

    public static Role findRoleById(Long idU) {
        try (Session session =HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction transaction = getTransaction(session);
            Query q = session.createSQLQuery("SELECT u.Categorie " +
                    "FROM Utilisateur u " +
                    "WHERE u.CodeU = :id");
            q.setParameter("id", idU);
            String categorie = (String) q.uniqueResult();
            session.close();
            switch (categorie){
                case "utilisateur":
                    return Role.ETUDIANT;
                case "etudiant":
                    return Role.ETUDIANT;
                case "enseignant":
                    return Role.ENSEIGNANT;
                default:
                    return Role.SCOLARITE;
            }
        }
    }



}
