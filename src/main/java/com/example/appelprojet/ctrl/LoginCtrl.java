package com.example.appelprojet.ctrl;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.dao.SeanceDAO;
import com.example.appelprojet.dao.UtilisateurDAO;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;
import com.example.appelprojet.util.Role;
import org.hibernate.SessionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

@WebServlet(name = "LoginCtrl", value = "/loginCtrl")
public class LoginCtrl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("psw");
        System.out.println(email + " && " + password);

        String msg_avert = "";
        if (email==null || email.isEmpty()) {
            msg_avert = "Saisez votre compte";
        }

        if ( password==null || password.isEmpty()) {
            msg_avert = "Saisez votre mot de passe";
        }

        if (msg_avert==null || msg_avert.isEmpty()) {
            try {
                Utilisateur utilisateur = UtilisateurDAO.getLoginInfo(email, password);
                Role role = UtilisateurDAO.findRoleById(utilisateur.getIdU());
                System.out.println("2222 " + password);
                System.out.println("find utilisateur in LoginCtrl " + utilisateur.getNomU());
                if (UtilisateurDAO.emailExiste(email))  {
                    if(!email.equals(utilisateur.getEmail())){
                        msg_avert = "Compte ne correspond pas";
                    }else if (!password.equals(utilisateur.getMdp())) {
                        msg_avert = "Mot de passe ne correspond pas";
                    }
                } else{
                    msg_avert = "Compte n'existe pas";
                }

                if(msg_avert.isEmpty()) {
                    HttpSession session = request.getSession(true);
//                    session.setAttribute("idu", utilisateur.getIdU());
                    session.setAttribute("utilisateur", (Utilisateur) utilisateur);
                    session.setAttribute("role", (Role) role);
                    if (role== Role.ENSEIGNANT){
                        System.out.println("get: " + Role.ENSEIGNANT);
                        if (SeanceDAO.isFindSeanceActuelByUser(utilisateur)) {
                            System.out.println("Find: Seance!");
                            Seance ficheAppel = SeanceDAO.infoFicheAppel(utilisateur);
//                        HttpSession session = request.getSession();
//                           session.setAttribute("seance", ficheAppel.getIdSeance());
                            session.setAttribute("seance", (Seance) ficheAppel);
                            System.out.println("Seance actelle: " + ficheAppel.toString());
                            System.out.println("Seance is not null!");
                            RequestDispatcher rd = request.getRequestDispatcher("appel");
                            request.setAttribute("seance", (Seance) ficheAppel);
                            rd.forward(request, response);
                        } else {
                            System.out.println("if not found seances!");
                            request.getRequestDispatcher("planningCtrl").forward(request, response);
//                           request.getRequestDispatcher("coursCtrl").forward(request,response);
                        }
                    } else if (role ==Role.ETUDIANT) {
                        System.out.println("get: " + Role.ETUDIANT);
                        request.getRequestDispatcher("presenceEtudiantCtrl").forward(request,response);
//                       request.getRequestDispatcher("uploadFile").forward(request, response);
                    }else {
                        System.out.println("get: " + Role.SCOLARITE);
                        request.getRequestDispatcher("download").forward(request, response);
                    }
                } else {
                    RequestDispatcher rd = request.getRequestDispatcher("login");
                    request.setAttribute("avert", msg_avert);
                    rd.forward(request, response);
                }
            }catch (Exception e){
                e.printStackTrace();
                msg_avert ="Erreur Technique";
                RequestDispatcher rd = request.getRequestDispatcher("message");
                request.setAttribute("message", msg_avert);
                rd.forward(request, response);
            }
        }
//        else {
//            request.getRequestDispatcher("login").forward(request,response);
//        }



    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>Connection OK</h1>");
//        out.println("</body></html>");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
