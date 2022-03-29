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
    SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                    /*----- Récupération des paramètres -----*/
//            Paramètre pour la date du début d'une semaine

            Date getDate = new Date();
            String getStrDate = request.getParameter("date");
            System.out.println(getStrDate);

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
                System.out.println(getDate);
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
            System.out.println("Planning date: " + getDate);
            /*------Transofoer des données au jsp------*/
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            System.out.println(utilisateur.getIdU() + " " + utilisateur.getNomU() + " prenom " + utilisateur.getPrenomU());
            List<Seance> seances = SeanceDAO.findSeancesSemaine(utilisateur, getDate);
            Planning planning = new Planning(getDate);
            List<Date> week = planning.weekDate;
            Map<String, List<Seance>> seancesMap = planning.weekPlanning;
            if (seances != null){
                for (Seance seance:seances){
                    seancesMap.get(Planning.getDateWithFormat(seance.getDateDebut(), "yyyy-MM-dd")).add(seance);
                }
            }
            System.out.println("SeancesMap: " + seancesMap);
            RequestDispatcher rd = request.getRequestDispatcher("planning");
            request.setAttribute("week", (List<Date>) week);
            request.setAttribute("date", Planning.getDateWithFormat(getDate, "yyyy-MM-dd"));
            request.setAttribute("seances", (TreeMap<String, List<Seance>>) seancesMap );
            rd.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
            RequestDispatcher rd = request.getRequestDispatcher("profil");
            request.setAttribute("avert", "Ne pas pouvoir chargement. ");
            rd.forward(request, response);
        }

        //        Obtenir l'id séance cliqué du Page Pllanning.jsp
        String idSeance = request.getParameter("idSeance");
        SeanceDAO seanceDAO = new SeanceDAO();
        Seance seance = seanceDAO.find(Long.parseLong(idSeance));
        if (idSeance != null){
            RequestDispatcher rd = request.getRequestDispatcher("appel");
            request.setAttribute("seance", (Seance) seance);
            rd.forward(request, response);
        }else{
            RequestDispatcher rd = request.getRequestDispatcher("message");
            request.setAttribute("avert", "Ne pas trouver!");
            rd.forward(request, response);
        }

//        /*----- Type de la réponse -----*/
//        String name = "planningCtrlXML";
////        response.setHeader("Content-Disposition", "attachment; filename=" + name + ".bpmn20.xml");
//        response.setContentType("application/xml;charset=UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /*----- Ecriture de la page XML -----*/
////            out.println("<?xml version=\"1.0\"?>");
//            out.println("<?xml version='1.0' encoding='UTF-8'?>");
//            out.println("<liste_seances>");
//
//            /*----- Récupération des paramètres -----*/
////            Paramètre pour la date du début d'une semaine
//            String getStrDate = request.getParameter("date");
//            Date getDate = new Date();
//            System.out.println(getStrDate);
//            if (getStrDate !=null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    getDate = sdf.parse(getStrDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println(getDate);
//            /*------Mettre des données dans XML------*/
//            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
//            System.out.println(utilisateur.getIdU() + " " + utilisateur.getNomU() + " prenom " + utilisateur.getPrenomU());
//            List<Seance> seances = SeanceDAO.findSeancesSemaine(utilisateur, getDate);
//            Planning planning = new Planning(getDate);
//            List<Date> week = planning.weekDate;
//            StringBuilder semaine = new StringBuilder();
//            for (Date d : week) {
//                semaine.append(" ").append(Planning.getDateWithFormat(d, "yyyy-MM-dd"));
//            }
//            out.println("<semaine>" + semaine +"</semaine>");
//
//
//            if (seances != null){
//                System.out.println("Seance : " + seances.get(0));
//                for (Seance seance : seances){
//                    float startTime = seance.getDateDebut().getHours();
//                    float endTime = seance.getDateDebut().getHours();
//                    if (seance.getDateDebut().getMinutes() == 30){
//                        startTime = seance.getDateDebut().getHours() + 0.5F;
//                    }
//                    if (seance.getDateFin().getMinutes()== 30){
//                        endTime = seance.getDateFin().getHours() + 0.5F;
//                    }
//                    int startLine = (int) ((startTime - 8) / 0.5 + 1);
//                    int endLine = (int) ((endTime - 8) / 0.5 + 1);
//                    out.println("<seance><id>" + seance.getIdSeance() +
//                            "</id><cours>" + seance.getCours().getNomCours().toUpperCase() + " " + seance.getCours().getTypeCours().toUpperCase() + "</cours>" +
//                            "<week>" + planning.day + "</week>" +
//                            "<temps>" + startLine + "-" + endLine + "</temps>" +
//                            "<enseignant>" + seance.getEnseignant().getNomU() +" " + seance.getEnseignant().getPrenomU() + "</enseignant>" +
//                            "<salle>" + seance.getSalle().getNomSalle() + "</salle>" +
//                            "</seance>");
//                }
//            }else{
//                System.out.println("Seance : NULL");
//                out.println("<seance>null</seance>");
//            }
//            out.println("</liste_seances>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            doGet(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

//        Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
//        Enseignant enseignant = (Enseignant) utilisateur;
//        Date date = new Date();
//        long dayMonth;
//        String strDate = request.getParameter("date");
//        Calendar calendar = Calendar.getInstance();
//        String planningAction = request.getParameter("planning_action");
//        HashMap<Long, ArrayList<Seance>> seancesFilter = new HashMap<>();
//
//        try {
//            if (!(planningAction == null || planningAction.isEmpty()) && !(strDate == null || strDate.isEmpty())) {
//                date = SDF_OLD_FORMAT.parse(strDate);
//                calendar.setTime(date);
//                switch (planningAction) {
//                    case "previous" :
//                        calendar.add(Calendar.WEEK_OF_MONTH, -1);
//                        date = calendar.getTime();
//                        break;
//                    case "next" :
//                        calendar.add(Calendar.WEEK_OF_MONTH, 1);
//                        date = calendar.getTime();
//                        break;
//                }
//            }
//            calendar.setTime(SDP_FR.parse(SDP_FR.format(date)));
//            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            Date firstDayOfWeek = calendar.getTime();
//            for (int i = 2; i <= 7; i++) {
//                seancesFilter.put(calendar.getTimeInMillis(), new ArrayList<>());
//                calendar.add(Calendar.DATE, 1);
//            }
//            Date lastDayOfWeek = calendar.getTime();
//
//            Set<Seance> seances = enseignant.getSeances();
//            for (Seance seance : seances) {
//                if (Planning.isWeek(seance.getDateDebut(), firstDayOfWeek, lastDayOfWeek)) {
//                    calendar.setTime(seance.getDateDebut());
////                    dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//                    dayMonth = SDP_FR.parse(SDP_FR.format(seance.getDateDebut())).getTime();
//                    if (seancesFilter.containsKey(dayMonth)) {
//                        seancesFilter.get(dayMonth).add(seance);
//                    } else {
//                        seancesFilter.put(dayMonth, new ArrayList<>(Collections.singletonList(seance)));
//                    }
//                }
//            }
//            request.setAttribute("date", Planning.getDateWithFormat(date, "dd-MM-yyyy"));
//            request.setAttribute("seancesFilter", new TreeMap<>(seancesFilter));
//            request.getRequestDispatcher("planning").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
