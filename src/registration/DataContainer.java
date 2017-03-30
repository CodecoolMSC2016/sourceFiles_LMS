package registration;

import Users.Mentor;
import Users.Student;
import Users.User;
import login.loginException.EmailNotFoundException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import registration.registerException.EmailAlreadyExists;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mudzso on 2017.03.29..
 */
public class DataContainer {
    private List<User>registeredUsers;
    private static DataContainer ourInstance = new DataContainer();

    public static DataContainer getInstance() {
        return ourInstance;
    }

    private DataContainer() {
        registeredUsers = new ArrayList<>();
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void addUser(String name,String email, String role, String absPath)throws EmailAlreadyExists {
        if (checkEmail(email))throw new EmailAlreadyExists("Email already exists");
        if(role.equals("mentor")){
            registeredUsers.add(new Mentor(name,email));
        }else {
            registeredUsers.add(new Student(name,email));
        }
        Document document = null;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(absPath);
            Element root = document.getDocumentElement();
            Element user = document.createElement("user");
            user.setAttribute("name", name);
            user.setAttribute("email", email);
            user.setAttribute("role", role);

            root.appendChild(user);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(absPath);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkEmail(String email){
        boolean result = false;
        for (User user:registeredUsers) {
            if(user.getEmail().equals(email)) result = true;

        }
        return result;
    }
    public User findUser(String email) throws EmailNotFoundException
    {
        for (User user:registeredUsers)
        {
            if (user.getEmail().equals(email)) return user;
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
                    System.out.println(name);
                    System.out.println(email);
                    System.out.println(role);
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
}
