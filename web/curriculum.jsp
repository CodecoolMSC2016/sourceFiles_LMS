<%--
  Created by IntelliJ IDEA.
  User: akos
  Date: 2017.04.11.
  Time: 11:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Curriculum overview</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <%--<script src="https://cdn.jsdelivr.net/mark.js/8.9.0/jquery.mark.min.js"></script>--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<%
    String role = (String) session.getAttribute("role");
    if (role != "mentor" && role != "student")
        response.sendRedirect("./login.jsp");
%>
    <div class="container">

        <div>
            <h1 class="jumbotron" id="mainheader">Welcome to the Curriculum overview page, ${name}.</h1>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div>
                    <h2 id="assheader">Current assignments:</h2>
                    <div id="postedContents" class="list-group"></div>
                    <p>This p tag should be replicated, it's a link to the future textpage/assignment</p>
                </div>
                <div>
                    <a href="./profile.jsp">Back to profile page</a>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    <p id="role" style="visibility: hidden;"><%=role%></p>

    </div>
<script src="scripts/assingmentLoader.js"></script>
</body>
</html>
