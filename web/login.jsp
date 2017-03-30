<%--
  Created by IntelliJ IDEA.
  User: akos
  Date: 2017.03.29.
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="style/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="style/style.css" type="text/css"/>
</head>
<body>
<div class="bg">
  <div class="wrapper">
    <div class="focus">
      source learn
    </div>
    <div class="mask">
      <div class="text">source learn</div>
    </div>
  </div>

  <div class="content">
    <form action="LoginHandler" method="post">
        <br>
        <p style="color: red;">${emailNotFound}</p>
        <label for="lemail" class="sr-only">E-mail address</label>
        <input type="email" name="email" id="lemail" class="form-control" required placeholder="E-mail address"><br>

        <button class="btn btn-default" type="submit">Login</button><br>
  </form>
      <hr>
  <p>You don't have an account yet?<br />Click <a href="/register.jsp">here</a> to register.</p>
  </div>
  <div class="background-image"></div>
</div>
</body>
</html>
