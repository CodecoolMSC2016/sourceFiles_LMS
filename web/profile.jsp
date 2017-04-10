<%--
  Created by IntelliJ IDEA.
  User: akos
  Date: 2017.03.30.
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>User profile</title>
    <link rel="stylesheet" href="style/bootstrap.min.css"/>
    <link rel="stylesheet" href="style/profilestyle.css"/>
</head>
<body>
    <div id="title">
        <div id="text">source learn</div>
    </div>
<div id="content">
    <div id="content1">
        <div id="popup">
            <form class="register" action="ProfileHandler" method="post">
                <input type="text" name="confirmEmail"  class="form-control" placeholder="E-mail address for confirmation" required><br>
                <input id="name" name="changedName" class="form-control" placeholder="Name" type="text"><br>
                <p id="pp">Would you change your role? <input type="checkbox" id="changerole" name = "changeRole" value = "true"></p><br>
                <button class="btn btn-success" type="submit" onclick="closePopup()">Update</button>
            </form>
        </div>
    </div>
    <div id="userphoto">
        <img src="images/avatar.png" alt="default avatar">
    </div>
    <h1>
        <h1::before></h1::before>
        ${name}'s profile
        <h1::after></h1::after>
    </h1>
        <section id="settings">
        <p class="setting">Email address:
        ${email}</p>
        <p class="setting">Name: <img src="images/edit.png" alt="*Edit*" onclick="openPopup()">
        ${name}</p>
        <p class="setting">Role: <img src="images/edit.png" alt="*Edit*" onclick="openPopup()">
        ${role}</p>
        </section>
    <a href="./login.jsp">Logout</a>
</div>
    <script>
        function openPopup() {
            document.getElementById("content1").style.display = "block";
        }
        function closePopup() {
            document.getElementById("content1").style.display = "none";
        }
    </script>
<%
    String name;
    String email;
    String role;

    if(session == null && request.getCookies() == null) {
        response.sendRedirect("login.jsp");
    }
    else {
        name = (String)session.getAttribute("name");
        email = (String)session.getAttribute("email");
        role = (String)session.getAttribute("role");
    }
%>
</body>
</html>
