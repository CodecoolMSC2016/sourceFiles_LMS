import Users.Mentor;
import Users.Student;
import Users.User;
import registerException.EmailAlreadyExists;

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

    public void addUser(String name,String email, String role)throws EmailAlreadyExists {
        if (checkEmail(email))throw new EmailAlreadyExists("Email already exists");
        if(role.equals("mentor")){
            registeredUsers.add(new Mentor(name,email));
        }else {
            registeredUsers.add(new Student(name,email));
        }
    }

    private boolean checkEmail(String email){
        boolean result = false;
        for (User user:registeredUsers) {
            if(user.getEmail().equals(email)) result = true;

        }
        return result;
    }
}
