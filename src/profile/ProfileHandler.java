package profile;

import Users.Mentor;
import Users.User;
import registration.DataContainer;

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
    private DataContainer container;
    private String abspath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = DataContainer.getInstance();
        abspath = getServletContext().getRealPath("/") + "resources/registeredUsers.xml";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        String newRole = "";
        String name = request.getParameter("changedName").replaceAll(" ", ":");
        if (name.equals("")){
            HttpSession session = request.getSession(false);
            name = (String)session.getAttribute("name");
        }
        boolean changeRole = Boolean.parseBoolean(request.getParameter("changeRole"));
        String email = request.getParameter("confirmEmail");
        try{
            user = DataContainer.getInstance().findUser(email);
            container.updateUser(email, abspath);
            Set<User> registeredUsers = container.getRegisteredUsers();
            if(registeredUsers.remove(user)){
                if (changeRole){
                    newRole = (user instanceof Mentor) ? "student" : "mentor";
                    container.addUser(name, email, newRole, abspath);
                }else{
                    newRole = (user instanceof Mentor) ? "mentor" : "student";
                    container.addUser(name, email, newRole, abspath);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
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
