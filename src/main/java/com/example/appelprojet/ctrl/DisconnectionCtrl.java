package com.example.appelprojet.ctrl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DisconnectionCtrl", value = "/disconnectionCtrl")
public class DisconnectionCtrl extends HttpServlet {
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(true);
            // go to login page
            session.removeAttribute("utilisateur");
            session.removeAttribute("idU");
            session.removeAttribute("idu");
            session.removeAttribute("idSeance");
            request.getRequestDispatcher("login").forward(request, response);
            session.invalidate();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
