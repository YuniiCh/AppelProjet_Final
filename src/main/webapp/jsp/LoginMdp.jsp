
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="css/jsp.css" type="text/css" />
  <title>Login avec mot de passe</title>
</head>
<body id="connectpage">

<h1>login</h1>

<form action="loginMdpCtrl" methode="GET">
  <label for="idU">User ID</label> <input class="pseudo" type="text" name="psd" value=""/><br>
  <label for="psw">Password</label> <input class="psw" type="text" name="psw" value=""/><br>

  <input class="sb" type="submit" value="Connexion"/>

</form>

<!-- 登录失败信息显示 -->
<%
  String avert = (String)request.getAttribute("avert");
  if(avert != null)
    out.println("<div class=\"avertissement\">" + avert + "</div>");
%>


</body>
</html>
