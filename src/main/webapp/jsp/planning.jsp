<%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/22
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.example.appelprojet.mertier.Planning" %>
<%@ page import="java.util.Date" %>
<style>
    <%@include file="/css/planning.css"%>
</style>
<html>
<head>
    <title>Download File</title>
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
            <li><span>08:00</span></li>
            <%
                int nbDistances = (int) ((20 - 8) / 0.5) + 1;
                int heure = 8;
                for (int i = 0; i<=nbDistances; i++){
                    String minute = "00";
                    if(i % 2 == 0){
                        minute = "30";
                    }else{
                        heure ++;
                    }
                    String strHeure = Integer.toString(heure);
                    if(strHeure.length()==1){
                        strHeure = "0" + strHeure;
                    }
                    out.println("<li><span>" + strHeure + ":" + minute + " </span></li>");
                }
            %>
        </ul>
    </div> <!-- .timeline -->

    <div class="events">
        <ul class="wrap">
            <c:forEach var="seances" items="${requestScope.seancesFilter}">
                <li class="events-group">
                    <div class="top-info">
                        <span>${Planning.getWeekDayInStr(Planning.getDayInMillis(seances.key))}</span>
                        <small>${Planning.getLongtoString(seances.key, "dd/MM")}</small>
                    </div>
                    <ul>
                        <c:forEach var="seance" items="${seances.value}">
                            <li class="single-event" data-start="${Planning.getDateWithFormat(seance.dateDebut, "HH:mm")}" data-end="${Planning.getDateWithFormat(seance.dateFin, "HH:mm")}" data-content="event-rowing-workout" data-event="event-2">
                                <a href="${pageContext.request.contextPath}/appel?id=${seance.idSeance}" >
                                    <em class="event-name">${seance.cour.nomCours}</em>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>

<%--            <li class="events-group">--%>
<%--                <div class="top-info"><span>Tuesday</span></div>--%>

<%--                <ul>--%>
<%--                    <li class="single-event" data-start="10:00" data-end="11:00"  data-content="event-rowing-workout" data-event="event-2">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Rowing Workout</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="11:30" data-end="13:00"  data-content="event-restorative-yoga" data-event="event-4">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Restorative Yoga</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="13:30" data-end="15:00" data-content="event-abs-circuit" data-event="event-1">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Abs Circuit</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="15:45" data-end="16:45"  data-content="event-yoga-1" data-event="event-3">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Yoga Level 1</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </li>--%>

<%--            <li class="events-group">--%>
<%--                <div class="top-info"><span>Wednesday</span></div>--%>

<%--                <ul>--%>
<%--                    <li class="single-event" data-start="09:00" data-end="10:15" data-content="event-restorative-yoga" data-event="event-4">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Restorative Yoga</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="10:45" data-end="11:45" data-content="event-yoga-1" data-event="event-3">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Yoga Level 1</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="12:00" data-end="13:45"  data-content="event-rowing-workout" data-event="event-2">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Rowing Workout</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="13:45" data-end="15:00" data-content="event-yoga-1" data-event="event-3">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Yoga Level 1</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </li>--%>

<%--            <li class="events-group">--%>
<%--                <div class="top-info"><span>Thursday</span></div>--%>

<%--                <ul>--%>
<%--                    <li class="single-event" data-start="09:30" data-end="10:30" data-content="event-abs-circuit" data-event="event-1">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Abs Circuit</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="12:00" data-end="13:45" data-content="event-restorative-yoga" data-event="event-4">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Restorative Yoga</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="15:30" data-end="16:30" data-content="event-abs-circuit" data-event="event-1">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Abs Circuit</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="17:00" data-end="18:30"  data-content="event-rowing-workout" data-event="event-2">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Rowing Workout</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </li>--%>

<%--            <li class="events-group">--%>
<%--                <div class="top-info"><span>Friday</span></div>--%>

<%--                <ul>--%>
<%--                    <li class="single-event" data-start="10:00" data-end="11:00"  data-content="event-rowing-workout" data-event="event-2">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Rowing Workout</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="12:30" data-end="14:00" data-content="event-abs-circuit" data-event="event-1">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Abs Circuit</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="15:45" data-end="16:45"  data-content="event-yoga-1" data-event="event-3">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Yoga Level 1</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </li>--%>
<%--            <!--        -->--%>
<%--            <li class="events-group">--%>
<%--                <div class="top-info"><span>Saturday</span></div>--%>
<%--                <ul>--%>
<%--                    <li class="single-event" data-start="09:30" data-end="10:30" data-content="event-abs-circuit" data-event="event-1">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Abs Circuit</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="11:00" data-end="12:30" data-content="event-rowing-workout" data-event="event-2">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Rowing Workout</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>

<%--                    <li class="single-event" data-start="14:00" data-end="15:15"  data-content="event-yoga-1" data-event="event-3">--%>
<%--                        <a href="#0">--%>
<%--                            <em class="event-name">Yoga Level 1</em>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </ul>--%>
<%--            </li>--%>
            <!--        -->
            <!--        -->
            <!--        -->
        </ul>
    </div>

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
</div> <!-- .cd-schedule -->


<script src="https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-1b93190375e9ccc259df3a57c1abc0e64599724ae30d7ea4c6877eb615f89387.js"></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js'></script>
<script src="/js/planning.js"></script>
