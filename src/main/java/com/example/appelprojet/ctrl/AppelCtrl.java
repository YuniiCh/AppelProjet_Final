package com.example.appelprojet.ctrl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "AppelCtrl", value = "/appelCtrl")
public class AppelCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*----- Type de la réponse -----*/
        response.setContentType("application/xml;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /*----- Ecriture de la page XML -----*/
            out.println("<?xml version=\"1.0\"?>");
            out.println("<liste_etat>");

            /*----- Récupération des paramètres -----*/
            String etat = request.getParameter("etatpresence");
            String etatpresence = "";
            System.out.println(etat);
            /*------Mettre des données dans XML------*/
            if (etat.equals("2")) {
                etatpresence = "<etatpresence>Retard</etatpresence>";
                out.println("<etatpresence>Retard</etatpresence>");
            } else if (etat.equals("3")) {
                etatpresence = "<etatpresence>Absent</etatpresence>";
                out.println("<etatpresence>Absent</etatpresence>");
            }else {
                etatpresence = "<etatpresence>Présent</etatpresence>";
                out.println("<etatpresence>Présent</etatpresence>");
//            switch (etat){
//                case "2":
//                    etatpresence = "<etatpresence>Retard</etatpresence>";
//                    out.println("<etatpresence>Retard</etatpresence>");
//                case "3":
//                    etatpresence = "<etatpresence>Absent</etatpresence>";
//                    out.println("<etatpresence>Absent</etatpresence>");
//                default:
//                    etatpresence = "<etatpresence>Présent</etatpresence>";
//                    out.println("<etatpresence>Présent</etatpresence>");
            }
            System.out.println(etatpresence);
            out.println("</liste_etat>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
