<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>

<html>

<head>
    <link rel="shortcut icon" href="logo.png"/>
    <title>文件上传</title>

</head>
<body>

<form method="post" action="deposerJustificatifCtrl" enctype="multipart/form-data" id="pageDeposerJ">
    <p>Name of file<input type="text" name="nameJ"></p>
    <p>Choose a file: <input type="file" name="multiPartServlet" /></p>
    <input type="submit" value="Upload" />
</form>

</body>

</html>