<%@ page import="com.example.appelprojet.dao.JustificatifDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appelprojet.mertier.Justificatif" %>
<%@ page import="com.example.appelprojet.util.EtatValidation" %>
<%@ page import="com.example.appelprojet.mertier.Justifier" %>
<%@ page import="com.example.appelprojet.dao.JustifierDAO" %>
<%@ page import="org.apache.commons.collections.map.HashedMap" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="com.example.appelprojet.dao.SeanceDAO" %>
<%@ page import="com.example.appelprojet.mertier.Planning" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="com.example.appelprojet.dao.UtilisateurDAO" %>
<%@ page import="com.example.appelprojet.util.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.appelprojet.mertier.Utilisateur" %>

<html>
<head>
    <title>Valider justificatif</title>
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
            <a href="download" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bullseye fa-fw"></i>&nbsp; Valider les justificatifs d'absence</a>
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
        <h5><b><i class="fa fa-dashboard"></i> Validation justificatifs </b></h5>
    </header>
    <!-- Ajouter la nouvel page ici - le contenue dans la balise Body -->
    <div>
<div id="downloadlist">
    <form method="POST"  id="listConsultJ">
        <%
            Utilisateur utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
            Long idS = utilisateur.getIdU();
            List<Justifier> justifiers = JustifierDAO.lesJustifPasEncoreTraites(idS);

            if (!justifiers.isEmpty()){

                out.print("<table border=\"1\">");
                String disabled = "";
                out.print("<tr><th>Choix</th><th>Name Etudiant</th><th>Justificatif</th><th>Date Dépôt</th><th>Nom Cours</th><th>Num Scéance</th></tr>");
                StringBuilder res = new StringBuilder();

                for (int i = 0; i < justifiers.size(); i++) {

                    Justificatif justificatif = justifiers.get(i).getJustificatif();
                    Seance seance = justifiers.get(i).getSeance();
                    disabled = justificatif.getEtatValidation().equals(EtatValidation.NULL)? "disabled" : "";

                    res.append(String.format(
                            "<tr><td>" +
                                    "<input type='checkbox' %1$s name='idJSelect' value='%2$s'/></td>" +
                                    "<td>%3$s %4$s</td>" +
                                    "<td><a href='%5$s' download>%6$s</a></td>" +
                                    "<td>%7$s</td><td>%8$s</td><td>%9$s<input type='text' name='idSSelect' value='%10$s' style='display:none;' /></td></tr>",
                            disabled,
                            justificatif.getIdJ(),
                            justificatif.getEtudiant().getNomU(),
                            justificatif.getEtudiant().getPrenomU(),
                            "./upload/"+justificatif.getLinkFile(),
                            justificatif.getNameFile(),
                            justificatif.getDateDepot(),
                            seance.getCours().getNomCours(),
                            SeanceDAO.getNumSeances(seance.getIdSeance()),
                            seance.getIdSeance()
                    ));
                }
                out.print(res.toString());
                out.print("</table>");
                out.print("<input type=\"submit\" value=\"Accepter\" formaction=\"downloadCtrl?avis=accept\"> " +
                        "<input type=\"submit\" value=\"Refuser\" formaction =\"downloadCtrl?avis=refuser\">");
            }else {out.print("<p>Pas de Justificatifs reçu pour l'instant.</p>");}
        %>
    </form>
</div>
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
</body>
</html>
