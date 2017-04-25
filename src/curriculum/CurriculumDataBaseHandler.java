package curriculum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mudzso on 2017.04.24..
 */
public class CurriculumDataBaseHandler {
    private static final String DATABASE = "jdbc:mysql://192.168.150.39:3306/LMS";
    private static final String DB_USER = "LMSDBManager";
    private static final String DB_PASSWORD = "szupertitkos";
    private Connection connection;
    private List<CurrciculumData> currciculumDataList;
    private String query = "";
    private static CurriculumDataBaseHandler ourInstance = new CurriculumDataBaseHandler();


    public static CurriculumDataBaseHandler getInstance() {
        return ourInstance;
    }

    private CurriculumDataBaseHandler() {
        //query = commented out as this is empty.
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        currciculumDataList = new ArrayList<>();
    }

    public List<CurrciculumData> getCurrciculumDataList(){
        currciculumDataList.clear();
        return null;

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }
}
