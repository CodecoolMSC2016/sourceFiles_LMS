package profile;

import Users.User;
import org.codehaus.jackson.map.ObjectMapper;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by imre_meszesan on 11.04.17.
 */
@WebServlet(name = "UserListHandler",
    urlPatterns = "/UserListHandler"
)
public class UserListHandler extends HttpServlet {
    DataContainer container;
    Set<User> studentSet;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = DataContainer.getInstance();
        studentSet = new HashSet<>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<User> registeredUsers = container.getRegisteredUsers();

        HttpSession session = request.getSession(false);
        String userRole = (String)session.getAttribute("role");


        String usersToDisplay = "";
        if (userRole.equals("mentor")){
            for(User user: registeredUsers){
                String role = user.getClass().getSimpleName();
                if (role.equals("mentor")){
                    usersToDisplay += "<div class=\"button " + role + "\">" +
                            "<span class=\"title\">" +
                            user.getName() + "</span>" +
                            "<span class=\"meta type\">" +
                            role +
                            "</span></div>";

                }else {
                    usersToDisplay += "<div class=\"button " + role + "\">" +
                            "<span class=\"title\">" +
                            user.getName() + "</span>" +
                            "<span class=\"meta type\">" +
                            role +
                            "<span class=\"meta expiry\">" +
                            "   Email: " +
                            user.getEmail() +
                            "</span></div>";
                }
            }
        }else {
            for(User user: registeredUsers){
                String role = user.getClass().getSimpleName();
                if (role.equals("Student")){
                    usersToDisplay += "<div class=\"button " + role + "\">" +
                            "<span class=\"title\">" +
                            user.getName() + "</span>" +
                            "<span class=\"meta type\">" +
                            role +
                            "</span></div>";
                }
            }
        }
        request.setAttribute("registeredUsers", usersToDisplay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userList.jsp");
        dispatcher.forward(request, response);
    }
}
