package com.example.appelprojet.ctrl;

import com.example.appelprojet.dao.CoursDAO;
import com.example.appelprojet.dao.UtilisateurDAO;
import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PresenceEtudiantCtrl", value = "/presenceEtudiantCtrl")
public class PresenceEtudiantCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        long idU = utilisateur.getIdU();
        if (request.getParameter("idEtudiant")!=null){
            idU = Long.parseLong(request.getParameter("idEtudiant"));
        }
        List<Cours> cours = CoursDAO.findCoursByEtudiantId(idU);
        if (cours != null){
            RequestDispatcher rd = request.getRequestDispatcher("presenceEtudiant");
            request.setAttribute("list_cours", (List<Cours>) cours);
            rd.forward(request,response);
        }else {
            RequestDispatcher rd = request.getRequestDispatcher("message");
            request.setAttribute("message", "NOT FOUND COURS");
            rd.forward(request,response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
