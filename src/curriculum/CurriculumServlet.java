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

    public CurriculumServlet() {
        currciculumDataList = new ArrayList<>();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        currciculumDataList.add(new Text("The gold tastes with punishment, break the lighthouse before it travels.","Sing fast like a cold ship.",true, 0));
        currciculumDataList.add(new Text("Where is the sunny parrot?","Reliable crewmates, to the solar system.",false, 1));
        currciculumDataList.add(new Text("Lads die from desolations like real biscuit eaters.","Dexter gemnas ducunt ad ionicis tormento.",true, 2));
        currciculumDataList.add(new Text("Treasure ho! sail to be breaked.","Amors persuadere, tanquam mirabilis solitudo.",true, 3));
        currciculumDataList.add(new Text("How clear. You vandalize like a skull.","Pol, a bene clabulare, mirabilis zelus!",false, 4));
        currciculumDataList.add(new Text("Courage is a coal-black doubloons.","Cum canis accelerare, omnes lixaes locus pius, peritus stellaes.",true, 5));
        currciculumDataList.add(new Assigment("assigment","Cum canis accelerare, omnes lixaes locus pius, peritus stellaes.",true,30, 6));

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id;
        boolean doPublish;

        if(request.getParameterMap().containsKey("id")){;
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

    private class CurriculumComparator implements Comparator<CurrciculumData>{
        @Override
        public int compare(CurrciculumData data1, CurrciculumData data2) {
            if (data1.getIndex() > data2.getIndex()){
                return 1;
            }else if (data2.getIndex() > data1.getIndex()){
                return -1;
            }else {
                return 0;
            }
        }
    }
}
