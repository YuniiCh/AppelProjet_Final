<%@ page import="java.util.List" %>
<%@ page import="com.example.appelprojet.mertier.Seance" %>
<%@ page import="com.example.appelprojet.dao.SeanceDAO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.appelprojet.mertier.Etudiant" %><%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/23
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Fiche Appel</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="d-sm-flex justify-content-between align-items-center mb-4">--%>
<%--    <h5>${requestScope.Formation.getNomFormation()} </h5>--%>
<%--    <h5>${requestScope.Cours.getNomCours} </h5>--%>
<%--    <h5>Seance N° ${requestScope.Seance.getIdSeance} </h5>--%>
<%--    <h5>${requestScope.Salle.getNomSalle()}</h5>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Fiche Appel</title>
    <link href="../css/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<!-- Top container -->
<div>
    <div id="div1" style="float: left">
        <img src="image/logo.png"  style="height: 4.5rem; width: 4.5rem;" />
    </div>
    <div id="div2">
        <%
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            SeanceDAO seanceDAO = new SeanceDAO();
            Seance appel = seanceDAO.infoFicheAppelById((long) request.getAttribute("ficheAppel"));
        %>
        <h5 style="margin: 0"><%=appel.getCours().getTypeCours() %></h5>
        <p style="margin: 0"><%=appel.getCours().getTypeCours() %></p>
        <p style="margin: 0">Seance N°<%=appel.getIdSeance()%>  </p>
        <p style="margin: 0"><%=simpleDateFormat.format(appel.getDateDebut())%> - <%=appel.getDateFin().getHours()%>:<%=appel.getDateFin().getMinutes()%></p>
        <p style="margin: 0"><%=appel.getSalle().getNomSalle()%></p>
<%--        <h5 style="margin: 0">Master 2 MIAGE IPM</h5>--%>
<%--        <p style="margin: 0">CM Management Agile</p>--%>
<%--        <p style="margin: 0">Seance N°1</p>--%>
<%--        <p style="margin: 0"> MC405</p>--%>
    </div>
</div>

<!-- Liste étudiants -->
<form action="/mertier/seance" method="post">
    <br>
    <div>
        <button type="button">Tous présents</button>
        <button type="button">Tous absents</button>
        <button type="button">+</button>
        <button type="button">-</button>
    </div>
    <br>
    <div>
        <table style="border: 1px dotted black">
            <thead>
            <tr>

            </tr>
            </thead>
            <tbody>
            <%
                List<Etudiant> etudiants = seanceDAO.findEtudiansByID(appel);
                int i = 0;
                for (Etudiant e:
                        etudiants) {
            %>
            <tr>
                <td><img src="image/etudiant.png" style="height: 4.5rem; width: 4.5rem;" /></td>
                <td><% e.getNomU(); e.getPrenomU(); %></td>
                <td><button id="bt_etatP<%=i%>" class="bt_etatp_cl" type="button" name="etatPresent" onclick="getNbClick"><span id="etatPresent<%=i%>" class="etatpresent_cl">Présent</span></button></td>
            </tr>
            <%
                    i++; }
            %>
            </tbody>
        </table>
    </div>
    <br>
    <div style="margin-left: 20px;">
        <button type="submit" name="submit" value="Valider" style="width: 200px;">Valider</button>
    </div>
</form>
<a href="planning">Hello</a>

<script type="text/JavaScript" src="js/fctXML.js"></script>
</body>
</html>

