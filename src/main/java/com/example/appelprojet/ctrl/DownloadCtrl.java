package com.example.appelprojet.ctrl;

import com.example.appelprojet.config.HibernateUtil;
import com.example.appelprojet.mertier.*;
import com.example.appelprojet.util.EtatPresence;
import com.example.appelprojet.util.EtatValidation;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DownloadCtrl", value = "/downloadCtrl")
public class DownloadCtrl extends HttpServlet {
    public static final String UPLOAD_DIRECTORY = "upload";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // préparer les données
        String avis = request.getParameter("avis");
        String[] idJSelect= request.getParameterValues("idJSelect");
        String[] idSS= request.getParameterValues("idSSelect");
        String message = "";
        HttpSession sessionHttp = request.getSession(true);
        sessionHttp.setAttribute("action","pageDownload");

        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction t = session.beginTransaction();
            for (int i = 0; i < idJSelect.length; i++) {

                // préparer les données
                Justificatif justificatif = session.get(Justificatif.class, Long.valueOf(idJSelect[i]));
                Seance seance = session.get(Seance.class, Long.valueOf(idSS[i]));
                Justifier justifier = justificatif.getJustifiers().get(seance);
                Etudiant etudiant = justificatif.getEtudiant();
                Presence p = etudiant.getSeanPresences().get(seance);

                switch (avis) {
                    // en cas de l'acceptation de justificatif
                    case "accept":
                        if(seance.getDateDebut().before(justificatif.getDateDepot())){
                            justifier.setEtatValidation(EtatValidation.SIGNALER);
                            p.setEtatPresence(EtatPresence.ABSENCE_SIGNALE);
                        }else {
                            justifier.setEtatValidation(EtatValidation.ACCEPT);
                            p.setEtatPresence(EtatPresence.ABSENCE_JUSTIFIE);
                        }

                        session.update(justifier);
                        session.saveOrUpdate(p);
                        break;

                    // en cas de la refuse de justificatif
                    case "refuser":
                        justifier.setEtatValidation(EtatValidation.REFUSE);
                        p.setEtatPresence(EtatPresence.ABSENCE_NON_JUSTIFIE);
                        session.update(justifier);
                        session.saveOrUpdate(p);
                        break;

                    default:
                        continue;
                }
            }
            t.commit();
            session.close();
            RequestDispatcher rd = request.getRequestDispatcher("message");
            message = "L'enregistrement a réussi!";
            sessionHttp.setAttribute("message", message);
            rd.forward(request, response);

        }catch (Exception e){
            e.printStackTrace();
            RequestDispatcher rd = request.getRequestDispatcher("message");
            message = "L'enregistrement a échoué!";
            sessionHttp.setAttribute("message", message);
            rd.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
