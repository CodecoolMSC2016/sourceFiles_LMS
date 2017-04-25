package registration;

import Users.Mentor;
import Users.Student;
import Users.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDataBaseHandler {
    private static final String DATABASE = "jdbc:mysql://192.168.150.39:3306/LMS";
    private static final String DB_USER = "LMSDBManager";
    private static final String DB_PASSWORD = "szupertitkos";
    private Connection connection;
    private Set<User> registeredUsers;
    private String query = "";

    private static UserDataBaseHandler ourInstance = new UserDataBaseHandler();

    public static UserDataBaseHandler getInstance() {
        return ourInstance;
    }

    private UserDataBaseHandler(){
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        registeredUsers = new HashSet<>();
    }

    //Test passed, method seems to work correctly.
    public Set<User> getRegisteredUsers() throws SQLException{
        registeredUsers.clear();
        query = "SELECT * FROM Users";
        User user = null;
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();


        while (rs.next()){
            if(rs.getString("Role").equals("mentor")){
                user = new Mentor(rs.getString("Name"),rs.getString("Email"));
        }else{
                user = new Student(rs.getString("Name"),rs.getString("Email"));
            }

            registeredUsers.add(user);
        }


        return registeredUsers;
    }

    /* Method not working, test failed:
        "Can not issue data manipulation statements with executeQuery()."
        0/10 pls fix
     */
    public void addUser(String name,String email, String role)throws SQLException {
        if(!checkEmail(email)) {
            query = "INSERT INTO Users(Name,Email,Role) VAlUES(?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, role);
            ps.executeUpdate();
        }

    }

    /* Method not working, test failed:
        "Can not issue data manipulation statements with executeQuery()."
        0/10 pls fix
     */
    public void updateUser(String email,String name,String role) throws SQLException {

        query = "UPDATE Users SET Name = ?, Role = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,name);
        ps.setString(2,role);
        ps.setString(3,email);
        ps.executeUpdate();



    }

    private boolean checkEmail(String email){
        boolean result = false;
        Set<User>users = null;
        try {
           users = getRegisteredUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (User user:users) {
            if(user.getEmail().equals(email))result = true;
        }
        return result;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }


}
