<%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/22
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.appelprojet.mertier.Planning" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="com.example.appelprojet.mertier.Utilisateur" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appelprojet.dao.SeanceDAO" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.TreeMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="/css/planning.css"%>
    </style>
    <title>Planning</title>
<%--    <link href="${pageContext.request.contextPath}/css/planning.css" rel="stylesheet" type="text/css">--%>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%
    String date = Planning.getDateWithFormat(new Date(), "yyyy-MM-dd");
    if (request.getAttribute("date") != null) {
        date = (String) request.getAttribute("date");
    }
%>


<div class="d-sm-flex justify-content-between align-items-center mb-4">
    <h3 class="text-dark mb-0">Planning</h3>
</div>

<!-- .start timeline -->
<div class="container">
    <div class="p-3" style="background: #f5f5f5;">
        <form method="post" action="planningCtrl">
            <div class="row">
                <div class="col-sm-6 col-md-6 col-lg-6 col-xl-6 d-sm-flex justify-content-sm-center justify-content-lg-center justify-content-xl-center">
                    <div class="row">
                        <div class="col-4 col-sm-4 col-md-4 d-flex d-sm-flex justify-content-center align-items-center justify-content-sm-center align-items-sm-center"><label for="date" class="col-form-label d-md-flex justify-content-md-center align-items-md-center">Date</label></div>
                        <div class="col-6 col-sm-8 col-md-8 d-flex d-sm-flex d-md-flex justify-content-center align-items-center justify-content-sm-center align-items-sm-center justify-content-md-center align-items-md-center"><input id="date" name="date" type="date" value="<%= date %>"></div>
                    </div>
                </div>
                <div class="col-sm-6 col-md-6 col-lg-6 col-xl-6 d-flex d-sm-flex justify-content-center align-items-center justify-content-sm-center align-items-sm-center justify-content-lg-center justify-content-xl-center"><button id="rechercher" class="btn btn-primary d-xl-flex" type="submit" name="planning_action" value="search" >Rechercher</button></div>
                <div class="next_previous">
                    <button type="submit" name="planning_action" value="previous">Précédent</button>
                    <button type="submit" name="planning_action" value="next">Suivant</button>
                </div>
            </div>
        </form>
    </div>
</div>


<div class="cd-schedule loading">
    <div class="timeline">
        <ul>
<%--            <li><span>08:00</span></li>--%>
<%--            <li><span>08:30</span></li>--%>
            <li><span>09:00</span></li>
            <li><span>09:30</span></li>
            <li><span>10:00</span></li>
            <li><span>10:30</span></li>
            <li><span>11:00</span></li>
            <li><span>11:30</span></li>
            <li><span>12:00</span></li>
            <li><span>12:30</span></li>
            <li><span>13:00</span></li>
            <li><span>13:30</span></li>
            <li><span>14:00</span></li>
            <li><span>14:30</span></li>
            <li><span>15:00</span></li>
            <li><span>15:30</span></li>
            <li><span>16:00</span></li>
            <li><span>16:30</span></li>
            <li><span>17:00</span></li>
            <li><span>17:30</span></li>
            <li><span>18:00</span></li>
        </ul>
    </div> <!-- .cd-schedule__timeline -->

<%--    Planning   --%>
<div class="events">
    <ul class="wrap">
            <%
//                Date getDate = (Date) request.getAttribute("monday");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    getDate = sdf.parse(date);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(getDate);

                /*------Obtenir des données------*/
                Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
                System.out.println(utilisateur.getIdU() + " " + utilisateur.getNomU() + " prenom " + utilisateur.getPrenomU());
                TreeMap<String, List<Seance>> seancesMap = (TreeMap<String, List<Seance>>) request.getAttribute("seances");
//                Planning planning = new Planning(getDate);
//                List<Date> week = planning.weekDate;
//                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
//                StringBuilder semaine = new StringBuilder();
                System.out.println("Planning jsp page:  " + seancesMap);
                System.out.println("Seances Map:  " + seancesMap.keySet());
                for (String d: seancesMap.keySet()){
                    out.println("<li class=\"events-group\"> <div class=\"top-info\">");
                    out.println("<span>" + d + "</span></div><ul>");
                    if (seancesMap.get(d) != null) {
                        for (Seance seance : seancesMap.get(d)) {
                            String dataEvent = seance.getEtatAppel().equals("true")? "event-1" : "event-2";
                            String appelUrl = "planningCtrl?idSeance=" + seance.getIdSeance();
                            out.println("<li class=\"single-event\">");
                            out.println("<a data-start='" + Planning.getDateWithFormat(seance.getDateDebut(),"HH:mm") + "' ");
                            out.println("data-end=\"" + Planning.getDateWithFormat(seance.getDateFin(), "HH:mm") +"\" ");
                            out.println("data-content= 'event-rowing-workout' data-event=\"" + dataEvent + "\" ");
                            out.println("href=\"" + appelUrl + "\">");
                            out.println("<em class=\"event-name\">" + seance.getCours().getNomCours().toUpperCase() +" " +seance.getCours().getFormation().getNomFormation().toUpperCase() + "<br>" + seance.getEnseignant().getNomU().toUpperCase() + " " + seance.getEnseignant().getPrenomU().toUpperCase() + "<br>" + seance.getSalle().getNomSalle().toUpperCase() + "</em> </a></li>");
                        }
                        out.println("</ul> </li>");
                    }
                }
%>

    </ul>
</div>
<%--    v<!-- .end timeline -->--%>


    <div class="event-modal">
        <header class="header">
            <div class="content">
                <span class="event-date"></span>
                <h3 class="event-name"></h3>
            </div>

            <div class="header-bg"></div>
        </header>

        <div class="body">
            <div class="event-info"></div>
            <div class="body-bg"></div>
            <a href="">Fiche d'appel</a>
        </div>

        <a href="#0" class="close">Close</a>
    </div>

    <div class="cover-layer"></div>
<%--</div> <!-- .cd-schedule -->--%>

    <script src="https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-1b93190375e9ccc259df3a57c1abc0e64599724ae30d7ea4c6877eb615f89387.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js'></script>
    <script src="${pageContext.request.contextPath}/js/planning1.js"></script>
</body>
</html>