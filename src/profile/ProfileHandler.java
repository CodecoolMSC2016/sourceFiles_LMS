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
        RequestDispatcher dispatcher =  request.getRequestDispatcher("/profile.jsp");
        User user;
        String newRole = "";
        String name = request.getParameter("changedName");
        boolean changeRole = Boolean.parseBoolean(request.getParameter("changeRole"));
        String email = request.getParameter("confirmEmail");
        try{
            user = DataContainer.getInstance().findUser(email);
            container.deleteUser(email, abspath);
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
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("role", newRole);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher =  request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
