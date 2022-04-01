<%--
  Created by IntelliJ IDEA.
  User: zhaom
  Date: 01/04/2022
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/styles.css">
    <title>Se Connecter</title>
</head>
<body>
<div class="wrap">
    <form class="form" method="post" action="loginCtrl">


        <%--        <div class="left"><video src="" muted loop autoplay></video></div>--%>
        <div class="right">
            <img src="images/logo.png" alt="logo">
            <h1>Bienvenue !</h1>
            <label for="email"><h3>Email</h3></label>
            <input id="email" class="text" type="email" name="email"/>
            <label for="psw"><h3>Password</h3></label>
            <input id="psw" class="text" type="password" name="psw">
            <button id="loginsb" class="btn" type="submit">Se connecter</button>
            <p>
                <% ///<!-- Fail to login -->
                    String avert = (String)request.getAttribute("avert");
                    if(avert != null) {
                        out.println(avert);
                    }
                %>
            </p>
        </div>
    </form>

</div>
</body>
</html>
