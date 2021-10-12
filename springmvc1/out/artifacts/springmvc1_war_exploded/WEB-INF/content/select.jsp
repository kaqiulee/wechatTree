<%--
  Created by IntelliJ IDEA.
  User: 战神
  Date: 2021/4/14
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/tree/select" method="post">
        ID： <input type="text" name="ID"/>
        <input type="submit" name="查找"/>
</form>
</body>
</html>
