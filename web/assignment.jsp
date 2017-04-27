<%--
  Created by IntelliJ IDEA.
  User: trixi
  Date: 2017.04.25.
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Assignment Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="./style/assignment.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>

<%
    String role = (String) session.getAttribute("role");
    if(role != "mentor" && role != "student") {
        response.sendRedirect(".login.jsp");
        return;
    }

%>

<div class="container">

    <div>
        <h1 class="jumbotron" id="mainheader">Assignment: ${title}</h1>
    </div>
    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <div>
                <div id="assignment_content">
                    <blockquote>${text}</blockquote>
                    <p>Max score: ${maxScore}</p>
                    <form>
                        <input type="text" name="solution">
                        <input type="submit" value="Submit">
                        <div class="form-group">
                            <label for="exampleInputFile">File input</label>
                            <input type="file" id="exampleInputFile">
                            <p class="help-block">You can upload a file with your solution.</p>
                        </div>
                    </form>
                </div>
            </div>
            <div>
                <a href="./curriculum.jsp">Back to curriculum page</a>
            </div>
        </div>
        <div class="col-md-3"></div>
    </div>
    <p id="role" style="visibility: hidden;"><%=role%></p>

</div>
<script src="./scripts/assignmentLoader.js"></script>


</body>
</html>
