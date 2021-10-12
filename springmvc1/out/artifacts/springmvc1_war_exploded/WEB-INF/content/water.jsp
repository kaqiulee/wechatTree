<%--
  Created by IntelliJ IDEA.
  User: 战神
  Date: 2021/4/14
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>浇水</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form action="${pageContext.request.contextPath}/tree/water" method="post">
    id： <input type="text" name="ID"/>
    <input type="submit" name="浇水"/>
</form>
</body>
</html>
