<%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/22
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download File</title>
</head>
<body>
<a href="../download/1.jpg">img</a>
<a href="../download/1.text">text</a>
<a href="../download/1.rar">rar</a>
<br>
<a href="../download/1.text" download="">text</a>
<a href="../download/1.rar" download="">rar</a>
<a href=<%="http://localhost:8080/AppelProjet/upload/"+"css.png"%> download>img</a>
<a href=<%="http://localhost:8080/AppelProjet/upload/"+"English.docx"%> download>text</a>
<a href=<%="http://localhost:8080/AppelProjet/upload/"+"DeploiementSiteWeb.pdf"%> download>pdf</a>
<a href=<%="http://localhost:8080/AppelProjet/upload/"+"MapReduce_correction.zip"%> download>zip</a>
</body>
</html>
