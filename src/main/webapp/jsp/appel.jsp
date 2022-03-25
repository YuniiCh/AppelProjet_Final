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
    <link href="${pageContext.request.contextPath}/css/appelStyles.css" rel="stylesheet" type="text/css">
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
            SeanceDAO seanceDAO = new SeanceDAO();
            Seance appel = seanceDAO.infoFicheAppelById((long) request.getAttribute("ficheAppel"));
        %>
        <h5><%=appel.getCours().getTypeCours() %></h5>
        <p><%=appel.getCours().getNomCours() %></p>
        <p>Seance N°<%=appel.getIdSeance()%>  </p>
        <p><%=simpleDateFormat.format(appel.getDateDebut())%> - <%=appel.getDateFin().getHours()%>:<%=appel.getDateFin().getMinutes()%></p>
        <p><%=appel.getSalle().getNomSalle()%></p>
<%--        <h5 style="margin: 0">Master 2 MIAGE IPM</h5>--%>
<%--        <p style="margin: 0">CM Management Agile</p>--%>
<%--        <p style="margin: 0">Seance N°1</p>--%>
<%--        <p style="margin: 0"> MC405</p>--%>
        </div>

    </div>

<!-- Liste étudiants -->
<form action="/mertier/seance" method="post" class="center">
    <br>
    <div class="divButton">
        <button type="button" class="buttonTop">Tous présents</button>
        <button type="button" class="buttonTop">Ajouter</button>
        <button type="button" class="buttonTop">Supprimer</button>
    </div>
    <br>
    <br>
    <br>
    <div class="divTable">
        <table class="listEtudiant">
            <thead>
            <tr>
                <th scope="col">Photo</th>
                <th scope="col">Nom et prénom</th>
                <th scope="col">Etat</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Etudiant> etudiants = SeanceDAO.findEtudiansByID(appel);
                int i = 0;
                for (Etudiant e:
                        etudiants) {
            %>
            <tr>
                <td><img src="https://github.com/PikaMeoow/Photo-Etudiant/blob/main/<%=e.getIdU()%>.png?raw=true"  alt="images"/>
<%--                    <img src="image/etudiant.png" style="height: 4.5rem; width: 4.5rem;" />--%>
                </td>
                <td><%=e.getNomU()%>  <%=e.getPrenomU()%></td>
                <td><button id="bt_etatP<%=i%>" class="bt_etatp_cl" type="button" name="etatPresent" onclick="getNbClick(this);"><span id="etatPresent<%=i%>" class="etatpresent_cl">Présent</span></button></td>
            </tr>
            <%
                    i++; }
            %>
            </tbody>
        </table>
    </div>
    <br>
    <div id="div3">
        <button type="submit" name="submit" value="Valider" id="valider">Valider</button>
    </div>
</form>
<a href="planning">Planning</a>
</div>

<script type="text/JavaScript" src="js/fctXML.js"></script>
</body>
</html>

