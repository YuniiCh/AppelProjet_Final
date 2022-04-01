<%@ page import="java.util.List" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.appelprojet.util.Role" %>
<%@ page import="com.example.appelprojet.util.EtatPresence" %>
<%@ page import="com.example.appelprojet.mertier.Presence" %>
<%@ page import="com.example.appelprojet.dao.PresenceDAO" %>
<%@ page import="com.example.appelprojet.mertier.Utilisateur" %>
<%@ page import="com.example.appelprojet.dao.UtilisateurDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Fiche Appel</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/css/appelStyles.css" rel="stylesheet" type="text/css">
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
        <h5><b><i class="fa fa-dashboard"></i> Fiche Appel</b></h5>
    </header>

    <!-- FicheAppel -->
    <div>
        <!-- Top container -->
        <div class="wrap">
            <div class="center">
                <div id="div1" >
                    <img src="images/logo.png" class="imgAppel" alt="Logo"/>
                </div>
                <div id="div2">
                    <%
                        if (session.getAttribute("utilisateur") == null || session.getAttribute("utilisateur").toString().isEmpty()){
                            request.getRequestDispatcher("login").forward(request,response);
                        }
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                        Seance appel = (Seance) request.getAttribute("seance");
                        System.out.println("appel" + appel);
                        session.setAttribute("seance", appel);
                        String disable = "disabled=\"disabled\"";
                        if (!appel.getEtatAppel().equals("valide")){
                            disable = "";
                        }
                    %>
                    <h5 class="h5Appel"><%=appel.getCours().getTypeCours().toUpperCase() %></h5>
                    <p><%=appel.getCours().getNomCours().toUpperCase() %></p>
                    <p id="id_seance">Seance N°<%=appel.getIdSeance()%>  </p>
                    <p><%=simpleDateFormat.format(appel.getDateDebut())%> - <%=simpleDateFormat.format(appel.getDateFin()).split(" ")[1].substring(0,5)%></p>
                    <p><%=appel.getSalle().getNomSalle()%></p>
                </div>
            </div>

            <!-- Liste étudiants -->
            <form action="" method="post" class="center">
                <br>
                <div class="divButton">
                    <button id="btn_touspresence" type="button" class="buttonTop" <%=disable%>><span id="toustext">Tous présents</span></button>

                    <!-- Open Pop-Up -->
                    <button id="btn_add" type="button" class="buttonTop"  <%=disable%>>Ajouter</button>
                    <!-- Pop -->
                    <div id="add_student_pop" class="add_student_pop">
                        <!-- Pop Content-->
                        <div class="add_student_content">
                            <div class="pop-header">
                                <span class="close">&times;</span>
                                <h2>Ajouter Etudiant</h2>
                            </div>
                            <div class="pop-body">
                                <p>Ajouter un étudiant:<label for="search_student"><input id="search_student" type="text" placeholder="nom prénom identifiant" autofocus /></label><span id="delete_search">&times;</span><span id="addmsg"></span></p>
                                <div id="zone_show" class="zone_show_cl"></div>
                                <div id="zone_students_chosen" class="zone_students_chosen_cl"><table id="tb_choose"><tr><th>ID</th><th>Formation</th><th>Nom Prénom</th></tr></table></div>
                            </div>

                        </div>
                    </div>
                    <!-- POP END-->

                    <button id="btn_delete" type="button" class="buttonTop"  <%=disable%>>Supprimer</button>
                </div>

                <br>
                <br>
                <br>
                <div class="divTable">
                    <table class="listEtudiant" id="listEtudiant">
                        <thead>
                        <tr>
                            <th scope="col">Photo</th>
                            <th scope="col">Nom et prénom</th>
                            <th scope="col">Etat</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Presence> presences = PresenceDAO.findPresenceByIdSeance(appel.getIdSeance());
                            int i = 0;
                            String formation = "FI";
                            String unitDisable =  "disabled=\"disabled\"";
                            for (Presence p:presences) {
                                if ( !p.getEtudiant().getTypeEtudiant().toString().equals(formation)){
                                    formation = "FA";
                                }
                                if (p.getEtatPresence() != EtatPresence.ABSENCE_SIGNALE || appel.getEtatAppel().equals( "valide")){
                                    unitDisable = "";
                                }
                                String etat = "Présent";
                                String btnStyle = "style=\"background-color: #a8e2f8;\"";
                                if(p.getEtatPresence() == EtatPresence.RETART) {
                                    etat = "Retard";
                                    btnStyle = "style=\"background-color: #ffff66;\"";
                                }else if(p.getEtatPresence() == EtatPresence.ABSENCE_SIGNALE) {
                                    etat = "Signaler";
                                    btnStyle = "style=\"background-color: #ffcc99;\"";
                                }else if(p.getEtatPresence() == EtatPresence.PRESENCE){
                                    etat = "Présent";
                                }else {etat = "Absent";
                                    btnStyle = "style=\"background-color: #ffcc99;\"";
                                }
                        %>
                        <tr>
                            <td><img src="https://github.com/PikaMeoow/Photo-Etudiant/blob/main/<%=p.getEtudiant().getIdU()%>.png?raw=true" class="imgAppel" style="height: 4.5rem; width: 4.5rem;" alt="photos"/>
                                <%--                    <img src="image/etudiant.png" style="height: 4.5rem; width: 4.5rem;" />--%>
                            </td>
                            <td class="student_info"><a href="presenceEtudiantCtrl?idEtudiant=<%=p.getEtudiant().getIdU()%>"><span class="formation_color" style="<% if(formation.equals("FI")) out.println("background-color: mediumpurple;"); else out.println("background-color: mediumslateblue;");%>"
                            ><%=formation%> </span>
                                <span class="id_student"><%=p.getEtudiant().getNomU()%>  <%=p.getEtudiant().getPrenomU()%></span></a>
                            </td>
                            <td><button id="btn_etatP<%=i%>" class="btn_etatp_cl" type="button" name="<%=p.getEtudiant().getIdU()%>" onclick="getNbClick(this);"  <%=btnStyle%> <%=disable%> <%=unitDisable%>><span id="etatPresent<%=i%>" class="etatpresent_cl"><%=etat%></span></button></td>
                            <td><span class="btn_delet_one" id="delet_<%=p.getEtudiant().getIdU()%>" style="pointer-events: none; display: none; ">&circleddash;</span></td>
                        </tr>
                        <%
                                i++; }
                        %>
                        </tbody>
                    </table>
                </div>
                <br>
                <div id="div3">
                    <button type="button" name="save" value="save" id="save"  <%=disable%>>Enregistrer</button><span id="saveEtat"></span>
                    <button type="button" name="submit" value="valider" id="valider" <%=disable%>>Valider</button><span id="submitEtat"></span>
                </div>
            </form>
            <a href="planning">Planning</a>
        </div>

        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
                integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
                crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
                integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
                crossorigin="anonymous"></script>
        <script type="text/JavaScript" src="js/appel.js"></script>
    </div>
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
