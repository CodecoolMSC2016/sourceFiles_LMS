package profile;

import Users.Mentor;
import Users.Student;
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = DataContainer.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        String name = request.getParameter("changedName");
        boolean changeRole = Boolean.parseBoolean(request.getParameter("changeRole"));
        String email = (String)request.getAttribute("email");
        try {
            user = DataContainer.getInstance().findUser(email);
        } catch (EmailNotFoundException e) {
            e.printStackTrace();
        }
        if (changeRole){
            List<User> registeredUsers = container.getRegisteredUsers();
            String role = (String)request.getAttribute("role");
            registeredUsers.remove(user);
            try {
                if (role.equals("mentor")){
                    container.addUser(name, email, "sudent");
                }else {
                    container.addUser(name, email, "mentor");
                }
            } catch (EmailAlreadyExists emailAlreadyExists) {
                emailAlreadyExists.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher =  request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}
