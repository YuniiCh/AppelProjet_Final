package com.example.appelprojet.ctrl;

import com.example.appelprojet.dao.CoursDAO;
import com.example.appelprojet.mertier.Cours;
import com.example.appelprojet.mertier.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StatisticEnseignantCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
        List<Cours> cours = CoursDAO.findCoursByEnseignantId(utilisateur.getIdU());
        if (cours != null){
            RequestDispatcher rd = request.getRequestDispatcher("statisticEnseignant");
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
