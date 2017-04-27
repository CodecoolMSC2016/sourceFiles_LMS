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
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<%
    String role = (String) session.getAttribute("role");
    String name = (((String) session.getAttribute("name")).replace(":", " "));
%>
    <div class="container">

        <div>
            <h1 class="jumbotron" id="mainheader">Welcome to the Curriculum overview page, <%=name%>.</h1>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div id="content">
                    <h2 id="assheader">Current assignments:</h2>
                    <div id="sortable" class="list-group"></div>
                    <div class="panel-group" id="accordion">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                        Add Text Page</a>
                                </h4>
                            </div>
                            <div id="collapse1" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    Text Page Title:<br>
                                    <input id="textTitle" class="input-title" maxlength="45" type="text"><br>
                                    Text Page Content:<br>
                                    <textarea id="textContent" rows="10" cols="60"></textarea><br>
                                    <button id="submitText">Submit</button>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                                        Add Assignment Page</a>
                                </h4>
                            </div>
                            <div id="collapse2" class="panel-collapse collapse">
                                <div class="panel-body">
                                    Assignment Page Title:<br>
                                    <input id="assignmentTitle" class="input-title" maxlength="45" type="text"><br>
                                    Assignment Page Content:<br>
                                    <textarea id="assignmentContent" rows="10"  cols="60"></textarea><br>
                                    Maximum Score:<br>
                                    <input id="assignmentScore" class="input-title" type="number" max="127" placeholder="0"><br>
                                    <button id="submitAssignment">Submit</button>
                                </div>
                            </div>
                        </div>
                    </div>
                <div>
                    <a href="./profile.jsp">Back to profile page</a>
                </div>
                </div>
            </div>
            <div class="col-md-3"></div>
        </div>
    <p id="role" style="visibility: hidden;"><%=role%></p>

    </div>
<script src="./scripts/assingmentLoader.js"></script>
</body>
</html>
