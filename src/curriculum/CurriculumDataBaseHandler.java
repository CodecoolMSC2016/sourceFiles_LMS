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

                if(curriculumData instanceof Assigment){
                    published = curriculumData.isPublished();
                    query = "UPDATE AssignmentPages SET = ? WHERE ID = ?";
                }else if(curriculumData instanceof Text){
                    published = curriculumData.isPublished();
                    query = "UPDATE TextPages SET = ? WHERE ID = ?";
                }
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setBoolean(1,!published);
                ps.setString(2,id);
                ps.executeUpdate();

        }

    }

    public void updateAssignmentPage(String id,String title,String text,int maxScore)throws SQLException{
        query = "UPDATE AssignmentPage SET Title = ?, Content = ?, MaxScore = ? WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,title);
        ps.setString(2,text);
        ps.setInt(3,maxScore);
        ps.setString(4,id);
        ps.executeUpdate();
    }


    public void updateTextPage(String id,String title,String text)throws SQLException{
        query = "UPDATE AssignmentPage SET Title = ?, Content = ? WHERE ID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,title);
        ps.setString(2,text);
        ps.setString(3,id);
        ps.executeUpdate();
    }

    public CurrciculumData getCurriculumData(String id){
        CurrciculumData result = null;

        for (CurrciculumData currciculumData:getCurrciculumDataList()
             ) {
            if (currciculumData.getId().equals(id))result = currciculumData;
            }
        return result;

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
