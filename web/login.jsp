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
  <title>Title</title>
</head>
<body>
<div>
  <h1>SourceLearn Login page</h1>
</div>
<div>
  <p>Please log in to your account using your e-mail address and full name!</p>
  <form action="">
    <label for="lemail">E-mail address</label>
    <input type="email" name="email" id="lemail" required placeholder="E-mail address"><br>
    <label for="lname">Full name</label>
    <input type="text" name="name" id="lname" required placeholder="Full name"><br>

    <input type="submit">
  </form>
  <p>don't have an account yet? click <a href="/register.jsp">here</a> to register.</p>
</div>
</body>
</html>
