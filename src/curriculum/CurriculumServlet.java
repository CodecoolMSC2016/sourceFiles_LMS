package curriculum;

import org.codehaus.jackson.map.ObjectMapper;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                for (CurrciculumData data :currciculumDataList) {
                    if(data.getId().equals(id)){
                        if (doPublish){
                            data.setPublished();
                        }else {
                            int newIndex = Integer.parseInt(request.getParameter("index"));
                            data.setIndex(newIndex);
                        }
                    }
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        currciculumDataList.sort(new CurriculumComparator());
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");

        if(request.getSession().getAttribute("role").equals("mentor")){
        objectMapper.writeValue(response.getOutputStream(), currciculumDataList);
        }else {
            List<CurrciculumData>templist = new ArrayList<>();
            for (CurrciculumData data:currciculumDataList) {
                if (data.isPublished()){
                    templist.add(data);
                }
            }
            objectMapper.writeValue(response.getOutputStream(),templist);
        }
    }


}
