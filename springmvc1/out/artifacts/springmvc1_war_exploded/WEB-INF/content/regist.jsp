<%--
  Created by IntelliJ IDEA.
  User: hh
  Date: 2020/4/18
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

<%--     表单提交时会发送到 action 对应的url--%>
    <form action="${pageContext.request.contextPath}/user/regist" method="post">
        用户名： <input type="text" name="username"/>
        密码：   <input type="text" name="password"/>
        <input type="submit" name="注册"/>
    </form>

</body>
</html>
