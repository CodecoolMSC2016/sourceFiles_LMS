package assignmentPage;

import Users.User;
import jdk.nashorn.internal.ir.Assignment;
import org.codehaus.jackson.map.ObjectMapper;
import registration.DataContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Set;

/**
 * Created by trixi on 2017.04.24..
 */
@WebServlet(name = "AssignmentHandler",
urlPatterns = "/AssignmentHandler")
public class AssignmentHandler extends HttpServlet {

    private DataContainer container;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String content = request.getParameter(("content"));
        int maxScore = Integer.parseInt(request.getParameter("maxScore"));


        Assignment assignment = new Assignment(title, content, false, maxScore, index);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Set<Assignment> assignments = container.getAssignments();

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), assignments);

    }
}

