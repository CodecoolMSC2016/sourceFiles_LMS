import Users.Mentor;
import Users.Student;
import Users.User;
import org.junit.jupiter.api.Test;
import registration.UserDataBaseHandler;

import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by akos on 2017.04.24..
 */
class UserDataBaseHandlerTest
{
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
        assertEquals(5, users.size());
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
            assertEquals(5, udbh.getRegisteredUsers().size());
        }catch(SQLException e)
        {
            fail(e.getMessage());
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

}