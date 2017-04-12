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
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="./style/curriculumstyle.css">
<%--<script src="https://cdn.jsdelivr.net/mark.js/8.9.0/jquery.mark.min.js"></script>--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>
<%
    String role = (String) session.getAttribute("role");
    String name = (((String) session.getAttribute("name")).replace(":", " "));
    if (role != "mentor" && role != "student")
        response.sendRedirect("./login.jsp");
%>
    <div class="container">

        <div>
            <h1 class="jumbotron" id="mainheader">Welcome to the Curriculum overview page, <%=name%>.</h1>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div>
                    <h2 id="assheader">Current assignments:</h2>
                    <div id="sortable" class="list-group">
                    </div>
                </div>
                <div>
                    <a href="./profile.jsp">Back to profile page</a>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    <p id="role" style="visibility: hidden;"><%=role%></p>

    </div>
<script src="./scripts/assingmentLoader.js"></script>
</body>
</html>
