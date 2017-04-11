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
</head>
<body>
<%
    String role = (String) session.getAttribute("role");
    if (role != "mentor" || role != "student")
        response.sendRedirect("./login.jsp");
%>
    <div>
        <h1 id="mainheader">Welcome to the Curriculum overview page, NAME.</h1>
    </div>
    <div>
        <h2 id="assheader">Current assignments:</h2>
        <div id="postedContents">HERE COMES THE ASSIGNMENTS</div>
            <p>This p tag should be replicated, it's a link to the future textpage/assignment</p>
    </div>
<div>
    <a href="./profile.jsp">Back to profile page</a>
</div>
<p id="role" style="visibility: hidden;"><%=role%></p>
</body>
</html>
