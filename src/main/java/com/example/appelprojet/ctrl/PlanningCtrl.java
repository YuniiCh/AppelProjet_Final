package com.example.appelprojet.ctrl;

import com.example.appelprojet.dao.SeanceDAO;
import com.example.appelprojet.mertier.Planning;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "PlanningCtrl", value = "/planningCtrl")
public class PlanningCtrl extends HttpServlet {
    private static final SimpleDateFormat SDF_FR = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /*----- Récupération des paramètres -----*/

        //        Obtenir l'id séance cliqué du Page Pllanning.jsp
        String idSeance = request.getParameter("idSeance");
        if (idSeance != null){
            SeanceDAO seanceDAO = new SeanceDAO();
            Seance appel = seanceDAO.find(Long.parseLong(idSeance));
            RequestDispatcher rd = request.getRequestDispatcher("appel");
            request.setAttribute("seance", (Seance) appel);
            rd.forward(request, response);
        }else{
            //            Paramètre pour la date du début d'une semaine

            Date getDate = new Date();
            String getStrDate = request.getParameter("date");

            Calendar calendar = Calendar.getInstance();
            String planningAction = request.getParameter("planning_action");
            try {
                if (!(planningAction == null || planningAction.isEmpty()) && !(getStrDate == null || getStrDate.isEmpty())) {
                    try {
                        getDate = SDF.parse(getStrDate);
                    } catch (ParseException e) {
                        getDate = new Date();
                        e.printStackTrace();
                    }
                    calendar.setTime(getDate);
                    switch (planningAction) {
                        case "previous" :
                            calendar.add(Calendar.WEEK_OF_MONTH, -1);
                            getDate = calendar.getTime();
                            break;
                        case "next" :
                            calendar.add(Calendar.WEEK_OF_MONTH, 1);
                            getDate = calendar.getTime();
                            break;
                    }
                }
                /*------Transofoer des données au jsp------*/
                Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
                List<Seance> seances = SeanceDAO.findSeancesSemaine(utilisateur, getDate);
                Planning planning = new Planning(getDate);
                List<Date> week = planning.weekDate;
                Map<String, ArrayList<Seance>> seancesMap = new HashMap<>();
                for (Date d: week){
                    seancesMap.put(SDF.format(d),new ArrayList<Seance>());
                }
                if (seances != null){
                    for (Seance seance:seances){
                        if (!seancesMap.containsKey(SDF.format(seance.getDateDebut()))){
                            seancesMap.get(SDF.format(seance.getDateDebut())).add(seance);
                        }else {
                            seancesMap.put(SDF.format(seance.getDateDebut()), new ArrayList<>(Collections.singletonList(seance)));
                        }
                    }
                }

                RequestDispatcher rd = request.getRequestDispatcher("planning");
                request.setAttribute("week", (List<Date>) week);
                request.setAttribute("date", Planning.getDateWithFormat(getDate, "yyyy-MM-dd"));
                request.setAttribute("seances",  new TreeMap<>(seancesMap) );
                rd.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
                RequestDispatcher rd = request.getRequestDispatcher("message");
                request.setAttribute("avert", "Ne pas pouvoir chargement. ");
                rd.forward(request, response);
            }
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }


}
