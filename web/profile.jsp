<%--
  Created by IntelliJ IDEA.
  User: akos
  Date: 2017.03.30.
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User profile</title>
</head>
<body>
<div>
    <h1>${name} 's profile</h1>
</div>
<div>
    <h2>Profile details:</h2>
    <div>
        <p>${email}</p>
        <p>${name}</p>
        <p>${role}</p>
    </div>
    <a href="/login.jsp">Logout</a>
</div>
</body>
</html>
