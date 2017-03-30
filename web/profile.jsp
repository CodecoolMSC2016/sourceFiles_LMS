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
    <link rel="stylesheet" href="style/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="style/profilestyle.css"/>
</head>
<body>
<div class="container">
    <h1>${name} 's profile</h1>
    <div>
        <label>Email address:</label><p>${email}</p>
        <label>Name:</label><p>${name}</p>
        <label>Role:</label><p>${role}</p>
    </div>
    <a href="/login.jsp">Logout</a>
    <a href="">Edit profile</a>
</div>

</body>
</html>

