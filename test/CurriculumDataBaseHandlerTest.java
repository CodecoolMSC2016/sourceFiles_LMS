import curriculum.CurrciculumData;
import curriculum.CurriculumDataBaseHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by akos on 2017.04.25..
 */
class CurriculumDataBaseHandlerTest
{
    private static CurriculumDataBaseHandler cdbh;
    private static final String DATABASE = "jdbc:mysql://192.168.150.39:3306/LMS?useSSL=true";
    private static final String DB_USER = "LMSDBManager";
    private static final String DB_PASSWORD = "szupertitkos";
    private static final int listSizeafterDataGeneration = 7;

    private static void init() throws ClassNotFoundException, SQLException
    {
        try
        {
            dropTables();
            buildDummyDB();
            generateDummyData();
            cdbh = CurriculumDataBaseHandler.getInstance();
        } catch (Exception e)
        {
            e.printStackTrace();
            buildDummyDB();
            generateDummyData();
            cdbh = CurriculumDataBaseHandler.getInstance();
        }

    }

    //@AfterAll
    private static void afterTests() throws Exception
    {
        dropTables();
        generateDummyData();
    }

    @Test
    void getCurrciculumDataList() throws Exception
    {
        init();
        List<CurrciculumData> currciculumDataList = cdbh.getCurrciculumDataList();
        assertEquals(listSizeafterDataGeneration, currciculumDataList.size());
    }

    @Test
    void addAssigmentPage()
    {
        try
        {
            cdbh.addAssigmentPage("test", "I am testing shit", 100);
            assertEquals(listSizeafterDataGeneration+1, cdbh.getCurrciculumDataList().size());
        } catch (SQLException e)
        {
            e.printStackTrace();
            fail("Failed while adding to Assignment table");
        }
    }

    @Test
    void addTextPage()
    {
        try
        {
            cdbh.addTextPage("test", "I am testing shit", 100);
            assertEquals(listSizeafterDataGeneration+2, cdbh.getCurrciculumDataList().size());
        } catch (SQLException e)
        {
            e.printStackTrace();
            fail("Failed while adding to Assignment table");
        }
    }

    @Test
    void changeIndex()
    {
        try
        {
            cdbh.changeIndex("AACD123", 11);
            CurrciculumData data = getDataByID("AACD123");
            assertEquals(11, data.getIndex());
        }catch(SQLException e)
        {
            e.printStackTrace();
            fail("Failed while changing position index");
        }
    }

    @Test
    void switchPublished()
    {
        try
        {
            cdbh.switchPublished("AACD123");
            CurrciculumData data = getDataByID("AACD123");
            assertEquals(true, data.isPublished());
        }catch (SQLException e)
        {
            e.printStackTrace();
            fail("Failed while switching published state");
        }
    }

    private static void dropTables() throws Exception
    {
        Statement stmt = null;
        String query = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection sqlconn = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        stmt = sqlconn.createStatement();
        query = "DROP TABLE AssignmentPages, TextPages;";
        stmt.execute(query);
        sqlconn.close();
    }



    private static void createAssignmentsTable() throws ClassNotFoundException, SQLException
    {
        Statement stmt = null;
        String query = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection sqlconn = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        stmt = sqlconn.createStatement();
        query = "CREATE TABLE AssignmentPages(\n" +
                "  ID VARCHAR(255) UNIQUE PRIMARY KEY DEFAULT 'FUCK',\n" +
                "  PosIndex INT UNSIGNED NULL,\n" +
                "  Title VARCHAR(255) NULL,\n" +
                "  Content LONGTEXT NULL,\n" +
                "  MaxScore TINYINT NULL,\n" +
                "  Published BOOLEAN NULL);";
        stmt.execute(query);
        sqlconn.close();
    }

    private static void createTextPagesTable() throws ClassNotFoundException, SQLException
    {
        Statement stmt = null;
        String query = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection sqlconn = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        stmt = sqlconn.createStatement();
        query="CREATE TABLE TextPages(\n" +
                "  ID VARCHAR(255) UNIQUE PRIMARY KEY NOT NULL DEFAULT 'FUCK',\n" +
                "  PosIndex INT UNSIGNED NULL,\n" +
                "  Title VARCHAR(255) NULL,\n" +
                "  Content LONGTEXT NULL,\n" +
                "  Published BOOLEAN NULL);";
        stmt.execute(query);
        sqlconn.close();
    }

    private static void generateDummyData() throws ClassNotFoundException, SQLException
    {
        Statement stmt = null;
        String query = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection sqlconn = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO TextPages VALUES('ABED123', 0, 'How to implement the satanic bible', 'Today, we will summon Satan!', 0 );";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO TextPages VALUES('AACD123', 1, 'How to rape infants', 'Today, we will summon Satan!', 0 );";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO TextPages VALUES('AAAD123', 2, 'How to murder innocent pensioners', 'Today, we will summon Satan!', 0 );";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO TextPages VALUES('AASD123', 3, 'A roman isten verje bele a nathas faszat!', 'Today, we will summon Satan!', 1 );\n";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO AssignmentPages VALUES('ASSD123', 5, 'szoros marsi molylepke!', 'Today, we will summon Satan!', 1, 1 );";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO AssignmentPages VALUES('SSSD123', 5, 'szoros marsi molylepke!', 'Today, we will summon Satan!', 1, 1 );";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO AssignmentPages VALUES('CSSD123', 5, 'szoros marsi molylepke!', 'Today, we will summon Satan!', 1, 1 );\n";
        stmt.execute(query);
        sqlconn.close();
    }
    private static void buildDummyDB()
    {
        try
        {
            createAssignmentsTable();
            createTextPagesTable();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            fail("JDBC Driver Issue!");
        } catch (SQLException e)
        {
            e.printStackTrace();
            fail("SQL Query problem");
        }
    }

    private CurrciculumData getDataByID(String id)
    {
     CurrciculumData result = null;
        for (CurrciculumData data: cdbh.getCurrciculumDataList())
        {
            if(data.getId().equals(id)) result = data;
        }
     return result;
    }

}