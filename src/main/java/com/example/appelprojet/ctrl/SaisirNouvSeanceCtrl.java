package com.example.appelprojet.ctrl;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.*;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import org.hibernate.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SaisirNouvSeanceCtrl", value = "/saisirNouvSeanceCtrl")
public class SaisirNouvSeanceCtrl extends HttpServlet {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_salle>");

            /*----- Récupération des paramètres -----*/
            String dateDebut = request.getParameter("dateDebut");
            String dateFin = request.getParameter("dateFin");


            /*----- Lecture de liste de mots dans la BD -----*/
            List<Salle> lSalles = SalleDAO.salleDispoCeMoment(dateDebut,dateFin);

            for (Salle salle : lSalles){

                out.println("<salle><![CDATA[" + salle.getNomSalle() + "]]></salle>");
            }

            out.println("</liste_salle>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long idFormation= Long.valueOf(request.getParameter("lFormation"));
        String dateDebut = request.getParameter("dateDebut");
        String dateFin = request.getParameter("dateFin");
        String idEns= request.getParameter("lEns");
        String nomCours= request.getParameter("lCours");
        String nomSalle = request.getParameter("lSalle");

        String coursType= request.getParameter("lCoursTy");
        Formation formation = new FormationDAO().find(idFormation);
        Cours coursS = null;
        Salle salleS = new Salle();

        HttpSession sessionHttp = request.getSession(true);
        sessionHttp.setAttribute("action","saisirSea");

        System.out.println(nomCours+coursType+nomSalle+idFormation);

        try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = sn.beginTransaction();
            List<Cours> lCours = new CoursDAO().findAll();
            for (Cours cours : lCours) {
                System.out.println(cours.getNomCours() + cours.getTypeCours() + cours.getFormation().getNomFormation());
                if (cours.getNomCours().equals(nomCours) && cours.getTypeCours().equals(coursType) && cours.getFormation().getIdFormation()==idFormation) {
                    coursS = cours;

                    System.out.println("-----------1");
                    break;
                }
            }
        }

        try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = sn.beginTransaction();
            List<Salle> lSalles = new SalleDAO().findAll();
            for (Salle salle : lSalles) {
                System.out.println(salle.getNomSalle());
                if (salle.getNomSalle().equals(nomSalle)) {
                    salleS = salle;
                    break;
                }
            }
        }

        Enseignant enseignant = new EnseignantDAO().find(Long.valueOf(idEns));
        List<Etudiant> etudiantsDesigne = EtudiantDAO.listeEtudiantDapresFormationTD(coursType,idFormation);
        try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = sn.beginTransaction();

            System.out.println(coursS);
            Seance seance = new Seance(df.parse(dateDebut),df.parse(dateFin),null,coursS,enseignant,salleS);
            sn.save(seance);

            for (Etudiant etudiant:etudiantsDesigne){
                Presence presence = new Presence(null,new PresenceID(seance.getIdSeance(),etudiant.getIdU()),new Justificatif());
                sn.save(presence);
            }
            t.commit();
            sn.close();

        } catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher rd = request.getRequestDispatcher("message");
            sessionHttp.setAttribute("message", e);
            rd.forward(request, response);
        }

        RequestDispatcher rd = request.getRequestDispatcher("message");
        sessionHttp.setAttribute("message", "saisirSea");
        rd.forward(request, response);

    }
}
