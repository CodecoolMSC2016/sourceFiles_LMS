package assignmentPage;

import Users.User;
import curriculum.Assigment;
import curriculum.CurrciculumData;
import curriculum.CurriculumDataBaseHandler;
import jdk.nashorn.internal.ir.Assignment;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by trixi on 2017.04.24..
 */
@WebServlet(name = "AssignmentHandler",
urlPatterns = "/load-assignment-page")
public class AssignmentHandler extends HttpServlet {

    private CurriculumDataBaseHandler dbHandler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbHandler = CurriculumDataBaseHandler.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String title = request.getParameter("title");
        String content = request.getParameter(("content"));
        int maxScore = Integer.parseInt(request.getParameter("maxScore"));
        try {
            dbHandler.addAssigmentPage(title, content, maxScore);
        }
        catch (SQLException se) {
            throw new RuntimeException(se);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        CurrciculumData assignment = dbHandler.getCurriculumData(id);

        if(assignment instanceof Assigment) {

            request.setAttribute("title", assignment.getTitle());
            request.setAttribute("text", assignment.getText());
            request.setAttribute("maxScore", ((Assigment) assignment).getMaxScore() );
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("./assignment.jsp");
        dispatcher.forward(request, response);



    }
}

