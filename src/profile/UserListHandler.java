package profile;

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
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by imre_meszesan on 11.04.17.
 */

public class UserListHandler extends HttpServlet {
    UserDataBaseHandler container;
    Set<User> studentSet;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = UserDataBaseHandler.getInstance();
        studentSet = new HashSet<>();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<User> registeredUsers = null;
        try
        {
            registeredUsers = container.getRegisteredUsers();
        } catch (SQLException e)
        {
            //needs to be handled
            e.printStackTrace();
        }

        HttpSession session = request.getSession(false);
        String userRole = (String)session.getAttribute("role");


        String usersToDisplay = "";
        if (userRole.equals("mentor")){
            for(User user: registeredUsers){
                String userName = user.getName().replaceAll(":", " ");
                String role = user.getClass().getSimpleName();
                if (role.equals("mentor")){
                    usersToDisplay += "<div class=\"button " + role + "\">" +
                            "<span class=\"title\">" +
                            userName + "</span>" +
                            "<span class=\"meta type\">" +
                            role +
                            "</span></div>";

                }else {
                    usersToDisplay += "<div class=\"button " + role + "\">" +
                            "<span class=\"title\">" +
                            userName + "</span>" +
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
                String userName = user.getName().replaceAll(":", " ");
                if (role.equals("Student")){
                    usersToDisplay += "<div class=\"button " + role + "\">" +
                            "<span class=\"title\">" +
                            userName + "</span>" +
                            "<span class=\"meta type\">" +
                            role +
                            "</span></div>";
                }
            }
        }
        request.setAttribute("registeredUsers", usersToDisplay);
        RequestDispatcher dispatcher = request.getRequestDispatcher("./userList.jsp");
        dispatcher.forward(request, response);
    }
}