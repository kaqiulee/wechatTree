<%--
  Created by IntelliJ IDEA.
  User: hh
  Date: 2020/4/20
  Time: 6:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登陆</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
    <p> regist success !!! please login !!!</p>

    <form action="${pageContext.request.contextPath}/login" method="post">
        用户名： <input type="text" name="username"/>
        密码：   <input type="text" name="password"/>
        <input type="submit" name="登陆"/>
    </form>
</body>
</html>
