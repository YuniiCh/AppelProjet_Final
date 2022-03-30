<%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/23
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="com.example.appelprojet.dao.SeanceDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.appelprojet.mertier.Etudiant" %>
<%@ page import="com.example.appelprojet.dao.EtudiantDAO" %>
<%@ page import="com.example.appelprojet.util.EtatPresence" %>
<%@ page import="com.example.appelprojet.mertier.Presence" %>
<%@ page import="com.example.appelprojet.dao.PresenceDAO" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fiche Appel</title>
    <link href="${pageContext.request.contextPath}/css/appelStyles.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<!-- Top container -->
<div class="wrap">
    <div class="center">
    <div id="div1" >
        <img src="images/logo.png" />
    </div>
    <div id="div2">
        <%
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Seance appel = (Seance) request.getAttribute("seance");
            System.out.println("appel" + appel);
            session.setAttribute("seance", appel);
            String disable = "disabled=\"disabled\"";
            if (!appel.getEtatAppel().equals("valide")){
                disable = "";
            }
        %>
        <h5><%=appel.getCours().getTypeCours().toUpperCase() %></h5>
        <p><%=appel.getCours().getNomCours().toUpperCase() %></p>
        <p id="id_seance">Seance N°<%=appel.getIdSeance()%>  </p>
        <p><%=simpleDateFormat.format(appel.getDateDebut())%> - <%=simpleDateFormat.format(appel.getDateFin()).split(" ")[1].substring(0,5)%></p>
        <p><%=appel.getSalle().getNomSalle()%></p>
<%--        <h5 style="margin: 0">Master 2 MIAGE IPM</h5>--%>
<%--        <p style="margin: 0">CM Management Agile</p>--%>
<%--        <p style="margin: 0">Seance N°1</p>--%>
<%--        <p style="margin: 0"> MC405</p>--%>
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
                    <p>Ajouter un étudiant:<label for="search_student"><input id="search_student" type="text" placeholder="nom prénom identifiant" autofocus /></label><span id="delete_search">&times;</span><span id="addmsg"> </span></p>
                    <div id="zone_show" class="zone_show_cl"></div>
                    <div id="zone_students_chosen" class="zone_students_chosen_cl"></div>
                </div>
                <div class="pop-footer">
                    <h3>***************************</h3>
                </div>
            </div>
        </div>

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
                List<Presence> presences = PresenceDAO.findPresenceByIdSeance(appel);
                int i = 0;
                String formation = "FI";
                String unitDisable =  "disabled=\"disabled\"";
                for (Presence p:presences) {
                    if (!p.getEtudiant().getFormation().getNomFormation().toUpperCase().contains(formation)){
                        formation = "FA";
                    }
                    if (p.getEtatPresence() != EtatPresence.ABSENCE || appel.getEtatAppel().equals( "valide")){
                        unitDisable = "";
                    }
            %>
            <tr>
                <td><img src="https://github.com/PikaMeoow/Photo-Etudiant/blob/main/<%=p.getEtudiant().getIdU()%>.png?raw=true"  alt="images"/>
<%--                    <img src="image/etudiant.png" style="height: 4.5rem; width: 4.5rem;" />--%>
                </td>
                <td class="student_info"><span class="formation_color" style="<% if(formation.equals("FI")) out.println("background-color: mediumpurple;"); else out.println("background-color: mediumslateblue;");%>"
                ><%=formation%> </span>
                    <span class="id_student"><%=p.getEtudiant().getNomU()%>  <%=p.getEtudiant().getPrenomU()%></span>
                </td>
                <td><button id="btn_etatP<%=i%>" class="btn_etatp_cl" type="button" name="<%=p.getEtudiant().getIdU()%>" onclick="getNbClick(this);"  <%=disable%> <%=unitDisable%>>
                    <span id="etatPresent<%=i%>" class="etatpresent_cl">
                    <%
                    if(p.getEtatPresence() == EtatPresence.RETART) {
                        out.println("Retard");
                    }else if(p.getEtatPresence() == EtatPresence.ABSENCE_SIGNALE) {

                        out.println("Signaler");
                    }else if(p.getEtatPresence() == EtatPresence.PRESENCE){
                            out.println("Pésent");
                    }else {
                        out.println("Absent");
                    }
                %>
                    </span></button></td>
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
        <button type="button" name="submit" value="valide" id="valider"  <%=disable%>>Valider</button><span id="valideEtat"></span>
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
</body>
</html>

