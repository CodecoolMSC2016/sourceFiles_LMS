package profile;

import Users.Mentor;
import Users.User;
import registration.UserDataBaseHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebServlet("/ProfileHandler")
public class ProfileHandler extends HttpServlet {
    private UserDataBaseHandler container;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = UserDataBaseHandler.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String newRole = "";
        String name = request.getParameter("changedName").replaceAll(" ", ":");
        if (name.equals("")){
            session = request.getSession(false);
            name = (String)session.getAttribute("name");
        }
        boolean changeRole = Boolean.parseBoolean(request.getParameter("changeRole"));
        String email = request.getParameter("confirmEmail");
        try{
            container.updateUser(email, name, newRole.toLowerCase() );
            newRole = ((String) session.getAttribute("role"));
            if (changeRole){
                newRole = (session.getAttribute("role").equals("Mentor")) ? "student" : "mentor";
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        session = request.getSession(false);
        session.setAttribute("name", name);
        session.setAttribute("email", email);
        session.setAttribute("role", newRole);
        response.sendRedirect("./profile.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher =  request.getRequestDispatcher("./login.jsp");
        dispatcher.forward(request, response);
    }
}
