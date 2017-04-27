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
<%
    String role = (String) session.getAttribute("role");
    String email =(String) session.getAttribute("email");
    String name = (((String) session.getAttribute("name")).replace(":", " "));
    if (name == null){
        response.sendRedirect("login.jsp");
    }
%>

<div class="container">

    <div>
        <h1 class="jumbotron" id="mainheader"><%=name%>'s profile</h1>
    </div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div>
                <div id="sortable" class="list-group">

                    <div class="button <%=role%>">
                        <span class="title">Name: <%=name%></span>
                    </div>
                    <div class="button <%=role%>">
                        <span class="title">Role: <%=role%></span>
                    </div>
                    <div class="button <%=role%>">
                        <span class="title">Email: <%=email%></span>
                    </div>
                    <input type="button" value="Edit profile" class="btn btn-default" onclick="openPopup()">

                    <input type="button" value="User List" class="btn btn-default" onclick="loadUserList()">

                    <input type="button" value="Curriculum View" class="btn btn-default" onclick="loadCurriculum()">

                    <!--a href="curriculum.jsp">Curriculum view</a-->
                </div>

                <form action="LogoutHandler" method="GET">
                    <input type="submit" value="Logout">
                </form>

                <div id="content1">
                    <div id="popup">
                        <form class="register" action="ProfileHandler" method="post">
                            <input style="visibility: hidden" type="radio" name="confirmEmail"  class="form-control" value="${email}" checked><br>
                            <input id="name" name="changedName" class="form-control" placeholder="Name" type="text"><br>
                            <p id="pp">Would you change your role? <input type="checkbox" id="changerole" name = "changeRole" value = "true"></p><br>
                            <button class="btn btn-success" type="submit" onclick="closePopup()">Update</button>
                        </form>
                    </div>
                </div>

            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
    <p id="role" style="visibility: hidden;"><%=role%></p>

</div>
<script>
    function openPopup() {
        document.getElementById("content1").style.display = "block";
    }
    function closePopup() {
        document.getElementById("content1").style.display = "none";
    }
    function loadCurriculum() {
        window.location.assign("./curriculum.jsp");
    }
    function loadUserList() {
        window.location.assign("./userList.jsp");
    }
</script>
</body>
</html>