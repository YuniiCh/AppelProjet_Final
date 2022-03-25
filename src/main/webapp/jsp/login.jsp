<%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/23
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/jsp.css" rel="stylesheet" type="text/css"/>
    <title>Login</title>
</head>
<body id="connectpage" >
<div class="limiter">
<div class="container-login100">
    <div class="wrap-login100 p-t-85 p-b-20">
        <form class="loginform" action="loginCtrl" method="post">
					<span class="login100-form-title p-b-70">
						Bienvenue !
					</span>
            <span class="login100-form-avatar">
						<img src="resources/images/logoUt1.png" alt="AVATAR">
            </span>
            <div class="wrap-input100 validate-input m-b-35" data-validate="Entrez votre identifiant">
                <label for="pseudo">Pseudo</label> <input id="pseudo" type="text" name="psd"/>
            </div>

            <div class="wrap-input100 validate-input m-b-50" data-validate="Entrez votre mot de passe">
                <label for="psw"> Password </label><input id="psw" type="text" name="psw"/>
            </div>
            <div id="msg_err">
                <% ///<!-- Fail to login -->
                    String avert = (String)request.getAttribute("avert");
                    if(avert != null) {
                        out.println(avert);
                    }
                %>
            </div>
            <div class="container-login100-form-btn">
                <button id="loginsb" type="submit">
                    Se connecter
                </button>
            </div>
            <ul class="login-more p-t-190">
            </ul>
        </form>
    </div>
</div>
</div>
</body>
</html>