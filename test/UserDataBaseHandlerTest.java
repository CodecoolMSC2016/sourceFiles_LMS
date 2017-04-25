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
        assertEquals(4, users.size());
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
            udbh.updateUser("proba@email.co", "Zsiros:Joska", "mentor");
        }
        catch(Exception e)
        {
            fail(e.getMessage());
        }
    }

}