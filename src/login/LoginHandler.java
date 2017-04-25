package login;

import Users.Mentor;
import Users.User;
import login.loginException.EmailNotFoundException;
import registration.UserDataBaseHandler;
import registration.registerException.EmailAlreadyExists;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

@WebServlet("/LoginHandler")
public class LoginHandler extends HttpServlet
{
    private UserDataBaseHandler container;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = UserDataBaseHandler.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        String role;
        RequestDispatcher disp;
        String email = request.getParameter("email");
        User loggedIn = null;
        try
        {
            loggedIn = findUser(email);
        } catch (EmailNotFoundException e)
        {
            request.setAttribute("emailNotFound",e.getMessage());
            disp =  request.getRequestDispatcher("./login.jsp");
            disp.forward(request,response);
        }
        catch (SQLException e)
        {
            //Needs to be handled properly
            e.printStackTrace();
        }
        String userName = loggedIn.getName();
        userName = userName.replaceAll(" ", ":");
        if (loggedIn instanceof Mentor)
        {
            role = "mentor";
        }
        else
        {
            role = "student";
        }
        session.setAttribute("name", userName);
        session.setAttribute("email", email);
        session.setAttribute("role", role);
        session.setMaxInactiveInterval(60*30);
        response.sendRedirect("./profile.jsp");
    }

    private User findUser(String email) throws SQLException, EmailNotFoundException
    {
        Set<User> users = container.getRegisteredUsers();
        for (User user:users)
        {
            if (user.getEmail().equals(email))
            {
                return user;
            }
        }
        throw new EmailNotFoundException("Email not found!");
    }
}
