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
            }

            //            Paramètre pour montrer des étudiants à choisir
            String search = request.getParameter("search_student");
            System.out.println(search);
            /*------Mettre des données dans XML------*/
            if(search!=null){
                Seance seance = (Seance) request.getSession().getAttribute("seance");
                List<Etudiant> tousEtudiants = EtudiantDAO.etudiantsMemeCoursByIdSeance(seance);
                List<Etudiant> etudiants = EtudiantDAO.findEtudiansByID(seance);


                if (tousEtudiants != null){
                    for (Etudiant student : tousEtudiants) {
                        String nom = student.getNomU() + " " + student.getPrenomU() + " " + student.getIdU();
                        if (!etudiants.contains(student) && nom.toLowerCase().contains(search.toLowerCase())) {
                            out.println("<student><id>" + student.getIdU()  + "</id><type>" + student.getTypeEtudiant() + "</type><nom>" + student.getNomU() + " " + student.getPrenomU()  +  "</nom></student>");
                            System.out.println("<student>" + student.getIdU() + "  " + student.getNomU() + " " + student.getPrenomU() + "</student>");
                        }else {
                            out.println("<student>null</student>");
                            System.out.println("<student>Ne pas trouver</student>");
                        }
                    }
                }else{
                    out.println("<student>null</student>");
                }
            }


            //            Paramètre pour ajouter une un étudiant
            String addstudent = request.getParameter("addstudent");
            String supprStudent = request.getParameter("deletestudent");
            System.out.println(addstudent);
            /*------Mettre des données dans XML------*/
            if (addstudent !=null || supprStudent !=null){
                Seance seance = (Seance) request.getSession().getAttribute("seance");
                String idStrEtudiant = addstudent;
                EtudiantDAO etudiantDAO = new EtudiantDAO();
                if (supprStudent != null){
                    idStrEtudiant = supprStudent;
                }
                System.out.println(idStrEtudiant);
                Etudiant etudiant = etudiantDAO.find(Long.parseLong(idStrEtudiant));
                if (supprStudent != null){
                    PresenceDAO.supprEtuPresence(etudiant, seance);
                    out.println("<student>delete</student>");
                }else {
//              requete add student
                    EtudiantDAO.addEtudiantSeanceById(etudiant.getIdU(), seance.getIdSeance());
                    out.println("<student>add</student>");
                }
            }else if((addstudent !=null && supprStudent ==null) || (addstudent ==null && supprStudent !=null)){
                out.println("<student>null</student>");
            }


            //            Parametres pour valider la fiche d'appel
            String etats = request.getParameter("etats");
            String action = request.getParameter("action");
            if (etats != null && action != null){
                String[] idStu_etatStu = etats.split(",");
                System.out.println("Id status: " + idStu_etatStu[0]);
                //            Les infos de la séance
                Seance seance = (Seance) request.getSession().getAttribute("seance");
                EtudiantDAO etudiantDAO = new EtudiantDAO();
                for (String ids : idStu_etatStu){
                    Etudiant etudiant = etudiantDAO.find(Long.parseLong(ids.split(" ")[0]));
                    if (ids.toLowerCase().contains("retard")) {
                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.RETART, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
                    }else if (ids.toLowerCase().contains("absent")){
                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.ABSENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
                        if (action.equals("submit")){
                            FontionsUtiles.notifyAbsenceSeanceByEtu(etudiant,seance);
                        }
                    }else {
                        PresenceDAO.updateEtatPreEtuByID(EtatPresence.PRESENCE, Long.parseLong(ids.split(" ")[0]), seance.getIdSeance());
                    }
                    out.println("<student>update</student>");
                }
                if (action.equals("submit")){
                    SeanceDAO.updateEtatAppelBySeance(seance,  "valide");
                }else {
                    SeanceDAO.updateEtatAppelBySeance(seance,  "enregistre");
                }

            }else {
                out.println("<student>null</student>");
            }

            out.println("</liste_etat>");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

}
