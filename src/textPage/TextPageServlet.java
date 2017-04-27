package textPage;

import Users.User;
import curriculum.CurrciculumData;
import curriculum.CurriculumDataBaseHandler;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by imre_meszesan on 24.04.17.

 */
public class TextPageServlet extends HttpServlet {
    private CurriculumDataBaseHandler dbHandler;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbHandler = CurriculumDataBaseHandler.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.println("ID: " + id);
        CurrciculumData data =  dbHandler.getCurriculumData(id);
        request.setAttribute("title", data.getTitle());
        request.setAttribute("text", data.getText());
        RequestDispatcher dispatcher = request.getRequestDispatcher("./textPage.jsp");
        dispatcher.forward(request, response);
    }
}
