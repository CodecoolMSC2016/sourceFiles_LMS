package curriculum;

import org.codehaus.jackson.map.ObjectMapper;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mudzso on 2017.04.11..
 */
@WebServlet(name = "CurriculumServlet",
urlPatterns = "/CurriculumServlet")
public class CurriculumServlet extends HttpServlet {

    private List<CurrciculumData>currciculumDataList;
    private CurriculumDataBaseHandler DBHandler;

    public CurriculumServlet() {
        DBHandler = CurriculumDataBaseHandler.getInstance();
        currciculumDataList = new ArrayList<>();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id;
        boolean doPublish;

        if(request.getParameterMap().containsKey("id")){
             id = request.getParameter("id");
             doPublish = Boolean.parseBoolean(request.getParameter("doPublish"));
            if(id != null){
                try
                {
                    if (doPublish){
                            DBHandler.switchPublished(id);
                    }else {
                        int newIndex = Integer.parseInt(request.getParameter("index"));
                        DBHandler.changeIndex(id, newIndex);
                    }
                }catch (SQLException exception)
                {
                    // Needs to be handled properly.
                    exception.printStackTrace();
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");

        if(request.getSession().getAttribute("role").equals("mentor"))
        {
            objectMapper.writeValue(response.getOutputStream(), DBHandler.getCurrciculumDataList());
        }else
        {
            List<CurrciculumData>templist = new ArrayList<>();
            for (CurrciculumData data:DBHandler.getCurrciculumDataList())
            {
                if (data.isPublished()){
                    templist.add(data);
                }
            }
            objectMapper.writeValue(response.getOutputStream(),templist);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

    }
}
