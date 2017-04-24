package registration;

import Users.Mentor;
import Users.Student;
import Users.User;
import login.loginException.EmailNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.*;
import java.util.HashSet;
import registration.registerException.EmailAlreadyExists;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Set;

public class DataContainer {
    private static final String DATABASE = "jdbc:mysql://192.168.150.39:3306/LMS";
    private static final String DB_USER = "LMSDBManager";
    private static final String DB_PASSWORD = "szupertitkos";
    private Connection connection;
    private Set<User> registeredUsers;
    private String query = "";

    private static DataContainer ourInstance = new DataContainer();

    public static DataContainer getInstance() {
        return ourInstance;
    }

    private DataContainer(){
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        registeredUsers = new HashSet<>();
    }

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

    public void addUser(String name,String email, String role)throws SQLException {
        query = "INSERT INTO Users(Name,Email,Role) VAlUES(?,?,?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,name);
        ps.setString(2,email);
        ps.setString(3,role);
        ps.executeQuery();


    }


    public void updateUser(String email,String name,String role) throws SQLException {

        query = "UPDATE Users SET Name = ?, Role = ?  WHERE email = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1,name);
        ps.setString(2,role);
        ps.setString(3,email);
        ps.executeQuery();



    }

    private boolean checkEmail(String email){
        boolean result = false;

    }
    public User findUser(String email) throws EmailNotFoundException
    {
        for (User user:registeredUsers)
        {
            if (user.getEmail().equals(email))
            {
                return user;
            }
        }
        throw new EmailNotFoundException("This address isn't registered!");
    }

    public void loadUsers(String absPath){
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(absPath);
            Element root = document.getDocumentElement();
            NodeList userList = root.getElementsByTagName("user");

            for (int i = 0; i < userList.getLength(); i++){
                Node node = userList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE){
                    Element user = (Element)node;
                    String name = user.getAttribute("name");
                    String email = user.getAttribute("email");
                    String role = user.getAttribute("role");
                    if (role.equals("mentor")){
                        registeredUsers.add(new Mentor(name, email));
                    }else {
                        registeredUsers.add(new Student(name, email));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DATABASE,
                DB_USER,
                DB_PASSWORD);
    }


}
