package curriculum;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudzso on 2017.04.11..
 */
@WebServlet(name = "CurriculumServlet",
urlPatterns = "/CurriculumServlet")
public class CurriculumServlet extends HttpServlet {

    private List<CurrciculumData>currciculumDataList;

    public CurriculumServlet() {
        currciculumDataList = new ArrayList<>();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        currciculumDataList.add(new Text("The gold tastes with punishment, break the lighthouse before it travels.","Sing fast like a cold ship.",true));
        currciculumDataList.add(new Text("Where is the sunny parrot?","Reliable crewmates, to the solar system.",false));
        currciculumDataList.add(new Text("Lads die from desolations like real biscuit eaters.","Dexter gemnas ducunt ad ionicis tormento.",true));
        currciculumDataList.add(new Text("Treasure ho! sail to be breaked.","Amors persuadere, tanquam mirabilis solitudo.",true));
        currciculumDataList.add(new Text("How clear. You vandalize like a skull.","Pol, a bene clabulare, mirabilis zelus!",false));
        currciculumDataList.add(new Text("Courage is a coal-black doubloons.","Cum canis accelerare, omnes lixaes locus pius, peritus stellaes.",true));
        currciculumDataList.add(new Assigment("assigment","Cum canis accelerare, omnes lixaes locus pius, peritus stellaes.",true,30));

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id;

        if(request.getParameterMap().containsKey("id")){
             id = request.getParameter("id");
            if(id != null){
                for (CurrciculumData data :currciculumDataList) {
                    if(data.getId().equals(id)){
                        data.setPublished();
                        response.sendRedirect("curriculum.jsp");
                    }
                }
            }
        }else{
            RequestDispatcher dispatcher =  request.getRequestDispatcher("/curriculum.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
