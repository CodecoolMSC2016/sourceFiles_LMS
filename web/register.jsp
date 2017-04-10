<%--
  Created by IntelliJ IDEA.
  User: trixi
  Date: 2017.03.30.
  Time: 10:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" href="style/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="style/registerstyle.css" type="text/css"/>
</head>
<body>
<div class="container">
    <form class="register" action="RegistrationHandler" method="post">
        <h3 class="register-heading">Registration page</h3><br>
        <label for="inputEmail" class="sr-only">Email address</label>
        <p style="color:red; font-size: 10px;">${wrongEmail}</p>
        <input type="email" id="inputEmail" class="form-control" name="email" placeholder="Email address" required autofocus><br>

        <label for="inputName" class="sr-only">Name</label>
        <input type="text" id="inputName" class="form-control" name="name" placeholder="Name" required><br>

        <input type="radio" name="role" value="mentor">Mentor
        <input type="radio" name="role" value="student" checked>Student<br><br>

        <button class="btn btn-success" type="submit">Register</button><br>
    </form>
    <p>Already have an account? click <a href="./login.jsp">here</a></p>
</div> <!-- /container -->
</body>
</html>