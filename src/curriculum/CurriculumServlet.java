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
        currciculumDataList.add(new Text("csuhaja","Sing fast like a cold ship.",true));
        currciculumDataList.add(new Text("Where is the sunny parrot?","Reliable crewmates, to the solar system.",false));
        currciculumDataList.add(new Text("Lads die from desolations like real biscuit eaters.","Dexter gemnas ducunt ad ionicis tormento.",true));
        currciculumDataList.add(new Text("Treasure ho! sail to be breaked.","Amors persuadere, tanquam mirabilis solitudo.",true));
        currciculumDataList.add(new Text("How clear. You vandalize like a skull.","Pol, a bene clabulare, mirabilis zelus!",false));
        currciculumDataList.add(new Text("Courage is a coal-black doubloons.","Cum canis accelerare, omnes lixaes locus pius, peritus stellaes.",true));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), currciculumDataList);
    }
}
