package login;

import Users.Mentor;
import Users.User;
import login.loginException.EmailNotFoundException;
import registration.DataContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
    private String abspath;
    private DataContainer container;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        abspath = getServletContext().getRealPath("/") + "resources/registeredUsers.xml";
        container = DataContainer.getInstance();
        container.loadUsers(abspath);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String role;
        RequestDispatcher disp;
        String email = request.getParameter("email");
        User loggedIn = null;
        try
        {
            loggedIn = DataContainer.getInstance().findUser(email);
        } catch (EmailNotFoundException e)
        {
            request.setAttribute("emailNotFound",e.getMessage());
            disp =  request.getRequestDispatcher("/login.jsp");
            disp.forward(request,response);
        }
        String userName = loggedIn.getName();
        if (loggedIn instanceof Mentor)
        {
            role = "mentor";
        }
        else
        {
            role = "student";
        }
        request.setAttribute("name", userName);
        request.setAttribute("email", email);
        request.setAttribute("role", role);
        disp =  request.getRequestDispatcher("/profile.jsp");
        disp.forward(request,response);
    }
}
