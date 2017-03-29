import Users.User;

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

    public void addUser(User user){
        registeredUsers.add(user);
    }
}
