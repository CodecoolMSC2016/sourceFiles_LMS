package login;

import Users.Mentor;
import Users.User;
import registration.DataContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akos on 2017.03.30..
 */
@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet
{
    private DataContainer container;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String role;
        String email = request.getParameter("email");
        User loggedIn = DataContainer.getInstance().findUser(email);
        String userName = loggedIn.getName();
        if (loggedIn instanceof Mentor)
        {
            role = "Mentor";
        }
        else
        {
            role = "Student";
        }
        request.setAttribute("name", userName);
        request.setAttribute("email", email);
        request.setAttribute("role", role);
        RequestDispatcher disp =  request.getRequestDispatcher("/profile.jsp");
        disp.forward(request,response);
    }
}
