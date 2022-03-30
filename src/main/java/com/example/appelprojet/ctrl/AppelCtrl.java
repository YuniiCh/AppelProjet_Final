package com.example.appelprojet.ctrl;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.EtudiantDAO;
import com.example.appelprojet.dao.PresenceDAO;
import com.example.appelprojet.dao.SeanceDAO;
import com.example.appelprojet.mertier.Etudiant;
import com.example.appelprojet.mertier.Presence;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.FontionsUtiles;
import org.hibernate.Transaction;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@WebServlet(name = "AppelCtrl", value = "/appelCtrl")
public class AppelCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_etat>");


            /*----- Récupération des paramètres -----*/
//            Paramètre pour changer l'état d'un student
            String etat = request.getParameter("etatpresence");
            String etatpresence;
            System.out.println(etat);

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

            //            Paramètre pour montrer des étudiants à choisir
            String search = request.getParameter("search_student");
            System.out.println(search);
            /*------Mettre des données dans XML------*/
            if(search!=null){
                List<Etudiant> list_students = EtudiantDAO.findByName(search);
                if (list_students != null){
                    for (Etudiant student : list_students) {
//                        out.println("<student>" + student.getIdU() + " <![CDATA[" + student.getNomU() + "]]> <![CDATA[" + student.getPrenomU() + "]]></student>");
                        out.println("<student>" + student.getIdU()  + ", " + student.getFormation().getNomFormation() + ", " + student.getNomU() + " " + student.getPrenomU()  +  "</student>");
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
            if (request.getParameter("deletestudent")!=null){
                //            Les infos de la séance
                Seance seance = (Seance) request.getSession().getAttribute("seance");
                String supprimer = request.getParameter("deletestudent");
//                String supprimer = request.getParameter("deletestudent").split("_")[0];
                System.out.println(supprimer);
//            HttpSession session = request.getSession(true);
//                String idSeance = request.getParameter("deletestudent").split("_")[1];
                /*------Mettre des données dans XML------*/
                if (supprimer !=null){
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
                }else if (seance != null){
                    out.println("<student>Non Id Seance</student>");
                    System.out.println("<student>Non Id Seance</student>");
                }
            }

            //            Parametres pour valider la fiche d'appel
            String etats = request.getParameter("etats");
            String action = request.getParameter("action");
            System.out.println(etats);
            if (etats != null && action != null){
                String[] idStu_etatStu = etats.split(",");
                System.out.println("Id status: " + idStu_etatStu[0]);
                //            Les infos de la séance
                Seance seance = (Seance) request.getSession().getAttribute("seance");
                System.out.println("Seance: " + seance);
                EtudiantDAO etudiantDAO = new EtudiantDAO();
                for (String ids : idStu_etatStu){
                    Etudiant etudiant = etudiantDAO.find(Long.parseLong(ids.split(" ")[0]));
                    System.out.println("Etudiant: " + etudiant);
                    if (ids.toLowerCase().contains("retard")) {
                        System.out.println(ids.split(" ")[0]);
                        System.out.println(Long.parseLong(ids.split(" ")[0]));
                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.RETART, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
                    }else if (ids.toLowerCase().contains("absent")){
                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.ABSENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
                        if (action.equals("submit")){
                            System.out.println("Send email pour la seance: " + seance);
                            FontionsUtiles.notifyAbsenceSeanceByEtu(etudiant,seance);
                            System.out.println("Send email successful: presence : " + etudiant);
                        }
                    }else {
                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.PRESENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
                    }
                    out.println("<student>update</student>");
                    System.out.println("<student>update</student>");
                }
                if (action.equals("submit")){
                    SeanceDAO.updateEtatAppelBySeance(seance,  "valide");
                }else {
                    SeanceDAO.updateEtatAppelBySeance(seance,  "enregistre");
                }

            }else {
                out.println("<student>null</student>");
                System.out.println("<student>null</student>");
            }


////            Parametres pour valider la fiche d'appel
//            String etats = request.getParameter("etats");
//            System.out.println(etats);
//            if (etats != null){
//                String[] idStu_etatStu = etats.split(",");
//                System.out.println("Id status: " + idStu_etatStu[0]);
//                //            Les infos de la séance
//                Seance seance = (Seance) request.getSession().getAttribute("seance");
//                System.out.println("Seance: " + seance);
//                EtudiantDAO etudiantDAO = new EtudiantDAO();
//                for (String ids : idStu_etatStu){
//                    Etudiant etudiant = etudiantDAO.find(Long.parseLong(ids.split(" ")[0]));
//                    System.out.println("Etudiant: " + etudiant);
//                    if (ids.toLowerCase().contains("retard")) {
//                        System.out.println(ids.split(" ")[0]);
//                        System.out.println(Long.parseLong(ids.split(" ")[0]));
//                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.RETART, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
//                    }else if (ids.toLowerCase().contains("absent")){
//                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.ABSENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
//                        System.out.println("Send email pour la seance: " + seance);
//                        FontionsUtiles.notifyAbsenceSeanceByEtu(etudiant,seance);
//                        System.out.println("Send email successful: presence : " + etudiant);
//                    }else {
//                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.PRESENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
//                    }
//                    out.println("<student>update</student>");
//                    System.out.println("<student>update</student>");
//                }
//                SeanceDAO.updateEtatAppelBySeance(seance,  "valide");
//            }else {
//                    out.println("<student>null</student>");
//                    System.out.println("<student>null</student>");
//            }


////            Enregistrer les états
//            String etatsSave = request.getParameter("etatsSave");
//            System.out.println(etatsSave);
//            if (etatsSave != null){
//                String[] idStu_etatStu = etatsSave.split(",");
//                System.out.println("Id status: " + idStu_etatStu[0]);
//                //            Les infos de la séance
//                Seance seance = (Seance) request.getSession().getAttribute("seance");
//                System.out.println("Seance: " + seance);
//                EtudiantDAO etudiantDAO = new EtudiantDAO();
//                for (String ids : idStu_etatStu){
//                    Etudiant etudiant = etudiantDAO.find(Long.parseLong(ids.split(" ")[0]));
//                    System.out.println("Etudiant: " + etudiant);
//                    try {
//                        if (ids.toLowerCase().contains("retard")) {
//                            System.out.println(ids.split(" ")[0]);
//                            System.out.println(Long.parseLong(ids.split(" ")[0]));
//                            PresenceDAO.updateEtatPreEtuByID(EtatPresence.RETART, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
//                        }else if (ids.toLowerCase().contains("absent")){
//                            PresenceDAO.updateEtatPreEtuByID(EtatPresence.ABSENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
//                        }else {
//                            PresenceDAO.updateEtatPreEtuByID(EtatPresence.PRESENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
//                        }
//                        out.println("<student>update</student>");
//                        System.out.println("<student>update</student>");
//                    }catch (Exception e){
//                        out.println("<student>null</student>");
//                        System.out.println("<student>null</student>");
//                    }
//                }
//                SeanceDAO.updateEtatAppelBySeance(seance, "enregistre");
//            }

            out.println("</liste_etat>");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

//    public void senMailAbsence(Etudiant etudiant, Seance seance) throws Exception {
//
//        FontionsUtiles.notifyAbsenceSeance(etudiant, seance);
//    }

}
