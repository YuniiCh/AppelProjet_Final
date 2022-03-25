package com.example.appelprojet.ctrl;

import com.example.appelprojet.mertier.Enseignant;
import com.example.appelprojet.mertier.Seance;
import com.example.appelprojet.mertier.Utilisateur;
import com.example.appelprojet.mertier.Planning;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "PlanningCtrl", value = "/planningCtrl")
public class PlanningCtrl extends HttpServlet {
    private static final SimpleDateFormat SDP_FR = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat SDF_OLD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

        Utilisateur utilisateur = (Utilisateur) request.getAttribute("utilisateur");
        Enseignant enseignant = (Enseignant) utilisateur;
        Date date = new Date();
        long dayMonth;
        String strDate = request.getParameter("date");
        Calendar calendar = Calendar.getInstance();
        String planningAction = request.getParameter("planning_action");
        HashMap<Long, ArrayList<Seance>> seancesFilter = new HashMap<>();

        try {
            if (!(planningAction == null || planningAction.isEmpty()) && !(strDate == null || strDate.isEmpty())) {
                date = SDF_OLD_FORMAT.parse(strDate);
                calendar.setTime(date);
                switch (planningAction) {
                    case "previous" :
                        calendar.add(Calendar.WEEK_OF_MONTH, -1);
                        date = calendar.getTime();
                        break;
                    case "next" :
                        calendar.add(Calendar.WEEK_OF_MONTH, 1);
                        date = calendar.getTime();
                        break;
                }
            }
            calendar.setTime(SDP_FR.parse(SDP_FR.format(date)));
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            Date firstDayOfWeek = calendar.getTime();
            for (int i = 2; i <= 7; i++) {
                seancesFilter.put(calendar.getTimeInMillis(), new ArrayList<>());
                calendar.add(Calendar.DATE, 1);
            }
            Date lastDayOfWeek = calendar.getTime();

            Set<Seance> seances = enseignant.getSeances();
            for (Seance seance : seances) {
                if (Planning.isWeek(seance.getDateDebut(), firstDayOfWeek, lastDayOfWeek)) {
                    calendar.setTime(seance.getDateDebut());
//                    dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    dayMonth = SDP_FR.parse(SDP_FR.format(seance.getDateDebut())).getTime();
                    if (seancesFilter.containsKey(dayMonth)) {
                        seancesFilter.get(dayMonth).add(seance);
                    } else {
                        seancesFilter.put(dayMonth, new ArrayList<>(Collections.singletonList(seance)));
                    }
                }
            }
            request.setAttribute("date", Planning.getDateWithFormat(date, "dd-MM-yyyy"));
            request.setAttribute("seancesFilter", new TreeMap<>(seancesFilter));
            request.getRequestDispatcher("planning").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }
}
