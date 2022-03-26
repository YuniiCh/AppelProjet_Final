package com.example.appelprojet.ctrl;

import com.example.appelprojet.dao.EtudiantDAO;
import com.example.appelprojet.dao.UtilisateurDAO;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Utilisateur;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AppelCtrl", value = "/appelCtrl")
public class AppelCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_etat>");

            /*----- Récupération des paramètres -----*/
//            Paramètre pour changer l'état d'un étudiant
            String etat = request.getParameter("etatpresence");
            String etatpresence;

            /*------Mettre des données dans XML------*/
            if(etat != null){
                if (etat.equals("1")) {
                    etatpresence = "<etatpresence>Retard</etatpresence>";
                    out.println("<etatpresence>Retard</etatpresence>");
                } else if (etat.equals("2")) {
                    etatpresence = "<etatpresence>Absent</etatpresence>";
                    out.println("<etatpresence>Absent</etatpresence>");
                }else {
                    etatpresence = "<etatpresence>Présent</etatpresence>";
                    out.println("<etatpresence>Présent</etatpresence>");
                }
                System.out.println(etatpresence);
            }

            //            Paramètre pour changer tous les états pour tous les étudiants
            String touspresence = request.getParameter("touspresence");
            System.out.println(touspresence);
            /*------Mettre des données dans XML------*/
            if(touspresence!=null){
                String allpresence = "Tous Présent";
                if (touspresence.equals("1")) {
                    allpresence = "Tous Absent";
                    out.println("Absent");
                }
                out.println("<allpresence>" + allpresence + "</allpresence>");
                System.out.println(allpresence);
            }


            //            Paramètre pour montrer des étudiants à choisir
            String search = request.getParameter("search_student");
            String seance = request.getParameter("seance");
            System.out.println(touspresence);
            /*------Mettre des données dans XML------*/
            if(search!=null){
                List<Etudiant> list_students = EtudiantDAO.findByName(search);
                System.out.println(list_students);
                if (list_students != null){
                    for (Etudiant student : list_students) {
//                        out.println("<student>" + student.getIdU() + " <![CDATA[" + student.getNomU() + "]]> <![CDATA[" + student.getPrenomU() + "]]></student>");
                        out.println("<student>" + student.getIdU() + "  " + student.getNomU() + " " + student.getPrenomU() + "</student>");
                        System.out.println("<student>" + student.getIdU() + "  " + student.getNomU() + " " + student.getPrenomU() + "</student>");
                    }
                }else {
                    out.println("<student>Ne pas trouver</student>");
                    System.out.println("<student>Ne pas trouver</student>");
                }
            }


            //            Paramètre pour ajouter une un étudiant
            String addstudent = request.getParameter("addstudent");
            System.out.println(addstudent);
            /*------Mettre des données dans XML------*/
//            if (addstudent !=null){
//                try {
//                    //把学生组别换成这个Seance的组
//                    EtudiantDAO etudiantDAO = new EtudiantDAO();
//                    ArrayList<String> list_students = etudiantDAO.find(choix);
//                    if (Bd.existMots(sm) == true) {
//                        Bd.supprimerMot(sm);
//                        out.println("<student>add</student>");
//                        System.out.println("<student>add</student>");
//                    }
//
//                } catch (ClassNotFoundException | SQLException ex) {
//                    out.println("<student>Erreur - " + ex.getMessage() + "</student>");
//                    System.out.println("<student>Erreur - " + ex.getMessage() + "</student>");
//                }
//            }



            //            Paramètre pour supprimer une un étudiant
            String supprimer = request.getParameter("deletestudent");
            System.out.println(supprimer);
            /*------Mettre des données dans XML------*/
            if (supprimer !=null && seance != null){
                out.println("<student>delete</student>");
                System.out.println("<student>delete</student>");
//                try {
//                    //把学生从这个Seance删除
//                    if (Bd.existMots(sm) == true) {
//                        Bd.supprimerMot(sm);
//                        out.println("<student>add</student>");
//                        System.out.println("<student>add</student>");
//                    }
//
//                } catch (ClassNotFoundException | SQLException ex) {
//                    out.println("<student>Erreur - " + ex.getMessage() + "</student>");
//                    System.out.println("<student>Erreur - " + ex.getMessage() + "</student>");
//                }
            }
            out.println("</liste_etat>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
