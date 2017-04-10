package login;

import Users.Mentor;
import Users.User;
import login.loginException.EmailNotFoundException;
import registration.DataContainer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

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
        HttpSession session = request.getSession(true);
        Cookie nameCookie;
        Cookie emailCookie;
        Cookie roleCookie;
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
        nameCookie = new Cookie("userName", userName);
        nameCookie.setMaxAge(60*60);
        emailCookie = new Cookie("email", email);
        emailCookie.setMaxAge(60*60);
        roleCookie = new Cookie("role", role);
        roleCookie.setMaxAge(60*60);
        response.addCookie(nameCookie);
        response.addCookie(emailCookie);
        response.addCookie(roleCookie);
        session.setAttribute("name", userName);
        session.setAttribute("email", email);
        session.setAttribute("role", role);
        session.setMaxInactiveInterval(60*30);
        response.sendRedirect("/profile.jsp");
    }
}
