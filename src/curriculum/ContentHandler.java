package curriculum;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by imre_meszesan on 26.04.17.
 */
public class ContentHandler extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurriculumDataBaseHandler DBHandler = CurriculumDataBaseHandler.getInstance();

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String uniqueId;
        try
        {
            if (request.getParameter("type").equals("assignment"))
            {
                int maxScore = Integer.parseInt(request.getParameter("maxScore"));
                uniqueId = DBHandler.addAssigmentPage(title, content, maxScore);
            } else
            {
                uniqueId = DBHandler.addTextPage(title, content, 0);
            }
            response.getWriter().write(uniqueId);
        }catch (SQLException e)
        {
            response.sendError(500, "There was an error during database operations");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}