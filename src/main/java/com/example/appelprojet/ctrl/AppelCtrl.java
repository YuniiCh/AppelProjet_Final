package com.example.appelprojet.ctrl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

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

            //            Paramètre pour ajouter une un étudiant
            String ajouter = request.getParameter("ajoute");
            System.out.println(touspresence);
            /*------Mettre des données dans XML------*/
            if (ajouter !=null){
                String ajouteEtudiant = "ajouter";
                if (ajouter.equals("1")) {
                    ajouteEtudiant = "ajouter";
                    out.println("ajouter");
                }
                out.println("<ajouter>" + ajouteEtudiant + "</ajouter>");
                System.out.println(ajouteEtudiant);
            }


            //            Paramètre pour supprimer une un étudiant
            String supprimer = request.getParameter("supperimer");
            System.out.println(touspresence);
            /*------Mettre des données dans XML------*/
            if (supprimer !=null){
                String supEtudiant = "supprimer";
                if (supprimer.equals("1")) {
                    supEtudiant = "supperimer";
                    out.println("supperimer");
                }
                out.println("<supperimer>" + supEtudiant + "</supperimer>");
                System.out.println(supEtudiant);
            }
            out.println("</liste_etat>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
