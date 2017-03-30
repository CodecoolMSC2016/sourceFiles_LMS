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
    <div id="content1">
        <div id="popup">
            <form action="ProfileHandler" method="post">
                <input id="name" name="changedName" placeholder="Name" type="text">
                <button class="btn btn-success" type="submit" onclick="closePopup()">Update</button><br>
                <p>change your role? <input type="checkbox" id="changerole" name = "changeRole" value = "true"></p>
            </form>
        </div>
    </div>
    <button id="anyád" onclick="openPopup()">Update information</button>
    <script>
        function openPopup() {
            document.getElementById("content1").style.display = "block";
        }
        function closePopup() {
            document.getElementById("content1").style.display = "none";
        }
    </script>
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

