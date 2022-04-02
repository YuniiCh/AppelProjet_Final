<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.example.appelprojet.mertier.Planning" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="com.example.appelprojet.dao.UtilisateurDAO" %>
<%@ page import="com.example.appelprojet.util.Role" %>
<%@ page import="com.example.appelprojet.mertier.Utilisateur" %>

<html>
<head>
<title>Planning</title>
<meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/css/planning.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
        .w3-hover-black:hover {
            background-color: #e30909;}
    </style>
</head>
<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-large" style="z-index:4;  color: #ebf4f8; background-color: #430000;">
    <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i> &nbsp;Menu</button>
    <span class="w3-bar-item w3-right">Gestion Appel</span>
</div>
<%
    Utilisateur user = (Utilisateur) request.getSession().getAttribute("utilisateur");
    pageContext.setAttribute("typeU", UtilisateurDAO.findRoleById(user.getIdU()));
%>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
    <div class="w3-container w3-row">
        <div class="w3-col s4">
            <c:if test="${!empty typeU}">
                <img src="https://github.com/PikaMeoow/Photo-Etudiant/blob/main/<%=user.getIdU()%>.png?raw=true" class="w3-circle w3-margin-right" style="width:46px" alt="Photo">
            </c:if>
            <c:if test="${empty typeU}">
                <img src="https://github.com/PikaMeoow/Photo-Etudiant/blob/main/avatar.jpg?raw=true" class="w3-circle w3-margin-right" style="width:46px" alt="Avatar">
            </c:if>
        </div>
        <div class="w3-col s8 w3-bar">
            <c:if test="${!empty typeU}">
                <span>Bienvenue, <strong><%=user.getPrenomU()%></strong></span><br>
                <a href="profil" class="w3-bar-item w3-button"><i class="fa fa-user"></i></a>
                <a href="login" style="font-size:12px;" class="w3-bar-item w3-padding">
                    <i class="fa fa-bullseye fa-fw"></i>
                    Déconnecter
                </a>
            </c:if>
        </div>
    </div>
    <hr>
    <div class="w3-container">
        <h5>Menu</h5>
    </div>
    <div class="w3-bar-block">
        <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>&nbsp; Close Menu</a>
        <c:if test="${typeU.equals(Role.ETUDIANT)}">
            <a href="uploadFile" class="w3-bar-item w3-button w3-padding"><i class="fa fa-users fa-fw"></i>&nbsp; Upload File</a>
            <a href="presenceEtudiantCtrl" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bell fa-fw"></i>&nbsp; Consulter
                mes absences</a>
        </c:if>
        <c:if test="${typeU.equals(Role.ENSEIGNANT)}">
            <a href="planningCtrl" class="w3-bar-item w3-button w3-padding"><i class="fa fa-calendar"></i>&nbsp; Emploi du temps</a>
            <a href="statisticEnseignantCtrl" class="w3-bar-item w3-button w3-padding"><i class="fa fa-diamond fa-fw"></i>&nbsp; Consulter les statistiques</a>
            <a href="coursCtrl" class="w3-bar-item w3-button w3-padding"><i class="fa fa-diamond fa-fw"></i>&nbsp; Consulter mes cours </a>

        </c:if>
        <c:if test="${typeU.equals(Role.SCOLARITE)}">
            <a href="download" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bullseye fa-fw"></i>  Valider les justificatifs d'absence</a>
            <a href="saisirNouvSeance" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bullseye fa-fw"></i>&nbsp; Saisir nouveaux seances</a>

        </c:if>
    </div>
</nav>


<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">
    <!-- Header -->
    <header class="w3-container" style="padding-top:22px">
        <h5><b><i class="fa fa-dashboard"></i> Planning</b></h5>
    </header>
    <!-- Ajouter la nouvel page ici - le contenue dans la balise Body -->
    <div>

<%
    String date = Planning.getDateWithFormat(new Date(), "yyyy-MM-dd");
    if (request.getAttribute("date") != null) {
        date = (String) request.getAttribute("date");
    }
%>

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
    </div> <!-- .timeline -->

    <div class="events">
        <ul class="wrap">
            <c:forEach var="seances" items="${requestScope.seances}">
                <li class="events-group">
                    <div class="top-info">
                        <span>${Planning.getNumToStr(Planning.getStrToDate(seances.key).time)}</span>
                        <small>${seances.key}</small>
                    </div>
                    <ul>
                        <c:forEach var="seance" items="${seances.value}">
                            <li class="single-event" data-start="${Planning.getDateWithFormat(seance.dateDebut, "HH:mm")}" data-end="${Planning.getDateWithFormat(seance.dateFin, "HH:mm")}" data-content="event-rowing-workout" data-event="event-2">
                                <a href="planningCtrl?idSeance=${seance.idSeance}" >
                                    <em class="event-name">${seance.cours.nomCours}</em>
                                    <em class="event-name">${seance.cours.formation.nomFormation}</em>
                                    <em class="event-name">${seance.salle.nomSalle}</em>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
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

    </div>
    <!-- Fin de la page contenue -->

    <!-- Script Sidebar -->
    <script>
        // Get the Sidebar
        var mySidebar = document.getElementById("mySidebar");

        // Get the DIV with overlay effect
        var overlayBg = document.getElementById("myOverlay");

        // Toggle between showing and hiding the sidebar, and add overlay effect
        function w3_open() {
            if (mySidebar.style.display === 'block') {
                mySidebar.style.display = 'none';
                overlayBg.style.display = "none";
            } else {
                mySidebar.style.display = 'block';
                overlayBg.style.display = "block";
            }
        }

        // Close the sidebar with the close button
        function w3_close() {
            mySidebar.style.display = "none";
            overlayBg.style.display = "none";
        }
    </script>
    <script src="https://cpwebassets.codepen.io/assets/common/stopExecutionOnTimeout-1b93190375e9ccc259df3a57c1abc0e64599724ae30d7ea4c6877eb615f89387.js"></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js'></script>
    <script src='https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js'></script>
    <script src="${pageContext.request.contextPath}/js/planning1.js"></script>

</div>
</body>
</html>