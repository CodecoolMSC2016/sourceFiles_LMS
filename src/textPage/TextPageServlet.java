package textPage;

import Users.User;
import org.codehaus.jackson.map.ObjectMapper;
import registration.DataContainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * Created by imre_meszesan on 24.04.17.
 */
@WebServlet(name = "TextPageServlet",
    urlPatterns = "/TextPageServlet"
)
public class TextPageServlet extends HttpServlet {
    private DataContainer container;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        container = DataContainer.getInstance();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Set<User> userSet = container.getRegisteredUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), userSet);
    }
}
