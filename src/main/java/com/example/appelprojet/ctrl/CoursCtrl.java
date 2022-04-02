package com.example.appelprojet.ctrl;

import com.example.appelprojet.dao.CoursDAO;
import com.example.appelprojet.dao.EnseignantDAO;
import com.example.appelprojet.dao.SeanceDAO;
import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Enseignant;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CoursCtrl", value = "/coursCtrl")
public class CoursCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        String nomCours = request.getParameter("select_cours");
        String idC = request.getParameter("idC");
        List<Cours> cours = CoursDAO.findCoursByEnseignantId(utilisateur.getIdU());
        List<Cours> coursList = new ArrayList<>();
        if (nomCours!=null){
            System.out.println("nom cours" + nomCours);
            if (cours!=null){
                for (Cours c: cours){
                    if (c.getNomCours().contains(nomCours)){
                        coursList.add(c);
                    }
                }
            }else {
                coursList = cours;
            }

            RequestDispatcher rd = request.getRequestDispatcher("cours");
            request.setAttribute("list_cours", (List<Cours>) coursList);
            rd.forward(request,response);
        }else if (idC != null){
            List<Seance> seances = SeanceDAO.findSeanceByIdUserCours(utilisateur.getIdU(), Long.parseLong(idC));
            if(seances != null){
                RequestDispatcher rd = request.getRequestDispatcher("listAppel");
                request.setAttribute("seances", (List<Seance>) seances);
                rd.forward(request,response);
            }else{
                String avert = "Utilisateur n'a pas encore de seances passés";
                RequestDispatcher rd = request.getRequestDispatcher("message");
                request.setAttribute("message", avert);
                rd.forward(request,response);
            }
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("cours");
            request.setAttribute("list_cours", (List<Cours>) cours);
            rd.forward(request,response);
        }


    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
