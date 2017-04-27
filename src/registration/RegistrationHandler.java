package registration;

import registration.registerException.EmailAlreadyExists;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class RegistrationHandler extends HttpServlet {
    private UserDataBaseHandler container;
    private String abspath;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = UserDataBaseHandler.getInstance();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role").toLowerCase();
        String email = request.getParameter("email");
        String name = request.getParameter("name");

        try {
            container.addUser(name, email, role);
        } catch (EmailAlreadyExists emailAlreadyExists)
        {
            request.setAttribute("wrongEmail", emailAlreadyExists.getMessage());
        }
        catch (SQLException e)
        {
            //Needs to be handled correctly
            e.printStackTrace();
            response.sendError(500, "There was an error during database operations");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("./register.jsp");
        dispatcher.forward(request, response);
    }

}
