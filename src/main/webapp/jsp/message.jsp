<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet"  type="text/css"  href="Style/style.css"/>
    <title>Message</title>
</head>
<body>
<%
    String message = (String)request.getSession(false).getAttribute("message");
    out.print(message);
%>
<a href="uploadFile">Hello</a>
</body>
</html>
