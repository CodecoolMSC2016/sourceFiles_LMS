package curriculum;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by mudzso on 2017.04.24..
 */
public class CurriculumDataBaseHandler {
    private static final String DATABASE = "jdbc:mysql://192.168.150.39:3306/LMS?useSSL=true";
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
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        currciculumDataList = new ArrayList<>();
    }

    public List<CurrciculumData> getCurrciculumDataList(){
        currciculumDataList.clear();
        try {
            addToListAssigmentPages();
            addToListTextPages();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        currciculumDataList.sort(new CurriculumComparator());
        return currciculumDataList;
    }

    private void addToListAssigmentPages()throws SQLException{
        query = "SELECT * FROM AssignmentPages";
        CurrciculumData currciculumData = null;
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            currciculumData = new Assigment(
                    rs.getString("Title"),
                    rs.getString("Content"),
                    rs.getBoolean("Published"),
                    rs.getInt("MaxScore"),
                    rs.getInt("PosIndex"),
                    rs.getString("ID")
                    );
            currciculumDataList.add(currciculumData);
        }
    }


        private void addToListTextPages()throws SQLException{
            query = "SELECT * FROM TextPages";
            CurrciculumData currciculumData = null;
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                currciculumData = new Text(
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getBoolean("Published"),
                        rs.getInt("PosIndex"),
                        rs.getString("ID")
                );
                currciculumDataList.add(currciculumData);
            }
    }


    public void addAssigmentPage(String title,String text,int maxScore)throws SQLException{
        int index = getCurrciculumDataList().size() + 1;
        query = "INSERT INTO AssignmentPages(ID,PosIndex,Title,Content,MaxScore,Published)" +
                "VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, UUID.randomUUID().toString());
        ps.setInt(2,index);
        ps.setString(3,title);
        ps.setString(4,text);
        ps.setInt(5,maxScore);
        ps.setBoolean(6,false);
        ps.executeUpdate();
    }

    //Why does it require a maximum score?
    public void addTextPage(String title,String text,int maxScore)throws SQLException{
        int index = getCurrciculumDataList().size() + 1;
        query = "INSERT INTO TextPages(ID,PosIndex,Title,Content,Published)" +
                "VALUES(?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, UUID.randomUUID().toString());
        ps.setInt(2,index);
        ps.setString(3,title);
        ps.setString(4,text);
        ps.setBoolean(5,false);
        ps.executeUpdate();
    }

    public void changeIndex(String id,int index)throws SQLException{
        for (CurrciculumData curriculumData:getCurrciculumDataList()
             ) {
            if (curriculumData.getId().equals(id)){
                if(curriculumData instanceof Assigment){
                    query = "SELECT Published FROM AssignmentPages";
                    query = "INSERT INTO AssignmentPages(PosIndex) VALUES(?)";
                }else if(curriculumData instanceof Text){
                    query= "INSERT INTO TextPages(PosIndex) VALUES(?)";
                }
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1,index);
                ps.executeUpdate();
            }

        }

    }


    public void switchPublished(String id)throws SQLException{
        boolean published = false;
        for (CurrciculumData curriculumData:getCurrciculumDataList()
                ) {
            if (curriculumData.getId().equals(id)){
                if(curriculumData instanceof Assigment){
                    query = "SELECT Published FROM AssignmentPages";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    published = rs.getBoolean(1);
                    query = "INSERT INTO AssignmentPages(Publised) VALUES(?)";
                }else if(curriculumData instanceof Text){
                    query = "SELECT Published FROM TextPages";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ResultSet rs = ps.executeQuery();
                    published = rs.getBoolean(1);
                    query= "INSERT INTO TextPages(Published) VALUES(?)";
                }
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setBoolean(1,!published);
                ps.executeUpdate();
            }

        }

    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }


    private class CurriculumComparator implements Comparator<CurrciculumData> {
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
