import Users.Mentor;
import Users.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by imre_meszesan on 29.03.17.
 */
@WebServlet(name = "RegistrationHandler")
public class RegistrationHandler extends HttpServlet {
    private DataContainer container;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = DataContainer.getInstance();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mentor = request.getParameter("mentor");
        String student = request.getParameter("student");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        if (mentor.equals("")){
            container.addUser(new Mentor(name, email));
        }else {
            container.addUser(new Student(name, email));
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
        dispatcher.forward(request, response);
    }

}
