package profile;

import Users.Mentor;
import Users.User;
import login.loginException.EmailNotFoundException;
import registration.DataContainer;
import registration.registerException.EmailAlreadyExists;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by imre_meszesan on 30.03.17.
 */
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
        User user = null;
        String newRole = "";
        String name = request.getParameter("changedName");
        boolean changeRole = Boolean.parseBoolean(request.getParameter("changeRole"));
        String email = request.getParameter("confirmEmail");
        try{
            user = DataContainer.getInstance().findUser(email);
            container.deleteUser(email, abspath);
            List<User> registeredUsers = container.getRegisteredUsers();
            registeredUsers.remove(user);
            if (changeRole){
                newRole = (user instanceof Mentor) ? "Student" : "Mentor";
                container.addUser(name, email, newRole, abspath);
            }else{
                newRole = (user instanceof Mentor) ? "Mentor" : "Student";
                container.addUser(name, email, newRole, abspath);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(email.equals(user.getEmail())){
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("role", newRole);
        }
        RequestDispatcher dispatcher =  request.getRequestDispatcher("/profile.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher =  request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
