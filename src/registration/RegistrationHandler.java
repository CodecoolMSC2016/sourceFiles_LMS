package registration;

import registration.registerException.EmailAlreadyExists;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


/**
 * Created by imre_meszesan on 29.03.17.
 */
@WebServlet("/RegistrationHandler")
public class RegistrationHandler extends HttpServlet {
    private DataContainer container;
    private String abspath;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        abspath = getServletContext().getRealPath("/") + "resources/registeredUsers.xml";
        container = DataContainer.getInstance();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role").toLowerCase();
        String email = request.getParameter("email");
        String name = request.getParameter("name");

        container = DataContainer.getInstance();
        try {
            container.addUser(name, email, role, abspath);
        } catch (EmailAlreadyExists emailAlreadyExists) {
            request.setAttribute("wrongEmail", emailAlreadyExists.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("./register.jsp");
        dispatcher.forward(request, response);
    }

}
