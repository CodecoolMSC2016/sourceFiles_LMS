<%--
  Created by IntelliJ IDEA.
  User: imre_meszesan
  Date: 11.04.17
  Time: 08:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registered Users</title>
</head>
<body>
<pre>${registeredUsers}</pre>
<a href="profile.jsp">Profile Page</a>
<%
    if(session == null || session.getAttribute("userName") == null){
        response.sendRedirect("/login.jsp");
    }
%>
</body>
</html>
