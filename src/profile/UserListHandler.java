package profile;

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

/**
 * Created by imre_meszesan on 11.04.17.
 */
@WebServlet(name = "UserListHandler",
    urlPatterns = "/UserListHandler"
)
public class UserListHandler extends HttpServlet {
    DataContainer container;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = DataContainer.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<User> registeredUsers = container.getRegisteredUsers();
        HttpSession session = request.getSession(false);
        String userRole = (String)session.getAttribute("role");
        String usersToDisplay = "";
        if (userRole.equals("mentor")){
            for(User user: registeredUsers){
                usersToDisplay += user + "\n" + user.getClass().getSimpleName() + "\n";
            }
        }else {
            for(User user: registeredUsers){
                String role = user.getClass().getSimpleName();
                if (role.equals("Student")){
                    usersToDisplay += user + "\n" + role + "\n";
                }
            }
        }
        request.setAttribute("registeredUsers", usersToDisplay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userList.jsp");
        dispatcher.forward(request, response);
    }
}
