<%--
  Created by IntelliJ IDEA.
  User: CYN
  Date: 2022/3/22
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" type="text/css">
    <title>Upload File</title>
</head>
<body>
<form method="POST" action="uploadCtrl" enctype="multipart/form-data" id="pageDeposerJ">    <p>Justificatif: <input type="text" name="nameJ"></p>
    <p>Date: <label>
    <input type="date" name="datefile">
</label></p>
    <p>Séance: <label>
    <select name="selectseance">
        <option> 1 </option>
        <option> 2 </option>
    </select>
</label>
  </p>
    <p>Choose a file: <input type="file" name="multiPartServlet" /></p >
    <button>Submit</button>
</form>
</body>
</html>
