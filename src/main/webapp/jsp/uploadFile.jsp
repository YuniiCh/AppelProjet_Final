<%@ page import="com.example.appelprojet.dao.UtilisateurDAO" %>
<%@ page import="com.example.appelprojet.mertier.Utilisateur" %>
<%@ page import="com.example.appelprojet.dao.SeanceDAO" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appelprojet.mertier.Etudiant" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jodd.madvoc.scope.SessionScope" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appelprojet.mertier.Planning" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.example.appelprojet.util.Role" %>

<html>
<head>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" type="text/css">
    <title>Dépôt justificatif</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
            <a href="presenceEtudiant" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bell fa-fw"></i>&nbsp; Consulter
                mes absences</a>
        </c:if>
        <c:if test="${typeU.equals(Role.ENSEIGNANT)}">
            <a href="planning" class="w3-bar-item w3-button w3-padding"><i class="fa fa-calendar"></i>&nbsp; Emploi du temps</a>
            <a href="statisticEnseignant" class="w3-bar-item w3-button w3-padding"><i class="fa fa-diamond fa-fw"></i>&nbsp; Consulter les statistiques</a>
            <a href="cours" class="w3-bar-item w3-button w3-padding"><i class="fa fa-diamond fa-fw"></i>&nbsp; Consulter mes cours </a>

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
        <h5><b><i class="fa fa-dashboard"></i> Dépôt justificatif</b></h5>
    </header>
    <!-- Ajouter la nouvel page ici - le contenue dans la balise Body -->
    <div>
    <%
        Long idE = user.getIdU();
        List<Seance> liste = new ArrayList<>();

        SeanceDAO seanceDAO = new SeanceDAO();
        if (idE != null)
            liste = (List) seanceDAO.tousAbsEtudiantCeMois(idE);

    %>
<div id="uploadF" >
    <form method="POST" action="uploadCtrl" enctype="multipart/form-data" id="pageDeposerJ">

        <% if(!liste.isEmpty()) {

            out.print("<table border=\"1\">");
            out.print("<tr><th>Num</th><th>Choix</th><th>Numéro de Séance</th><th>Nom de Cours</th><th>Type de Cours</th><th>Date de Début</th></tr>");

            for (int i = 0; i < liste.size(); i++) {

                out.println("<tr><td>"+(i+1)+"</td><td><input type=\"checkbox\" name=\"idSSelect\" value=" + liste.get(i).getIdSeance() + "/></td>" +
                        "<td>" + liste.get(i).getIdSeance() + "</td>"+
                        "<td>" + liste.get(i).getCours().getNomCours() + "</td>" +
                        "<td>" + liste.get(i).getDateDebut() + "</td>" +
                        "<td>" + liste.get(i).getCours().getTypeCours() + "</td></tr>");
            }
            out.print("</table>");
        }else {out.print("<p>Pas de l'absence pour l'instant.</p>");}
        %>

        <div id="infosUpload" <%=liste.isEmpty()?"hidden":""%>>
            <input type="text" hidden name="idE" value=<%=idE%>>
            <p>Name of file :</p> <input type="text" name="nameJ"/>
            <p>Choose a file: </p> <input type="file" name="multiPartServlet" />
            <p><input type="submit" value="Upload" /></p>
        </div>
    </form>
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
</div>
</div>
</body>
</html>
