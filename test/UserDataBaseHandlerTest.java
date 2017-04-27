import Users.Mentor;
import Users.Student;
import Users.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import registration.UserDataBaseHandler;
import registration.registerException.EmailAlreadyExists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by akos on 2017.04.24..
 */
class UserDataBaseHandlerTest
{
    private static final String DATABASE = "jdbc:mysql://192.168.150.39:3306/LMS?useSSL=true";
    private static final String DB_USER = "LMSDBManager";
    private static final String DB_PASSWORD = "szupertitkos";
    private final int initialDBSize = 3;

    @AfterAll
    private static void resetDBState() throws Exception
    {
        try
        {
            dropTable();
            createUsersTable();
        } catch (Exception e)
        {
            createUsersTable();
        }
    }

    @BeforeAll
    private static void init() throws Exception
    {
        try
        {
            dropTable();
            createUsersTable();
            generateDummyData();
        }catch(Exception e)
        {
            createUsersTable();
            generateDummyData();
        }
    }

    @Test
    void getRegisteredUsers()
    {
        UserDataBaseHandler udbh = UserDataBaseHandler.getInstance();
        try{
        Set<User> users = udbh.getRegisteredUsers();
            for (User user:users)
            {
                System.out.println(user.toString());
            }
        assertEquals(initialDBSize, users.size());
        }catch (SQLException exception){
            fail(exception.getMessage());
    }

    }

    @Test
    void addUser()
    {
        try
        {
            UserDataBaseHandler udbh = UserDataBaseHandler.getInstance();
            udbh.addUser("Feco","e@mail.com","student");
            assertEquals(initialDBSize+1, udbh.getRegisteredUsers().size());
        }catch(SQLException e)
        {
            fail(e.getMessage());
        } catch (EmailAlreadyExists emailAlreadyExists)
        {
            emailAlreadyExists.printStackTrace();
        }

    }

    @Test
    void updateUser()
    {
        UserDataBaseHandler udbh = UserDataBaseHandler.getInstance();
        try
        {
            String email = "proba@email.co";
            String name = "Zsiros:Joska";
            String role = "mentor";
            User updated = null;
            udbh.updateUser(email, name, role);
            for (User user:udbh.getRegisteredUsers())
            {
                if (user.getEmail().equals(email) && user.getName().equals(name) && user.getClass().getSimpleName().equalsIgnoreCase(role))
                {
                    updated = user;
                }
            }
            if (role.equals("mentor"))
            {
                assertEquals(new Mentor(name, email), updated);
            }
            else
            {
                assertEquals(new Student(name,email), updated);
            }
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }

    private static void createUsersTable() throws ClassNotFoundException, SQLException
    {
        Statement stmt = null;
        String query = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection sqlconn = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        stmt = sqlconn.createStatement();
        query = "CREATE TABLE Users(\n" +
                "  UserID INT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,\n" +
                "  Email VARCHAR(50) NULL UNIQUE,\n" +
                "  Name VARCHAR(255) NULL,\n" +
                "  Role ENUM(\"student\", \"mentor\")\n" +
                ");";
        stmt.execute(query);
        sqlconn.close();
    }

    private static void dropTable() throws Exception
    {
        Statement stmt = null;
        String query = null;
        Class.forName("com.mysql.jdbc.Driver");
        Connection sqlconn = DriverManager.getConnection(DATABASE, DB_USER, DB_PASSWORD);
        stmt = sqlconn.createStatement();
        query = "DROP TABLE Users;";
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
        query = "INSERT INTO Users VALUES(NULL , 'proba@email.co', 'Name:Elemer', 'student');\n";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO Users VALUES(NULL , 'proba2@email.co', 'Name:Elemer', 'student');\n";
        stmt.execute(query);
        stmt = sqlconn.createStatement();
        query = "INSERT INTO Users VALUES(NULL , 'proba3@email.co', 'Name:Kazmer', 'student');\n";
        stmt.execute(query);
    }

}