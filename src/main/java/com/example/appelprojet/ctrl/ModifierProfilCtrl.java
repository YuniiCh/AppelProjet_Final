package com.example.appelprojet.ctrl;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.UtilisateurDAO;
import com.example.appelprojet.mertier.Utilisateur;
import org.hibernate.Transaction;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ModifierProfilCtrl", value = "/modifierProfilCtrl")
public class ModifierProfilCtrl extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // préparer les données
        String idU= request.getParameter("id");
        Utilisateur utilisateur = new UtilisateurDAO().find(Long.valueOf(idU));
        String mail= request.getParameter("mail");
        utilisateur.setEmail(mail);

        // préparer les données forward
        HttpSession sessionHttp = request.getSession(true);
        sessionHttp.setAttribute("action","modifProfil");
        RequestDispatcher rd = request.getRequestDispatcher("message");

        // enregistrer les utilisateurs
        try (org.hibernate.Session sn = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = sn.beginTransaction();
            sn.update(utilisateur);
            t.commit();
            sn.close();
        }catch (Exception e) {
            e.printStackTrace();

            sessionHttp.setAttribute("message", e);
            rd.forward(request, response);
        }

        sessionHttp.setAttribute("message", "Le mail est modifié ! Faut reconnecter au System.");
        rd.forward(request, response);
    }
}
